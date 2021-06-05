package com.app.beer.order.service.services;

import com.app.beer.order.service.domain.BeerOrder;

/**
 * @author t0k02w6 on 05/06/21
 * @project mssc-beer-order-service
 */
public interface BeerOrderManager {
    BeerOrder newBeerOrder(BeerOrder beerOrder);
}
