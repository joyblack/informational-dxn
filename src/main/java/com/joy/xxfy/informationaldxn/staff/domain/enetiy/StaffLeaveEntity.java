package com.joy.xxfy.informationaldxn.staff.domain.enetiy;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.joy.xxfy.informationaldxn.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationaldxn.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.position.domain.entity.PositionEntity;
import com.joy.xxfy.informationaldxn.staff.domain.enums.LeaveTypeEnum;
import com.joy.xxfy.informationaldxn.staff.domain.enums.ReviewStatusEnum;
import com.joy.xxfy.informationaldxn.staff.domain.enums.StaffStatusEnum;
import com.joy.xxfy.informationaldxn.user.domain.entity.UserEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 离职表
 */
@Entity
@Data
@ToString(callSuper = true)
@Table(name = "staff_leave")
@Where(clause = "is_delete = 0")
public class StaffLeaveEntity extends BaseEntity {
    /**
     * 所属公司、平台，其实也是一个部门信息
     */
    @NotNull(message = "公司/煤矿平台信息不能为空")
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private DepartmentEntity company;

    /**
     * 所属部门
     */
    @NotNull(message = "入职部门不能为空")
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    private DepartmentEntity department;

    /**
     * 拥有职位信息
     */
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "position_id")
    @NotNull(message = "职位信息不能为空")
    private PositionEntity position;

    /**
     * 离职时间
     */
    @NotNull(message = "离职时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date leaveTime;

    /**
     * 全局的个人信息
     */
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "staff_personal_id")
    @NotNull(message = "员工个人信息不能为空")
    private StaffPersonalEntity staffPersonal;


    /**
     * 离职类型
     */
    private LeaveTypeEnum leaveType;
}
