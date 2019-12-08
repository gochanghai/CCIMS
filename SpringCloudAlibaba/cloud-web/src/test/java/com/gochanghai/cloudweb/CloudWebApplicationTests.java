package com.gochanghai.cloudweb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CloudWebApplicationTests {

    @Test
    public void contextLoads() {
        String ss = "we";
        if (!"we".equals(ss)) {
            return;
        } else {
            System.out.println("wewewe");
        }
    }

}
