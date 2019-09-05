package com.joy.xxfy.informationaldxn.module.system.web.req;

import com.joy.xxfy.informationaldxn.module.common.enums.CommonStatusEnum;
import com.joy.xxfy.informationaldxn.module.common.web.req.BaseAddReq;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.enums.UserTypeEnum;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ToString
public class UserAddReq extends BaseAddReq {

    @NotBlank(message = "登陆名不能为空")
    private String loginName;

    @NotBlank(message = "电话号码不能为空")
    private String phone;

    /**
     * 部门信息
     */
    @NotNull(message = "部门信息不能为空")
    private Long departmentId;

    private String password;

    private String affirmPassword;

    /**
     * 真实姓名
     */
    private String username;

    /**
     * 身份证号
     */
    private String idNumber;



}
