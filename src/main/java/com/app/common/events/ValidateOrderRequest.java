package com.app.common.events;

import com.app.common.model.BeerOrderDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * @author t0k02w6 on 05/06/21
 * @project mssc-beer-order-service
 */
@Data
@NoArgsConstructor
public class ValidateOrderRequest {
    private BeerOrderDto beerOrderDto;
}
