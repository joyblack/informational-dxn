package com.joy.xxfy.informationaldxn.position.domain.entity;

import com.joy.xxfy.informationaldxn.common.domain.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;

@Entity
@Table(name = "all_position")
@Data
@ToString
@SQLDelete(sql = "update all_position set is_delete = 1 where id = ?")
@Where(clause = "is_delete = 0")
public class PositionEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 5615405659867992100L;
    // 职位名称
    private String positionName;

    // 描述信息
    private String describes;

}
