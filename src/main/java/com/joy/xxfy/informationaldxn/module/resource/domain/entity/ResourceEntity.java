package com.joy.xxfy.informationaldxn.module.resource.domain.entity;

import com.joy.xxfy.informationaldxn.module.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationaldxn.module.resource.domain.enums.ResourceType;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

/**
 * 资源（权限）
 */
@Entity
@Table(name = "all_resource")
@Data
@ToString(callSuper = true)
@Where(clause = "is_delete = 0")
public class ResourceEntity extends BaseEntity {

    /**
     * 资源URL
     */
    private String resourceUrl;

    /**
     * 资源类型 => ResourceType 0-菜单 1-按钮
     */
    @Column(nullable = false)
    private ResourceType resourceType;

    /**
     * 资源名称
     */
    @Column(nullable = false)
    private String resourceName;

    /**
     * 资源别称
     */
    @Column(nullable = false)
    private String resourceAliasName;

    /**
     * 父节点ID
     */
    @Column(nullable = false)
    private Long parentId;

    /**
     * 路径
     */
    @Lob
    @Column(nullable = false)
    private String path;

    /**
     * 子资源信息
     */
    @Transient
    private List<ResourceEntity> children;

}