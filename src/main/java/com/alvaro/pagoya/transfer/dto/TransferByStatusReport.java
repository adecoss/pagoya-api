package com.alvaro.pagoya.transfer.dto;

public record TransferByStatusReport(
    String status,
    Long total
) {}
