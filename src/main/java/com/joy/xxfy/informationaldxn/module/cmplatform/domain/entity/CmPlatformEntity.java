package com.joy.xxfy.informationaldxn.module.cmplatform.domain.entity;

import com.joy.xxfy.informationaldxn.module.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationaldxn.module.common.enums.CommonStatusEnum;
import com.joy.xxfy.informationaldxn.module.user.domain.entity.UserEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "all_cm_platform")
@Data
@ToString(callSuper = true)
@Where(clause = "is_delete = 0")
public class CmPlatformEntity extends BaseEntity {
    /**
     * 平台的名称
     */
    @NotNull(message = "名称不能为空")
    private String cmName;

    /**
     * 对应的管理账户
     */
    @NotNull
    @ManyToOne(cascade = {},fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    /**
     * 状态 0-停用 1-启用
     */
    private CommonStatusEnum status;

}
