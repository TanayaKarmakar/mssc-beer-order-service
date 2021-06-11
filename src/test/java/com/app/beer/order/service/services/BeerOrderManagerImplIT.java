package com.app.beer.order.service.services;

import com.app.beer.order.service.domain.BeerOrder;
import com.app.beer.order.service.domain.BeerOrderLine;
import com.app.beer.order.service.domain.BeerOrderStatusEnum;
import com.app.beer.order.service.domain.Customer;
import com.app.beer.order.service.repositories.BeerOrderRepository;
import com.app.beer.order.service.repositories.CustomerRepository;
import com.app.beer.order.service.services.beer.BeerServiceImpl;
import com.app.common.model.BeerDto;
import com.app.common.model.BeerOrderPagedList;
import com.app.common.model.BeerPagedList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jenspiegsa.wiremockextension.WireMockExtension;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.github.jenspiegsa.wiremockextension.ManagedWireMockServer.with;

import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static com.github.tomakehurst.wiremock.client.WireMock.get;

/**
 * @author t0k02w6 on 09/06/21
 * @project mssc-beer-order-service
 */
@ExtendWith(WireMockExtension.class)
@SpringBootTest
public class BeerOrderManagerImplIT {
    @Autowired
    private BeerOrderManager beerOrderManager;

    @Autowired
    private BeerOrderRepository beerOrderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    private ObjectMapper objectMapper;

    private Customer testCustomer;

    private UUID beerId = UUID.randomUUID();

    @TestConfiguration
    static class RestTemplateBuilderProvider {
        @Bean(destroyMethod = "stop")
        public WireMockServer wireMockServer() {
            WireMockServer server = with(wireMockConfig().port(8083));
            server.start();
            return server;
        }
    }

    @BeforeEach
    void setUp() {
        Customer customer = new Customer();
        customer.setCustomerName("TestCustomer");

        testCustomer = customerRepository.save(customer);
    }

    @Test
    public void testNewToAllocate() throws JsonProcessingException {
        BeerDto beerDto = createBeerDto();
        BeerPagedList list = new BeerPagedList(Collections.singletonList(beerDto));


        wireMockServer.stubFor(get(BeerServiceImpl.BEER_UPC_PATH_V1 + "12345")
                .willReturn(okJson(objectMapper.writeValueAsString(beerDto))));
        BeerOrder beerOrder = createBeerOrder();

        BeerOrder savedBeerOrder = beerOrderManager.newBeerOrder(beerOrder);

        assertNotNull(savedBeerOrder);
        assertEquals(BeerOrderStatusEnum.ALLOCATED, savedBeerOrder.getOrderStatus());
    }

    public BeerDto createBeerDto() {
        BeerDto beerDto = new BeerDto();
        beerDto.setId(beerId);
        beerDto.setUpc("12345");
        return beerDto;
    }

    public BeerOrder createBeerOrder() {
        BeerOrder beerOrder = new BeerOrder();
        beerOrder.setCustomer(testCustomer);

        Set<BeerOrderLine> lines = new HashSet<>();
        BeerOrderLine orderLine = new BeerOrderLine();
        orderLine.setBeerId(beerId);
        orderLine.setOrderQuantity(1);
        orderLine.setBeerOrder(beerOrder);
        lines.add(orderLine);

        beerOrder.setBeerOrderLines(lines);

        return beerOrder;
    }

}
