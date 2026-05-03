package com.alvaro.pagoya.customer.mapper;

import com.alvaro.pagoya.customer.dto.CreateCustomerRequest;
import com.alvaro.pagoya.customer.dto.CustomerResponse;
import com.alvaro.pagoya.customer.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    @Mapping(target = "id", ignore = true)
    Customer toEntity(CreateCustomerRequest request);
    CustomerResponse toResponse(Customer customer);
}
