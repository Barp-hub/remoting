package io.github.riwcwt.helloatomikos.service;

import io.github.riwcwt.express.entity.Express;
import io.github.riwcwt.express.mapper.ExpressMapper;
import io.github.riwcwt.order.entity.Order;
import io.github.riwcwt.order.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper = null;

    @Autowired
    private ExpressMapper expressMapper = null;

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void addOrder() {
        Order order = new Order();
        order.setProduct("手提包");
        order.setCount(1);

        this.orderMapper.insert(order);

        if (order.getId() > 0) {
            throw new RuntimeException("故意抛出异常");
        }

        Express express = new Express();
        express.setStatus(1);
        this.expressMapper.insert(express);
    }

}
