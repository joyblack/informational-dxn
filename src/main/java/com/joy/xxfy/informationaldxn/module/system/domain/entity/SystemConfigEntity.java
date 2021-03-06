package com.joy.xxfy.informationaldxn.module.system.domain.entity;

import com.joy.xxfy.informationaldxn.module.common.domain.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "system_config")
@Data
@ToString(callSuper = true)
@Where(clause = "is_delete = 0")
public class SystemConfigEntity extends BaseEntity {

    @NotNull
    @Column(nullable = false)
    private String configName;

    @NotNull
    @Column(nullable = false)
    private String configValue;
}

