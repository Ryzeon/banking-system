package me.ryzeon.bankingsystem;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BankingSystemApplicationTests {

    @Autowired
    private ApplicationContext context;

    @Test
    void applicationContextLoads() {
        assertNotNull(context);
    }

    @Test
    void mainApplicationStarts() {
        BankingSystemApplication.main(new String[]{});
        assertNotNull(context);
    }

}
