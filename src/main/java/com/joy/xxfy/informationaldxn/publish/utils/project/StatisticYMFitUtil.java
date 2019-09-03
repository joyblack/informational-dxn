package com.joy.xxfy.informationaldxn.publish.utils.project;

import com.joy.xxfy.informationaldxn.module.safe.domain.vo.PerMonthStringTotalCountVo;
import com.joy.xxfy.informationaldxn.module.safe.domain.vo.PerMonthTotalCountVo;
import com.joy.xxfy.informationaldxn.publish.utils.format.FormatToStringValueUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 给不存在的年月补全0
 */
public class StatisticYMFitUtil {
    public static List<PerMonthTotalCountVo> format(List<PerMonthTotalCountVo> data){
        List<PerMonthTotalCountVo> result = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            long ct = 0;
            for (PerMonthTotalCountVo datum : data) {
                if(datum.getMonth().equals(month)){
                    ct = datum.getCt();
                    data.remove(datum);
                    break;
                }
            }
            result.add(new PerMonthTotalCountVo(month, ct));
        }
        return result;
    }


}
