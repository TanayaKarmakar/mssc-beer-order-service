package com.app.beer.order.service.statemachine.actions;

import com.app.beer.order.service.config.JmsConfig;
import com.app.beer.order.service.domain.BeerOrder;
import com.app.beer.order.service.domain.BeerOrderEventEnum;
import com.app.beer.order.service.domain.BeerOrderStatusEnum;
import com.app.beer.order.service.repositories.BeerOrderRepository;
import com.app.beer.order.service.services.BeerOrderServiceImpl;
import com.app.beer.order.service.web.mappers.BeerOrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author t0k02w6 on 08/06/21
 * @project mssc-beer-order-service
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AllocateOrderAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {
    private final JmsTemplate jmsTemplate;
    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderMapper beerOrderMapper;

    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> stateContext) {
        String beerOrderId = (String)stateContext.getMessage().getHeaders().get(BeerOrderServiceImpl.BEER_ORDER_HEADER);
        BeerOrder beerOrder = beerOrderRepository.findOneById(UUID.fromString(beerOrderId));

        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_QUEUE, beerOrderMapper.beerOrderToDto(beerOrder));

        log.debug("Sent allocation Request for order id: " + beerOrderId);
    }
}
