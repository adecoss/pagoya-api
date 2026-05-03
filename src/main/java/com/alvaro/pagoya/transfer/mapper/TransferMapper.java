package com.alvaro.pagoya.transfer.mapper;

import com.alvaro.pagoya.transfer.dto.TransferResponse;
import com.alvaro.pagoya.transfer.model.Transfer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransferMapper {
    TransferResponse toResponse(Transfer transfer);
}
