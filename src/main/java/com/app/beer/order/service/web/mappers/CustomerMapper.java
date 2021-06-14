package com.app.beer.order.service.web.mappers;

import com.app.beer.order.service.domain.Customer;
import com.app.common.model.CustomerDto;
import org.mapstruct.Mapper;

/**
 * @author t0k02w6 on 14/06/21
 * @project mssc-beer-order-service
 */
@Mapper(uses = {DateMapper.class})
public interface CustomerMapper {
    CustomerDto customerToDto(Customer customer);

    Customer dtoToCustomer(CustomerDto customerDto);
}
