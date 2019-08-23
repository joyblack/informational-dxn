package com.joy.xxfy.informationaldxn.module.staff.web.req;

import com.joy.xxfy.informationaldxn.common.web.req.BaseUpdateReq;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ToString(callSuper = true)
public class StaffInjuryUpdateReq extends BaseUpdateReq {
    /**
     * 工伤原因
     */
    @Lob
    @NotBlank(message = "工伤原因不能为空")
    private String injuryReasons;

    /**
     * 工伤时间
     */
    @NotNull(message = "工伤时间不能为空")
    private Date InjuryTime;

    /**
     * 工伤描述
     */
    private String injuryDescribes;


    /**
     * 主治医院
     */
    private String hospital;

    /**
     * 医治时间
     */
    private Date treatTime;

}
