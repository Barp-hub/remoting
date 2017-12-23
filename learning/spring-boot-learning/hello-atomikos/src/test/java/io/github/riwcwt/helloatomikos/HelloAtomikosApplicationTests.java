package io.github.riwcwt.helloatomikos;

import io.github.riwcwt.helloatomikos.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloAtomikosApplicationTests {

    @Autowired
    private OrderService orderService = null;

    @Test
    public void order() {
        this.orderService.addOrder();
    }

}
