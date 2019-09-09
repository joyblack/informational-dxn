package com.joy.xxfy.informationaldxn.module.device.domain.entity;

import com.joy.xxfy.informationaldxn.module.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

/**
 * 设备分类
 */
@Entity
@Table(name = "device_category")
@Data
@ToString(callSuper = true)
@Where(clause = "is_delete = 0")
public class DeviceCategoryEntity extends BaseEntity {
    /**
     * 所属平台
     */
    @JoinColumn(name = "company_id")
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    private DepartmentEntity belongCompany;


    /**
     * 名称
     */
    private String categoryName;

    /**
     * 父节点
     */
    @Column(nullable = false)
    private Long parentId;


    /**
     * 是否是父节点，父节点不能作为分类值录入
     */
    private Boolean isParent;

    /**
     * 路径信息
     */
    @Lob
    private String path;

    @Transient
    private List<DeviceCategoryEntity> children;



}
