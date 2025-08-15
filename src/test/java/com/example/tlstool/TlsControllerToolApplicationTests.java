package com.example.tlstool;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class TlsControllerToolApplicationTests {

    @Test
    void contextLoads() {
    }

}
