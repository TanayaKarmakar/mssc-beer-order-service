package com.app.beer.order.service.services.beer;

import com.app.beer.order.service.web.model.BeerDto;

import java.util.Optional;
import java.util.UUID;

/**
 * @author t0k02w6 on 17/05/21
 * @project mssc-beer-order-service
 */
public interface BeerService {
    Optional<BeerDto> getBeerById(UUID uuid);

    Optional<BeerDto> getBeerByUpc(String upc);
}
