package com.joy.xxfy.informationaldxn.module.document.domain.entity;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.joy.xxfy.informationaldxn.module.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationaldxn.module.common.enums.CommonYesEnum;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.document.domain.enums.LicenceTypeEnum;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 许可证（证照）
 */
@Entity
@Table(name = "document_licence")
@Data
@ToString(callSuper = true)
@Where(clause = "is_delete = 0")
public class LicenceEntity extends BaseEntity {
    /**
     * 所属煤矿平台
     */
    @JoinColumn(name = "belong_company_id")
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    private DepartmentEntity belongCompany;

    /**
     * 证件类型
     */
    @Column(nullable = false)
    private LicenceTypeEnum licenceType;

    /**
     * 证号
     */
    private String licenceNumber;

    /**
     * 有效期限
     */
    private Date expiryTime;

    /**
     * 有效期到期剩余天数
     */
    @Transient
    private Long leftDays;

    /**
     * 发证机关
     */
    private String issueOffice;

    /**
     * 颁证日期
     */
    private Date issueTime;


    /**
     * 有效期超时前提示天数，默认30天
     */
    private Long tipDays;

    /**
     * 提示证照有效期快要临近的开始时间，其值等于证照有效期 - 提示天数
     */
    private Date tipStartTime;

    /* 如下是采矿许可证多出来的属性 */
    /**
     * 矿区面积（km2）
     */
    private BigDecimal mineArea;

    /**
     * 矿井规模（万吨/年）
     */
    private BigDecimal mineScale;

    /**
     * 准采标高
     */
    private String mineElevation;

    /**
     * 计算剩余天数
     */
    public Long getLeftDays() {
        if(expiryTime == null){
            return null;
        }
        return DateUtil.between(expiryTime, new Date(), DateUnit.DAY);
    }
}
