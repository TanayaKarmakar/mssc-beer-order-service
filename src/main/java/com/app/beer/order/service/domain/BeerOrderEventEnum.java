package com.app.beer.order.service.domain;

/**
 * @author t0k02w6 on 02/06/21
 * @project mssc-beer-order-service
 */
public enum BeerOrderEventEnum {
    VALIDATE_ORDER, VALIDATION_PASSED, VALIDATION_FAILED, ALLOCATE_ORDER,
    ALLOCATION_SUCCESS, ALLOCATION_NO_INVENTORY, ALLOCATION_FAILED,
    BEERORDER_PICKED_UP;
}
