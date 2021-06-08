package com.app.beer.order.service.services.listeners;

import com.app.beer.order.service.config.JmsConfig;
import com.app.beer.order.service.services.BeerOrderManager;
import com.app.common.events.ValidateOrderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author t0k02w6 on 08/06/21
 * @project mssc-beer-order-service
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ValidationResultListener {
    private BeerOrderManager beerOrderManager;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE)
    public void listen(ValidateOrderResult validateOrderResult) {
        final UUID beerOrderId = validateOrderResult.getOrderId();

        log.debug("Validation Result for Order Id: " + beerOrderId);

        beerOrderManager.processValidationResult(beerOrderId, validateOrderResult.getIsValid());

    }
}
