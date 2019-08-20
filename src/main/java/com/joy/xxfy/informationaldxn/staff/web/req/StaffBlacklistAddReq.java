package com.joy.xxfy.informationaldxn.staff.web.req;

import com.joy.xxfy.informationaldxn.common.domain.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ToString(callSuper = true)
public class StaffBlacklistAddReq extends BaseEntity {
    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    private String username;

    /**
     * 身份证号码
     */
    @NotBlank(message = "身份证号码不能为空")
    private String idNumber;


    /**
     * 工伤原因
     */
    @Lob
    @NotBlank(message = "原因不能为空")
    private String blacklistReasons;

    /**
     * 自动解禁日期
     */
    private Date overTime;

}
