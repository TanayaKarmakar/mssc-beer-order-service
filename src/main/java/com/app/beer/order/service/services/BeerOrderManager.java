package com.app.beer.order.service.services;

import com.app.beer.order.service.domain.BeerOrder;
import com.app.common.model.BeerOrderDto;

import java.util.UUID;

/**
 * @author t0k02w6 on 05/06/21
 * @project mssc-beer-order-service
 */
public interface BeerOrderManager {
    BeerOrder newBeerOrder(BeerOrder beerOrder);

    void processValidationResult(UUID beerOrderId, Boolean isValid);

    void beerOrderAllocationPassed(BeerOrderDto beerOrder);

    void beerOrderAllocationPendingInventory(BeerOrderDto beerOrder);

    void beerOrderAllocationFailed(BeerOrderDto beerOrder);
}
