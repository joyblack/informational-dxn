package com.joy.xxfy.informationaldxn.module.common.constant;

/**
 * Excel导入常量配置
 */
public class ExcelImportConstant {
    /**
     * 员工导入字段映射列信息
     */
    //private String[] fieldNames = {"存档号", "姓名", "性别", "民族", "出生年月", "身份证号", "文化程度", "户口性质", "联系电话", "家庭住址", "部门/队组", "职务/工种", "入职时间", "参保状态", "参保时间", "体检医院", "体检时间", "备注"};
    public static final  String[] STAFF_EXCEL_COLUMN_NAMES = {"存档号", "姓名", "性别", "民族", "出生年月", "身份证号", "文化程度", "户口性质", "联系电话", "家庭住址", "队组", "工种", "入职时间", "参保状态", "体检医院", "体检时间", "备注"};
    public static final  String[] STAFF_VO_FIELDS = {"archive", "name", "sex", "nationality", "birthDate", "idNumber", "education", "accountCharacter", "phone", "homeAddress", "department", "position", "entryTime", "insured", "physicalExaminationHospital", "physicalExaminationTime", "remarks"};



    /**
     * excel导入时间格式
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";
}
