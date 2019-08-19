package com.joy.xxfy.informationaldxn.file.domain.entity;

import com.joy.xxfy.informationaldxn.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationaldxn.file.enums.UploadModuleEnums;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "all_system_file")
@Data
@ToString(callSuper = true)
@Where(clause = "is_delete = 0")
public class SystemFileEntity extends BaseEntity {
    // 文件上传模块
    @Column(nullable = false)
    private UploadModuleEnums uploadModule;

    // 存储路径
    @Lob
    @Column(nullable = false)
    private String storePath;

    // 名称
    private String fileName;

    // 大小
    private Long fileSize;




}
