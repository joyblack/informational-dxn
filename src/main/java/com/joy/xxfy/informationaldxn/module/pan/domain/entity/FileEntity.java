package com.joy.xxfy.informationaldxn.module.pan.domain.entity;

import com.joy.xxfy.informationaldxn.module.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.pan.domain.enums.PermissionTypeEnum;
import com.joy.xxfy.informationaldxn.module.user.domain.entity.UserEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * 资料
 */
@Entity
@Table(name = "pan_file")
@Data
@ToString(callSuper = true)
@Where(clause = "is_delete = 0")
public class FileEntity extends BaseEntity {

    /**
     * 创建者
     */
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "upload_user_id", nullable = false)
    private UserEntity createUser;

    /**
     * 所属平台
     */
    @JoinColumn(name = "belong_company_id")
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    private DepartmentEntity belongCompany;

    /**
     * 父文件夹ID
     */
    @Column(nullable = false)
    private Long parentId;


    /**
     * 原始名称
     */
    @Column(nullable = false)
    private String originalName;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文件格式
     */
    private String fileType;

    /**
     * 文件路径
     */
    @Lob
    @Column(nullable = false)
    private String path;

    /**
     * 存储路径
     */
    @Lob
    @Column(nullable = false)
    private String storePath;

    /**
     * 文件权限类型
     */
    private PermissionTypeEnum permissionType;

    /**
     * 是否是文件夹
     */
    private Boolean isFolder;

}
