package com.app.beer.order.service.services.listeners;

import com.app.beer.order.service.config.JmsConfig;
import com.app.beer.order.service.services.BeerOrderManager;
import com.app.common.events.AllocateOrderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author t0k02w6 on 09/06/21
 * @project mssc-beer-order-service
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderAllocationResultListener {
    private final BeerOrderManager beerOrderManager;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE)
    public void listen(AllocateOrderResult allocateOrderResult) {
        if(!allocateOrderResult.getAllocationError() && !allocateOrderResult.getPendingInventory())
            //allocated normally
            beerOrderManager.beerOrderAllocationPassed(allocateOrderResult.getBeerOrderDto());
        else if(!allocateOrderResult.getAllocationError() && allocateOrderResult.getPendingInventory())
            //pending inventory
            beerOrderManager.beerOrderAllocationPendingInventory(allocateOrderResult.getBeerOrderDto());
        else if(allocateOrderResult.getAllocationError())
            //allocation failed
            beerOrderManager.beerOrderAllocationFailed(allocateOrderResult.getBeerOrderDto());
    }
}
