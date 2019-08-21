package com.joy.xxfy.informationaldxn.produce.web.req;

import com.joy.xxfy.informationaldxn.common.web.req.BaseAddReq;
import com.joy.xxfy.informationaldxn.common.web.req.BaseUpdateReq;
import com.joy.xxfy.informationaldxn.produce.domain.entity.DrillWorkDetailEntity;
import com.joy.xxfy.informationaldxn.produce.domain.enums.DrillCategoryEnum;
import com.joy.xxfy.informationaldxn.produce.domain.enums.DrillRockCharacterEnum;
import com.joy.xxfy.informationaldxn.produce.domain.enums.DrillTypeEnum;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

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

    /**
     * 关联的钻孔工作详情
     */
    @NotEmpty(message = "至少添加一条钻孔工作详情记录")
    private List<DrillWorkDetailEntity> drillWorkDetail;

}
