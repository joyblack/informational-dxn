package com.joy.xxfy.informationaldxn.publish.result;

public enum Notice{



    EXECUTE_IS_SUCCESS("操作成功", 200),
    EXECUTE_IS_FAILED("操作失败", 234),

    // 公共模块
    REQUEST_PARAMETER_IS_ERROR("请求参数缺失或未空值", 100000),
    IDENTITY_NUMBER_ERROR("身份证信息错误", 100001),
    PHONE_ERROR("电话号码错误", 100002),
    PASSWORD_AFFIRM_ERROR("确认密码不匹配", 100003),
    IDENTITY_NUMBER_ALREADY_EXIST("身份证号码已存在", 100005),
    PHONE_ALREADY_EXIST("电话号码已存在", 100006),
    LOGIN_NAME_ALREADY_EXIST("登录名已存在", 100007),
    PASSWORD_ERROR("密码错误", 100008),


    // user
    USER_NOT_LOGIN("用户未登录", 101000),
    USER_NOT_EXIST("用户信息不存在", 101001),


    // department
    DEPARTMENT_NOT_EXIST("部门信息不存在",102000),
    DEPARTMENT_PARENT_NOT_EXIST("父部门信息不存在", 102001),
    DEPARTMENT_NAME_ALREADY_EXIST("部门名称已存在", 102002),
    COMPANY_NOT_EXIST("平台/公司信息不存在",102003),

    // staff
    STAFF_ENTRY_NOT_EXIST("员工入职信息不存在", 103000 ),
    STAFF_REVIEW_STATUS_MUST_BE_SELECT("请选择审核通过或者不通过",103003),
    ID_NUMBER_PHOTO_IS_ILLEGAL("身份证照片非法", 103004 ),
    ONE_INCH_PHOTO_IS_ILLEGAL("一寸照片非法", 103005 ),
    STAFF_LEAVE_NOT_EXIST("员工离职信息不存在", 103000 ),


    // position
    POSITION_NOT_EXIST("职位信息不存在", 104000),
    POSITION_NAME_ALREADY_EXIST("职位名称已存在", 104001),
    STAFF_ALREADY_IN_DEPARTMENT("员工已经存在于此部门下", 103001),

    // store
    STOREHOUSE_NOT_EXIST("仓库信息不存在", 105000),
    STOREHOUSE_NAME_ALREADY_EXIST("仓库名称已存在", 105001),

    // cm platform
    CM_PLATFORM_NOT_EXIST("煤矿平台信息不存在", 106000),
    CM_PLATFORM_NAME_ALREADY_EXIST("煤矿平台名称已存在", 106001),

    // resource
    RESOURCE_NOT_EXIST("资源信息不存在", 107000),
    RESOURCE_PARENT_NOT_EXIST("父资源信息不存在", 107001),
    RESOURCE_NAME_ALREADY_EXIST("资源名称已存在", 107001),

    // file
    UPLOAD_FILE_IS_NULL("上传文件为空", 108000),
    UPLOAD_FILE_MODULE_IS_NULL("上传文件模块为空", 108001),
    UPLOAD_FILE_MODULE_ERROR("上传文件模块错误，不存在此模块", 108002),
    UPLOAD_FILE_ERROR("上传文件错误", 108003),
    UPLOAD_FILE_FAILED("上传文件失败", 108004),
    ;



    private String message;
    private int code;

    Notice(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
