package com.app.beer.order.service.services;

import com.app.beer.order.service.domain.BeerOrder;
import com.app.beer.order.service.domain.BeerOrderEventEnum;
import com.app.beer.order.service.domain.BeerOrderStatusEnum;
import com.app.beer.order.service.repositories.BeerOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;

import java.util.Optional;
import java.util.UUID;

/**
 * @author t0k02w6 on 05/06/21
 * @project mssc-beer-order-service
 */
@RequiredArgsConstructor
public class BeerOrderStateChangeInterceptor extends StateMachineInterceptorAdapter<BeerOrderStatusEnum, BeerOrderEventEnum> {
    private final BeerOrderRepository beerOrderRepository;

    @Override
    public void preStateChange(State<BeerOrderStatusEnum, BeerOrderEventEnum> state, Message<BeerOrderEventEnum> message, Transition<BeerOrderStatusEnum, BeerOrderEventEnum> transition, StateMachine<BeerOrderStatusEnum, BeerOrderEventEnum> stateMachine) {
        Optional.ofNullable(message).ifPresent(msg -> {
            Optional.ofNullable(UUID.class.cast(msg.getHeaders().getOrDefault(BeerOrderServiceImpl.BEER_ORDER_HEADER, UUID.fromString(""))))
                    .ifPresent(id -> {
                        BeerOrder beerOrder = beerOrderRepository.getOne(id);
                        beerOrder.setOrderStatus(state.getId());
                        beerOrderRepository.save(beerOrder);
                    });
        });
    }
}
