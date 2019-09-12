package com.joy.xxfy.informationaldxn.module.common.web.res;

import com.joy.xxfy.informationaldxn.module.common.enums.FillDailyStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FillResultRes {
    private String date;
    private FillDailyStatusEnum info;
}
