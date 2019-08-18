package com.joy.xxfy.informationaldxn.user.domain.entity;

import com.joy.xxfy.informationaldxn.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationaldxn.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.user.domain.enums.UserStatusEnum;
import com.joy.xxfy.informationaldxn.user.domain.enums.UserTypeEnum;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "all_user")
@Data
@ToString
@SQLDelete(sql = "update all_user set is_delete = 1 where id = ?")
@Where(clause = "is_delete = 0")
public class UserEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 2015171475599371602L;

    private String idNumber;

    @NotEmpty(message = "登陆名不能为空")
    @Column(nullable = false)
    private String loginName;

    @NotEmpty(message = "电话号码不能为空")
    @Column(nullable = false)
    private String phone;

    private String password;

    @Transient
    private String affirmPassword;

    /**
     * 真实姓名
     */
    private String username;

    /**
     * 用户类型
     */
    private UserTypeEnum userType = UserTypeEnum.CM_COMMON;

    /**
     * 用户状态
     */
    private UserStatusEnum userStatus = UserStatusEnum.START;


    /**
     * 资源列表
     */
    private String permissions;

    /**
     * 部门信息
     */
    @NotNull(message = "部门信息不能为空")
    @JoinColumn(name = "department_id")
    @ManyToOne(cascade = {},fetch = FetchType.EAGER)
    private DepartmentEntity department;

}
