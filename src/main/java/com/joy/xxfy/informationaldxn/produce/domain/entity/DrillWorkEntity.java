package com.joy.xxfy.informationaldxn.produce.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.joy.xxfy.informationaldxn.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationaldxn.produce.domain.enums.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 钻孔工作
 */
@Entity
@Table(name = "produce_drill_work")
@Data
@ToString(callSuper = true)
@Where(clause = "is_delete = 0")
public class DrillWorkEntity extends BaseEntity {

    /**
     * 钻孔工作名称
     */
    @Column(nullable = false)
    private String drillWorkName;

    /**
     * 开孔日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date drillTime;

    /**
     * 钻孔类别
     */
    private DrillCategoryEnum drillCategory;

    /**
     * 钻孔类型
     */
    private DrillTypeEnum drillType;


    /**
     * 钻孔岩性
     */
    private DrillRockCharacterEnum drillRockCharacter;

    /**
     * 关联的钻孔工作详情
     */
    @OneToMany(mappedBy = "drillWork",fetch = FetchType.EAGER, cascade = {})
    private List<DrillWorkDetailEntity> drillWorkDetail;

}
