package com.joy.xxfy.informationaldxn.publish.result;

public enum Notice{
    EXECUTE_IS_SUCCESS("操作成功", 200),
    EXECUTE_IS_FAILED("操作失败", 234),

    PERMISSION_FORBIDDEN("权限禁止", 235),
    PERMISSION_ONLY_SUPPORT_CM("仅支持煤矿平台执行此操作", 236),
    PERMISSION_ONLY_SUPPORT_CP("仅支持集团执行此操作", 237),
    IMPORT_FILE_IS_NULL("导入文件为空", 240),
    DATA_IN_USED_CANT_BE_DELETE("该数据正在使用中，不允许删除。", 299),
    DATA_HAS_CHILD_CANT_BE_DELETE("该数据拥有子节点数据，不允许删除。", 299),

    // 公共模块
    REQUEST_PARAMETER_IS_ERROR("请求参数缺失或为空值", 100000),
    IDENTITY_NUMBER_ERROR("身份证信息错误", 100001),
    PHONE_ERROR("电话号码错误", 100002),
    PASSWORD_AFFIRM_ERROR("确认密码不匹配", 100003),
    IDENTITY_NUMBER_ALREADY_EXIST("身份证号码已存在", 100005),
    PHONE_ALREADY_EXIST("电话号码已存在", 100006),
    LOGIN_NAME_ALREADY_EXIST("登录名已存在", 100007),
    PASSWORD_ERROR("密码错误", 100008),
    LOGIN_NAME_PASSWORD_NOT_MATCH("登录名与密码不匹配", 100009),



    // user
    USER_NOT_LOGIN("用户未登录", 101000),
    USER_NOT_EXIST("用户信息不存在", 101001),
    ROLE_NAME_ALREADY_EXIST("角色名称已存在", 102002),
    ROLE_NOT_EXIST("角色信息不存在", 102003),

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
    STAFF_LEAVE_NOT_EXIST("员工离职信息不存在", 103006 ),
    STAFF_PERSONAL_INFO_NOT_EXIST("员工个人信息不存在", 103007),
    STAFF_INJURY_INFO_NOT_EXIST("员工工伤信息不存在", 103008),
    STAFF_BLACKLIST_ALREADY_EXIST("该员工已处于黑名单中", 103008),
    STAFF_STILL_ENTRY("该员工还处于在职状态", 103009),
    STAFF_BLACKLIST_NOT_EXIST("黑名单信息不存在", 103010),
    STAFF_SHIFT_INFO_NOT_EXIST("职位调动信息不存在", 103011),


    // position
    POSITION_NOT_EXIST("职位信息不存在", 104000),
    POSITION_NAME_ALREADY_EXIST("职位名称已存在", 104001),
    POSITION_IN_USED("职位信息正在使用中", 104002),



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
    UPLOAD_FILE_ERROR("上传文件错误", 108001),
    UPLOAD_FILE_FAILED("上传文件失败", 108002),
    UPLOAD_FILE_TYPE_ERROR("文件类型错误", 108003),


    // 掘进工作面
    DRIVING_FACE_NOT_EXIST("掘进工作面信息不存在", 109000),
    DRIVING_FACE_ALREADY_EXIST("掘进工作面信息已存在", 109001),
    DRIVING_FACE_NAME_ALREADY_EXIST("掘进工作面名称已存在", 109002),

    // 回采工作面
    BACK_MINING_NAME_ALREADY_EXIST("回采工作面名称已存在", 110000),
    BACK_MINING_ALREADY_EXIST("回采工作面信息已存在", 110001),
    BACK_MINING_NOT_EXIST("回采工作面信息不存在", 110002),

    // 钻孔工作
    DRILL_WORK_NAME_ALREADY_EXIST("钻孔工作名称已存在", 111000),
    DRILL_WORK_ALREADY_EXIST("钻孔工作信息已存在", 111001),
    DRILL_WORK_NOT_EXIST("钻孔工作信息不存在", 111002),
    DRILL_HOLE_NOT_EXIST("钻孔信息不存在", 111003),
    SET_LENGTH_MORE_LEFT_LENGTH("设置工作长度大于剩余长度", 111004),
    LENGTH_SHOULD_MORE_ZERO("长度不能小于等于0", 111005),
    DAILY_NOT_EXIST("日报信息不存在", 111006),
    DAILY_ALREADY_EXIST("日报信息已存在，添加了重复的日报信息", 111007),
    DAILY_EXIST_CANT_DELETE("已有关联的日报信息，不允许删除", 111010),
    DAILY_EXIST_CANT_EDIT_LENGTH("已有关联的日报信息，不允许修改设计长度", 111011),

    // 培训
    TRAINING_NAME_ALREADY_EXIST("培训名称已存在", 112000),
    TRAINING_ALREADY_EXIST("培训信息已存在", 112001),
    TRAINING_NOT_EXIST("培训信息不存在", 112002),

    // 安全
    SAFE_INSPECTION_NOT_EXIST("安全巡检信息不存在", 112000),
    SAFE_INSPECTION_ALREADY_EXIST("安全巡检信息已存在", 112001),
    VIOLATION_NOT_EXIST("违章信息不存在", 112002),

    // 设备
    DEVICE_CATEGORY_PARENT_NOT_EXIST("父设备类型信息不存在", 113000),
    DEVICE_CATEGORY_NOT_EXIST("设备类型信息不存在", 113001),
    DEVICE_CATEGORY_ALREADY_EXIST("设备类型信息已存在", 113002),
    DEVICE_CATEGORY_NAME_EXIST("设备类型名称已存在", 113003),
    DEVICE_INFO_ALREADY_EXIST("设备信息已存在", 113004),
    DEVICE_INFO_NOT_EXIST("设备信息不存在", 113005),
    DEVICE_INFO_NAME_NOT_EXIST("设备名称不存在", 113006),

    DEVICE_MAINTAIN_ALREADY_EXIST("设备维保信息已存在", 113007),
    DEVICE_MAINTAIN_NOT_EXIST("设备维保信息不存在", 113008),


    // 资料
    PAN_FILE_NOT_EXIST("文件不存在", 114000),
    PAN_FILE_NAME_ALREADY_EXIST("文件名称已存在", 114001),
    PAN_PARENT_FOLDER_NOT_EXIST("父文件夹不存在", 114002),
    NOT_SUPPORT_THIS_PERMISSION_TYPE("不支持此种权限类型", 114003),
    BORROW_INFO_NOT_EXIST("借阅信息不存在", 114004),
    LICENCE_NOT_EXIST("证照信息不存在", 114005),
    LICENCE_ALREADY_EXIST("证照信息已存在", 114006),


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
