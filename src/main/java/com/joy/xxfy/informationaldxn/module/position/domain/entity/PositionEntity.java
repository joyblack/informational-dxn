package com.joy.xxfy.informationaldxn.module.position.domain.entity;

import com.joy.xxfy.informationaldxn.common.domain.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "all_position")
@Data
@ToString(callSuper = true)
@Where(clause = "is_delete = 0")
public class PositionEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 5615405659867992100L;
    // 职位名称
    @Column(nullable = false)
    private String positionName;

    // 描述信息
    private String describes;

}
