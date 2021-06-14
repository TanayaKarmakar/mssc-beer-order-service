package com.app.common.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author t0k02w6 on 14/06/21
 * @project mssc-beer-order-service
 */
public class CustomerPagedList extends PageImpl<CustomerDto> {

    public CustomerPagedList(List<CustomerDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public CustomerPagedList(List<CustomerDto> content) {
        super(content);
    }
}
