package com.joy.xxfy.informationaldxn.module.staff.domain.enetiy;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.joy.xxfy.informationaldxn.module.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 职位调动
 */
@Entity
@Data
@ToString(callSuper = true)
@Table(name = "staff_shift")
@Where(clause = "is_delete = 0")
public class StaffShiftEntity extends BaseEntity {
    /**
     * 全局的个人信息
     */
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "staff_personal_id")
    @NotNull(message = "员工个人信息不能为空")
    private StaffPersonalEntity staffPersonal;

    /**
     * 调动时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date shiftTime;

    /**
     * 调入的煤矿信息
     */
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "target_company_id")
    private DepartmentEntity targetCompany;

    /**
     * 调入的煤矿部门
     */
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "target_department_id")
    private DepartmentEntity targetDepartment;

    /**
     * 调到的职务/工种
     */
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    private PositionEntity targetPosition;

}
