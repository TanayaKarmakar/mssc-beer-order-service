package com.app.beer.order.service.services.testcomponents;

import com.app.beer.order.service.config.JmsConfig;
import com.app.common.events.ValidateOrderRequest;
import com.app.common.events.ValidateOrderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @author t0k02w6 on 11/06/21
 * @project mssc-beer-order-service
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderValidationListener {
    private JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE)
    public void listen(Message msg) {
        ValidateOrderRequest validateOrderRequest = (ValidateOrderRequest) msg.getPayload();


        ValidateOrderResult validateOrderResult = new ValidateOrderResult();
        validateOrderResult.setIsValid(true);
        validateOrderResult.setOrderId(validateOrderRequest.getBeerOrderDto().getId());

        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE, validateOrderResult);
    }
}
