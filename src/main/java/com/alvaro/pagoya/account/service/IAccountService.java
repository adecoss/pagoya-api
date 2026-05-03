package com.alvaro.pagoya.account.service;

import com.alvaro.pagoya.account.dto.AccountBalanceResponse;
import com.alvaro.pagoya.account.dto.AccountResponse;
import com.alvaro.pagoya.account.dto.AccountSummaryReport;
import com.alvaro.pagoya.account.dto.CreateAccountRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface IAccountService {
    AccountResponse create(CreateAccountRequest request);
    AccountBalanceResponse getBalance(String accountNumber);
    Page<AccountResponse> findByCustomer(Long customerId, Pageable pageable);
    List<AccountSummaryReport> reportSummary();
}
