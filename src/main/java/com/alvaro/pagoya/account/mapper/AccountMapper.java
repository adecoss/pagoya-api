package com.alvaro.pagoya.account.mapper;

import com.alvaro.pagoya.account.dto.AccountBalanceResponse;
import com.alvaro.pagoya.account.dto.AccountResponse;
import com.alvaro.pagoya.account.model.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountResponse toResponse(Account account);
    AccountBalanceResponse toBalance(Account account);
}
