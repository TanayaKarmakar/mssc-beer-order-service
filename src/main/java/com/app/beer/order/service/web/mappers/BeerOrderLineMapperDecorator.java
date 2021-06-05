package com.app.beer.order.service.web.mappers;

import com.app.beer.order.service.domain.BeerOrderLine;
import com.app.beer.order.service.services.beer.BeerService;
import com.app.common.model.BeerDto;
import com.app.common.model.BeerOrderLineDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * @author t0k02w6 on 17/05/21
 * @project mssc-beer-order-service
 */
public abstract class BeerOrderLineMapperDecorator implements BeerOrderLineMapper {
    @Autowired
    private BeerService beerService;

    @Autowired
    private BeerOrderLineMapper beerOrderLineMapper;

    public BeerOrderLineDto beerOrderLineToDto(BeerOrderLine line) {
        BeerOrderLineDto beerOrderLineDto = beerOrderLineMapper.beerOrderLineToDto(line);
        Optional<BeerDto> beerDtoOptional = beerService.getBeerByUpc(line.getUpc());

        beerDtoOptional.ifPresent(beerDto -> {
            beerOrderLineDto.setBeerName(beerDto.getBeerName());
            beerOrderLineDto.setBeerStyle(beerDto.getBeerStyle());
            beerOrderLineDto.setPrice(beerDto.getPrice());
            beerOrderLineDto.setBeerId(beerDto.getId());
        });
        return beerOrderLineDto;
    }
}

