package com.alvaro.pagoya.transfer.service;

import com.alvaro.pagoya.account.exception.AccountNotOperativeException;
import com.alvaro.pagoya.account.model.Account;
import com.alvaro.pagoya.account.model.AccountStatus;
import com.alvaro.pagoya.account.repository.AccountRepository;
import com.alvaro.pagoya.shared.exception.ResourceNotFoundException;
import com.alvaro.pagoya.transfer.dto.TransferByCurrencyReport;
import com.alvaro.pagoya.transfer.dto.TransferByDayReport;
import com.alvaro.pagoya.transfer.dto.TransferByStatusReport;
import com.alvaro.pagoya.transfer.dto.TransferRequest;
import com.alvaro.pagoya.transfer.dto.TransferResponse;
import com.alvaro.pagoya.transfer.exception.InsufficientBalanceException;
import com.alvaro.pagoya.transfer.exception.SameAccountTransferException;
import com.alvaro.pagoya.transfer.mapper.TransferMapper;
import com.alvaro.pagoya.transfer.model.Transfer;
import com.alvaro.pagoya.transfer.model.TransferStatus;
import com.alvaro.pagoya.transfer.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TransferService implements ITransferService {

    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;
    private final TransferMapper transferMapper;
    private final RestTemplate restTemplate;

    @Override
    @Transactional
    public TransferResponse transfer(TransferRequest request) {
        // RN-10: cuentas distintas
        if (request.sourceAccountNumber().equals(request.targetAccountNumber())) {
            throw new SameAccountTransferException();
        }

        Account source = accountRepository
            .findByAccountNumber(request.sourceAccountNumber())
            .orElseThrow(() -> new ResourceNotFoundException(
                "cuenta origen no encontrada"));
        Account target = accountRepository
            .findByAccountNumber(request.targetAccountNumber())
            .orElseThrow(() -> new ResourceNotFoundException(
                "cuenta destino no encontrada"));

        // RN-05: cuenta origen activa
        if (source.getStatus() != AccountStatus.ACTIVE) {
            throw new AccountNotOperativeException();
        }
        // RN-11: validar saldo
        if (source.getBalance().compareTo(request.amount()) < 0) {
            throw new InsufficientBalanceException();
        }

        BigDecimal finalAmount = request.amount();
        BigDecimal rate = null;
        // RN-12: tipo de cambio cuando no es PEN
        if (!"PEN".equals(request.currency())) {
            rate = getExchangeRate(request.currency());
            finalAmount = request.amount().multiply(rate);
        }
        source.setBalance(source.getBalance().subtract(request.amount()));
        target.setBalance(target.getBalance().add(finalAmount));
        accountRepository.save(source);
        accountRepository.save(target);

        Transfer transfer = Transfer.builder()
            .sourceAccountNumber(request.sourceAccountNumber())
            .targetAccountNumber(request.targetAccountNumber())
            .amount(request.amount())
            .currency(request.currency())
            .exchangeRate(rate)
            .status(TransferStatus.COMPLETED)
            .createdAt(LocalDateTime.now())
            .build();
        return transferMapper.toResponse(transferRepository.save(transfer));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransferResponse> findByAccountNumber(
            String accountNumber, Pageable pageable) {
        return transferRepository
            .findBySourceAccountNumber(accountNumber, pageable)
            .map(transferMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransferByCurrencyReport> reportByCurrency() {
        return transferRepository.reportByCurrencyRaw().stream()
            .map(r -> new TransferByCurrencyReport(
                (String) r[0],
                ((Number) r[1]).longValue(),
                (BigDecimal) r[2]))
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransferByDayReport> reportByDay(LocalDate from, LocalDate to) {
        return transferRepository.reportByDayRaw(from, to).stream()
            .map(r -> new TransferByDayReport(
                ((java.sql.Date) r[0]).toLocalDate(),
                ((Number) r[1]).longValue(),
                (BigDecimal) r[2]))
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransferByStatusReport> reportByStatus() {
        return transferRepository.reportByStatusRaw().stream()
            .map(r -> new TransferByStatusReport(
                (String) r[0],
                ((Number) r[1]).longValue()))
            .toList();
    }

    private BigDecimal getExchangeRate(String currency) {
        String url = "https://api.frankfurter.dev/v2/rates?base=PEN&quotes=" + currency;
        Map response = restTemplate.getForObject(url, Map.class);
        Map rates = (Map) response.get("rates");
        return new BigDecimal(rates.get(currency).toString());
    }
}
