package com.joy.xxfy.informationaldxn.staff.domain.enetiy;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.joy.xxfy.informationaldxn.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationaldxn.staff.domain.enums.AccountCharacterEnum;
import com.joy.xxfy.informationaldxn.staff.domain.enums.EducationEnum;
import com.joy.xxfy.informationaldxn.staff.domain.enums.InsuredEnum;
import com.joy.xxfy.informationaldxn.staff.domain.enums.SexEnum;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@ToString(callSuper = true)
@Where(clause = "is_delete = 0")
@Table(name = "all_staff_personal", uniqueConstraints = {@UniqueConstraint(columnNames="idNumber")})//身份证设置唯一性约束
public class StaffPersonalEntity extends BaseEntity {

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
}
