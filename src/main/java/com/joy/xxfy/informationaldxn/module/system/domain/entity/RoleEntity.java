package com.joy.xxfy.informationaldxn.module.system.domain.entity;

import com.joy.xxfy.informationaldxn.module.common.domain.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 角色
 */
@Entity
@Table(name = "system_role")
@Data
@ToString
@Where(clause = "is_delete = 0")
public class RoleEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -873520780965494950L;

    /**
     * 所属平台
     */
    @ManyToOne(cascade = {},fetch = FetchType.EAGER)
    @JoinColumn(name = "belong_company_id")
    private DepartmentEntity belongCompany;

    /**
     * 角色名称
     */
    @Column(nullable = false)
    private String roleName;

    /**
     * 资源ID列表
     */
    @Lob
    private String permissions;
}
