package com.joy.xxfy.informationaldxn.staff.web.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.joy.xxfy.informationaldxn.common.web.req.BaseAddReq;
import com.joy.xxfy.informationaldxn.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.staff.domain.enums.*;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ToString
public class StaffEntryAddReq extends BaseAddReq {
    /**
     * 所属公司、平台，其实也是一个部门信息
     */
    @NotNull(message = "公司/煤矿平台信息不能为空")
    private Long companyId;

    /**
     * 所属部门
     */
    @NotNull(message = "入职部门不能为空")
    private Long departmentId;

    /**
     * 拥有职位信息
     */
    @NotNull(message = "职位信息不能为空")
    private Long positionId;

    /**
     * 入职时间
     */
    @NotNull(message = "入职时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date entryTime;


    /**
     * 体检医院
     */
    private String physicalExaminationHospital;

    /**
     * 体检时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
     * 员工状态
     */
    private StaffStatusEnum staffStatus = StaffStatusEnum.INCUMBENCY;

    /**
     * 银行卡号
     */
    private String bankNumber;

    /**
     * 开户行
     */
    private String openBank;


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
    private Long reviewUserId;


    /**
     * 身份证
     */
    @NotEmpty(message = "身份证号不能为空")
    private String idNumber;

    /**
     * 姓名
     */
    @NotEmpty(message = "姓名不能为空")
    private String username;

    /**
     * 性别
     */
    @NotNull(message = "性别不能为空")
    private SexEnum sex;

    /**
     * 民族
     */
    @NotEmpty(message = "民族不能为空")
    private String nationality;

    /**
     * 出生日期
     */
    @NotNull(message = "出生日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date birthDate;

    /**
     * 学历
     */
    private EducationEnum education = EducationEnum.BACHELOR;

    /**
     * 户口性质 0-农村户口 1-城市户口
     */
    private AccountCharacterEnum accountCharacter = AccountCharacterEnum.COUNTRYSIDE_CHARACTER;

    /**
     * 联系电话
     */
    @NotEmpty(message = "联系电话不能为空")
    @Column(nullable = false)
    private String phone;

    /**
     * 家庭住址
     */
    private String homeAddress;

    /**
     * 参保状态 0-否 1-是 2-已停止
     */
    private InsuredEnum insured = InsuredEnum.NO;

    /**
     * 参保时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date insuredTime;

    /**
     * 籍贯
     */
    private String nativePlace;

    /**
     * 毕业院校
     */
    private String graduationCollege;

    /**
     * 毕业时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date graduationTime;

    /**
     * 专业
     */
    private String profession;

    /**
     * 身份证照片
     */
    private Long idNumberPhotoId;

    /**
     * 身份证照片
     */
    private Long oneInchPhotoId;
}
