package com.joy.xxfy.informationaldxn;

import cn.hutool.json.JSONUtil;
import com.joy.xxfy.informationaldxn.module.staff.domain.enetiy.StaffLeaveEntity;
import com.joy.xxfy.informationaldxn.module.staff.domain.enums.LeaveTypeEnum;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;

public class JsonTests {

    @Test
    public void contextLoads() {
        String s = "1-2-3";
        String[] split = s.split("-");

        System.out.println(Arrays.asList(split));
    }

}
