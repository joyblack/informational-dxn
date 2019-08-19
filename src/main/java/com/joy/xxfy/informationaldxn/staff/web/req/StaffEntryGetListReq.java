package com.joy.xxfy.informationaldxn.staff.web.req;

import com.joy.xxfy.informationaldxn.common.web.req.BasePageReq;
import com.joy.xxfy.informationaldxn.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.position.domain.entity.PositionEntity;
import com.joy.xxfy.informationaldxn.staff.domain.enums.EducationEnum;
import com.joy.xxfy.informationaldxn.staff.domain.enums.ReviewStatusEnum;
import com.joy.xxfy.informationaldxn.staff.domain.enums.SexEnum;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString(callSuper = true)
public class StaffEntryGetListReq extends BasePageReq {
    /**
     * 姓名
     */
    private String username;

    /**
     * 身份证
     */
    private String idNumber;

    /**
     * 联系方式
     */
    private String phone;



    /**
     * 出生日期截止
     */
    private Date birthDateStart;
    private Date birthDateEnd;

    /**
     * 性别
     */
    private SexEnum sex;

    /**
     * 民族
     */
    private String nationality;

    /**
     * 学历
     */
    private EducationEnum education;


    /**
     * 入职时间范围
     */
    private Date entryTimeStart;
    private Date entryTimeEnd;

    /**
     * 审核状态
     */
    private ReviewStatusEnum ReviewStatus;

    /**
     * 入职部门
     */
    private Long departmentId;

    /**
     * 入职的职位
     */
    private Long positionId;
}
