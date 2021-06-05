package com.app.beer.order.service.statemachine.actions;

import com.app.beer.order.service.config.JmsConfig;
import com.app.beer.order.service.domain.BeerOrder;
import com.app.beer.order.service.domain.BeerOrderEventEnum;
import com.app.beer.order.service.domain.BeerOrderStatusEnum;
import com.app.beer.order.service.repositories.BeerOrderRepository;
import com.app.beer.order.service.services.BeerOrderServiceImpl;
import com.app.beer.order.service.web.mappers.BeerOrderMapper;
import com.app.common.events.ValidateOrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author t0k02w6 on 05/06/21
 * @project mssc-beer-order-service
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ValidateOrderAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {
    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderMapper beerOrderMapper;
    private final JmsTemplate jmsTemplate;

    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> stateContext) {
        String beerOrderId = (String)stateContext.getMessage().getHeaders().get(BeerOrderServiceImpl.BEER_ORDER_HEADER);
        BeerOrder beerOrder = beerOrderRepository.findOneById(UUID.fromString(beerOrderId));
        ValidateOrderRequest validateOrderRequest = new ValidateOrderRequest();
        validateOrderRequest.setBeerOrderDto(beerOrderMapper.beerOrderToDto(beerOrder));
        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_QUEUE, validateOrderRequest);
        log.debug("Sent validation order request to queue for order id : {}", beerOrderId);
    }
}
