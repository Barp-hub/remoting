package io.github.riwcwt.hellomybatisstarter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "io.github.riwcwt.mapper")
public class HelloMybatisStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloMybatisStarterApplication.class, args);
    }
}
