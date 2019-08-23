package com.joy.xxfy.informationaldxn;

import cn.hutool.json.JSONUtil;
import com.joy.xxfy.informationaldxn.module.staff.domain.enetiy.StaffLeaveEntity;
import com.joy.xxfy.informationaldxn.module.staff.domain.enums.LeaveTypeEnum;
import org.junit.Test;

import java.util.Date;

public class JsonTests {

    @Test
    public void contextLoads() {
        StaffLeaveEntity obj = new StaffLeaveEntity();
        obj.setLeaveType(LeaveTypeEnum.NORMAL);
        obj.setLeaveTime(new Date());
        System.out.println(JSONUtil.toJsonPrettyStr(obj));
    }

}
