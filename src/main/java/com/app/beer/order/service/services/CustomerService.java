package com.app.beer.order.service.services;

import com.app.common.model.CustomerPagedList;
import org.springframework.data.domain.Pageable;

/**
 * @author t0k02w6 on 14/06/21
 * @project mssc-beer-order-service
 */
public interface CustomerService {
    CustomerPagedList listCustomers(Pageable pageable);
}
