package com.app.beer.order.service.services;

import com.app.beer.order.service.domain.BeerOrder;
import com.app.beer.order.service.domain.BeerOrderLine;
import com.app.beer.order.service.domain.BeerOrderStatusEnum;
import com.app.beer.order.service.domain.Customer;
import com.app.beer.order.service.repositories.BeerOrderRepository;
import com.app.beer.order.service.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author t0k02w6 on 09/06/21
 * @project mssc-beer-order-service
 */
@SpringBootTest
public class BeerOrderManagerImplIT {
    @Autowired
    private BeerOrderManager beerOrderManager;

    @Autowired
    private BeerOrderRepository beerOrderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private Customer testCustomer;

    private UUID beerId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        Customer customer = new Customer();
        customer.setCustomerName("TestCustomer");

        testCustomer = customerRepository.save(customer);
    }

    @Test
    public void testNewToAllocate() {
        BeerOrder beerOrder = createBeerOrder();

        BeerOrder savedBeerOrder = beerOrderManager.newBeerOrder(beerOrder);

        assertNotNull(savedBeerOrder);
        assertEquals(BeerOrderStatusEnum.ALLOCATED, savedBeerOrder.getOrderStatus());

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
