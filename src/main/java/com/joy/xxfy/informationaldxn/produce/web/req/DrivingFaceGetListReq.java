package com.joy.xxfy.informationaldxn.produce.web.req;

import com.joy.xxfy.informationaldxn.common.web.req.BasePageReq;
import com.joy.xxfy.informationaldxn.produce.domain.enums.CrossSectionTypeEnum;
import com.joy.xxfy.informationaldxn.produce.domain.enums.DrivingTechnologyTypeEnum;
import com.joy.xxfy.informationaldxn.produce.domain.enums.RockCharacterEnum;
import com.joy.xxfy.informationaldxn.produce.domain.enums.SupportMethodEnum;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString(callSuper = true)
public class DrivingFaceGetListReq extends BasePageReq {

    /**
     * 名称
     */
    private String drivingFaceName;

    /**
     * 开掘日期起止
     */
    private Date startTimeStart;
    private Date startTimeEnd;


    /**
     * 断面形式
     */
    private CrossSectionTypeEnum crossSectionType;


    /**
     * 掘进工艺
     */
    private DrivingTechnologyTypeEnum drivingTechnologyType;

    /**
     * 支护方式
     */
    private SupportMethodEnum supportMethod;

    /**
     * 岩性
     */
    private RockCharacterEnum rockCharacter;

}
