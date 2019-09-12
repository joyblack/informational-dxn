package com.joy.xxfy.informationaldxn;

import cn.hutool.core.io.resource.ClassPathResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.ResourceEntity;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class JsonTests {

    @Test
    public void contextLoads() {
        InputStream inputStream = new ClassPathResource("resource.json").getStream();

        ObjectMapper mapper = new ObjectMapper();
        try {
            List<ResourceEntity> resourceEntity = mapper.readValue(inputStream, ArrayList.class);

            System.out.println(resourceEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






}
