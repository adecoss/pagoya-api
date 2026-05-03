package com.alvaro.pagoya.transfer.service;

import com.alvaro.pagoya.transfer.dto.TransferByCurrencyReport;
import com.alvaro.pagoya.transfer.dto.TransferByDayReport;
import com.alvaro.pagoya.transfer.dto.TransferByStatusReport;
import com.alvaro.pagoya.transfer.dto.TransferRequest;
import com.alvaro.pagoya.transfer.dto.TransferResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface ITransferService {
    TransferResponse transfer(TransferRequest request);
    Page<TransferResponse> findByAccountNumber(String accountNumber, Pageable pageable);
    List<TransferByCurrencyReport> reportByCurrency();
    List<TransferByDayReport> reportByDay(LocalDate from, LocalDate to);
    List<TransferByStatusReport> reportByStatus();
}
