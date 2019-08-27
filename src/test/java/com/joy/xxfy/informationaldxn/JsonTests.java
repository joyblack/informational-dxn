package com.joy.xxfy.informationaldxn;

import cn.hutool.json.JSONUtil;
import com.joy.xxfy.informationaldxn.module.staff.domain.enetiy.StaffLeaveEntity;
import com.joy.xxfy.informationaldxn.module.staff.domain.enums.LeaveTypeEnum;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;

public class JsonTests {

    @Test
    public void contextLoads() {
        // 获取前月的第一天
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cale = Calendar.getInstance();
        cale.setTime(new Date());
        //cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        System.out.println(format.format(cale.getTime()));
    }




}
