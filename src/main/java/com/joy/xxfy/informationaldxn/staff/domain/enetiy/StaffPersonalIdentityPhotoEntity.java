package com.joy.xxfy.informationaldxn.staff.domain.enetiy;

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
@Table(name = "staff_personal_identity_photo")
@Data
@ToString(callSuper = true)
@Where(clause = "is_delete = 0")
public class StaffPersonalIdentityPhotoEntity extends BaseEntity {
    // 存储路径
    @Lob
    @Column(nullable = false)
    private String storePath;

    // 名称
    private String fileName;

    // 大小
    private Long fileSize;

    // 所属的个人信息
    // ...



}
