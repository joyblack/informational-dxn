package com.joy.xxfy.informationaldxn.module.drill.web.req;

import com.joy.xxfy.informationaldxn.module.common.web.req.BaseUpdateReq;
import com.joy.xxfy.informationaldxn.module.drill.domain.enums.DrillCategoryEnum;
import com.joy.xxfy.informationaldxn.module.drill.domain.enums.DrillRockCharacterEnum;
import com.joy.xxfy.informationaldxn.module.drill.domain.enums.DrillTypeEnum;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@ToString(callSuper = true)
public class DrillWorkUpdateReq extends BaseUpdateReq {
    /**
     * 钻孔工作名称
     */
    @NotBlank(message = "名称不能为空")
    private String drillWorkName;

    /**
     * 开孔日期
     */
    private Date drillTime;

    /**
     * 钻孔类别
     */
    private DrillCategoryEnum drillCategory;

    /**
     * 钻孔类型
     */
    private DrillTypeEnum drillType;


    /**
     * 钻孔岩性
     */
    private DrillRockCharacterEnum drillRockCharacter;

}
