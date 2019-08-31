package com.joy.xxfy.informationaldxn.publish.constant;

public class ExportConstant {
    // 日期导出格式
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    // 入职信息导出字段
    public static final String[] FIELD_STAFF_ENTRY = {"姓名", "出生日期", "性别", "民族",
            "入职时间", "入职公司/煤矿", "入职部门","入职职务/工种", "联系方式", "审核状态","备注"};

    // 煤矿生产日报标题头
    public static final String[] FIELD_CM_PRODUCE = {"","早班","中班","晚班","圆班","","早班","中班","晚班","圆班"};
    // 煤矿生产日报标题头(短)，打钻部分
    public static final String[] FIELD_CM_PRODUCE_SHORT = {"","早班","中班","晚班","圆班",""};

    // 集团生产日报标题头
    public static final String[] FIELD_CP_PRODUCE = {"","","早班","中班","晚班","","","","早班","中班","晚班","","","","",""};
}
