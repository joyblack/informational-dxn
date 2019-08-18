package com.joy.xxfy.informationaldxn.department.domain.entity;

import com.joy.xxfy.informationaldxn.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationaldxn.department.domain.enums.DepartmentTypeEnum;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity(name = "all_department")
@Data
@ToString
@SQLDelete(sql = "update all_department set is_delete = 1 where id = ?")
@Where(clause = "is_delete = 0")
public class DepartmentEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 4751065577213406424L;
    /**
     * 部门名称
     */
    @NotEmpty(message = "部门名称不能为空")
    private String departmentName;

    /**
     * 编码
     */
    private String code;

    /**
     * 父节点部门信息
     */
    @NotNull(message = "父部门不能为空")
    private Long parentId;

    /**
     * 负责人
     */
    private String responseUser;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 部门类型:默认为煤矿平台所属部门
     */
    private DepartmentTypeEnum departmentType = DepartmentTypeEnum.CM_PLATFORM;

    /**
     * 部门路径
     */
    private String path;

    /**
     * 子部门
     */
    @Transient
    private List<DepartmentEntity> children;
}
