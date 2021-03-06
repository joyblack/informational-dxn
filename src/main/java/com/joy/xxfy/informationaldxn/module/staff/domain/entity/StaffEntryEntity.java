package com.joy.xxfy.informationaldxn.module.staff.domain.entity;

import com.joy.xxfy.informationaldxn.module.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.staff.domain.enums.ReviewStatusEnum;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@ToString(callSuper = true)
@Table(name = "staff_entry")//身份证设置唯一性约束
@Where(clause = "is_delete = 0")
public class StaffEntryEntity extends BaseEntity {
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
     * 入职时间
     */
    @NotNull(message = "入职时间不能为空")
    private Date entryTime;


    /**
     * 体检医院
     */
    private String physicalExaminationHospital;

    private Date physicalExaminationTime;

    /**
     * 薪酬(元/月)
     */
    private Long remuneration;

    /**
     * 薪酬（元/量）
     */
    private Long remunerationL;


    /**
     * 银行卡号
     */
    private String bankNumber;

    /**
     * 开户行
     */
    private String openBank;


    /**
     * 全局的个人信息
     */
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "staff_personal_id")
    @NotNull(message = "员工个人信息不能为空")
    private StaffPersonalEntity staffPersonal;


    /**
     * 审核状态：待审核 审核通过 审核未通过
     */
    private ReviewStatusEnum reviewStatus = ReviewStatusEnum.PASS;

    /**
     * 审核原因
     */
    private String reviewReasons;

    /**
     * 审核备注
     */
    private String reviewRemarks;


    /**
     * 审核时间,初始状态是为空，审核员审核之后生成该时间
     */
    private Date reviewTime;


    /**
     * 审核人的信息
     */
    @OneToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "review_user_id")
    private UserEntity reviewUser;
}
