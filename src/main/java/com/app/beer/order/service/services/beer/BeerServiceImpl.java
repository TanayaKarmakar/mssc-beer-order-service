package com.app.beer.order.service.services.beer;

import com.app.common.model.BeerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

/**
 * @author t0k02w6 on 17/05/21
 * @project mssc-beer-order-service
 */
@Service
@ConfigurationProperties(prefix = "sfg.brewery", ignoreUnknownFields = true)
public class BeerServiceImpl implements BeerService{
    public final static String BEER_PATH_V1 = "/api/v1/beer/";
    public final static String BEER_UPC_PATH_V1 = "/api/v1/beer/upc/";

    @Value("${sfg.brewery.beer-service-host}")
    private String beerServiceHost;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Optional<BeerDto> getBeerById(UUID uuid) {
        return Optional.ofNullable(restTemplate.getForObject(beerServiceHost + BEER_PATH_V1 + uuid.toString(), BeerDto.class));
    }

    @Override
    public Optional<BeerDto> getBeerByUpc(String upc) {
        return Optional.ofNullable(restTemplate.getForObject(beerServiceHost + BEER_UPC_PATH_V1 + upc, BeerDto.class));
    }
}
