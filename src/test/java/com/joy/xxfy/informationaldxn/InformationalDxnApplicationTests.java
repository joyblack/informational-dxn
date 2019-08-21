package com.joy.xxfy.informationaldxn;

import com.joy.xxfy.informationaldxn.produce.domain.entity.DrillWorkDetailEntity;
import com.joy.xxfy.informationaldxn.produce.domain.entity.DrillWorkEntity;
import com.joy.xxfy.informationaldxn.produce.domain.repository.DrillWorkRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InformationalDxnApplicationTests {

    @Autowired
    private DrillWorkRepository drillWorkRepository;

    @Test
    public void contextLoads() {
        DrillWorkEntity entity = new DrillWorkEntity();
        entity.setDrillWorkName("test");

        DrillWorkDetailEntity detailEntity = new DrillWorkDetailEntity();
        detailEntity.setTotalLength(BigDecimal.ONE);
        detailEntity.setOrderNumber(1L);
        detailEntity.setCode("0001");

        entity.setDrillWorkDetail(Arrays.asList(detailEntity));

        drillWorkRepository.save(entity);


    }

}
