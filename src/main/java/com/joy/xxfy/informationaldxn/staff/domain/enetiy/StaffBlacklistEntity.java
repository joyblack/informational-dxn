package com.joy.xxfy.informationaldxn.staff.domain.enetiy;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.joy.xxfy.informationaldxn.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationaldxn.department.domain.entity.DepartmentEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 黑名单
 */
@Entity
@Data
@ToString(callSuper = true)
@Table(name = "staff_blacklist")
@Where(clause = "is_delete = 0")
public class StaffBlacklistEntity extends BaseEntity {
    /**
     * 全局的个人信息
     */
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "staff_personal_id")
    @NotNull(message = "员工个人信息不能为空")
    private StaffPersonalEntity staffPersonal;


    /**
     * 所在的煤矿平台（最后一个离职岗位）
     */
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "comany_id")
    @NotNull(message = "员工所在的煤矿/平台信息不能为空")
    private DepartmentEntity company;


    /**
     * 加入黑名单的原因
     */
    @Lob
    @Column(nullable = false)
    private String blacklistReasons;

    /**
     * 解禁的日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date overTime;

}
