package com.joy.xxfy.informationaldxn;

import com.joy.xxfy.informationaldxn.module.drill.domain.repository.DrillWorkRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InformationalDxnApplicationTests {

    @Autowired
    private DrillWorkRepository drillWorkRepository;

    @Test
    public void contextLoads() {

    }

}
