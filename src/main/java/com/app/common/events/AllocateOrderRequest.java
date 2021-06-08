package com.app.common.events;

import com.app.common.model.BeerOrderDto;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author t0k02w6 on 08/06/21
 * @project mssc-beer-order-service
 */
@Data
@NoArgsConstructor
public class AllocateOrderRequest {
    private BeerOrderDto beerOrderDto;
}
