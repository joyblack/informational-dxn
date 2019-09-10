package com.joy.xxfy.informationaldxn;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.joy.xxfy.informationaldxn.publish.constant.ExportConstant;
import org.junit.Test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ExcelTests {

    @Test
    public void excel() {
        String s = "   <br><br> 1";
        System.out.println(s.lastIndexOf("<br>"));
        String s1 = s.substring(0, s.lastIndexOf("<br>"));
        System.out.println(s1);
//        // 日期导出格式
//        SimpleDateFormat dateFormat = new SimpleDateFormat(ExportConstant.DATE_FORMAT);
//        List<List<String>> rows = new ArrayList<List<String>>();
//
//        // rows.add(CollUtil.newArrayList("合计", "", "", "",regularAttendanceEntity.getTotal().toString(),"" ));
//        ExcelWriter writer = ExcelUtil.getWriter();
//        writer.merge(12,"贵源煤矿二号井生产日报表  2019.06.03");
//        writer.merge(1,2,0,0,"掘进面名称",true);
//        writer.merge(1,1,1,4,"掘进进尺",true);
//        writer.merge(1,2,5,5,"月累计进尺",true);
//        writer.merge(1,1,6,9,"入井人数",true);
//        writer.merge(1,2,10,10,"今日产煤（吨）",true);
//        writer.merge(1,2,11,11,"月累计产煤（吨）",true);
//        writer.setCurrentRow(writer.getCurrentRow() + 1);
//        writer.writeRow(CollUtil.newArrayList("","早班","中班","晚班","圆班","","早班","中班","晚班","圆班"));
//        writer.write(rows,true);
//        writer.flush(new File("员工入职信息表"+ System.currentTimeMillis() +".xls"));
    }

    




}
