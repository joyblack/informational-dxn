package com.joy.xxfy.informationaldxn;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.joy.xxfy.informationaldxn.module.common.enums.LimitUserTypeEnum;
import com.joy.xxfy.informationaldxn.module.staff.domain.enetiy.StaffEntryEntity;
import com.joy.xxfy.informationaldxn.module.staff.domain.enetiy.StaffLeaveEntity;
import com.joy.xxfy.informationaldxn.module.staff.domain.enums.LeaveTypeEnum;
import com.joy.xxfy.informationaldxn.module.staff.web.req.StaffEntryGetListReq;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.publish.constant.ExportConstant;
import com.joy.xxfy.informationaldxn.publish.exception.JoyException;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.excel.ExportUtil;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
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
