package com.joy.xxfy.informationaldxn.module.pan.domain.entity;

import com.joy.xxfy.informationaldxn.module.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
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

    // 所属平台
    @JoinColumn(name = "belong_company_id")
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    private DepartmentEntity belongCompany;



    // 原始名称
    @Column(nullable = false)
    private String originalName;

    // 名称
    private String fileName;

    // 大小
    private Long fileSize;

    // 文件格式
    private String fileType;

    // 文件路径
    @Lob
    @Column(nullable = false)
    private String path;

    // 存储路径
    @Lob
    @Column(nullable = false)
    private String storePath;













}
