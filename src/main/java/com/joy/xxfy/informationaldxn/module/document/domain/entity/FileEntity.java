package com.joy.xxfy.informationaldxn.module.document.domain.entity;

import com.joy.xxfy.informationaldxn.module.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.document.domain.enums.PermissionTypeEnum;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 资料
 */
@Entity
@Table(name = "document_pan_file")
@Data
@ToString(callSuper = true)
@Where(clause = "is_delete = 0")
public class FileEntity extends BaseEntity {

    /**
     * 创建者
     */
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "create_user_id")
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
     * 新名称
     */
    @Column(nullable = false)
    private String newFileName;

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

    /**
     * 子文件夹列表
     */
    @Transient
    private List<FileEntity> children = new ArrayList<>();

}
