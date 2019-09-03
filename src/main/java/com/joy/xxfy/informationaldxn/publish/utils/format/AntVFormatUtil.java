package com.joy.xxfy.informationaldxn.publish.utils.format;

import com.joy.xxfy.informationaldxn.module.common.domain.vo.IkAndBvVo;
import com.joy.xxfy.informationaldxn.module.common.domain.vo.SkAndBvVo;
import com.joy.xxfy.informationaldxn.module.produce.domain.vo.StatisticOutputVo;
import com.joy.xxfy.informationaldxn.module.safe.domain.vo.PerMonthStringTotalCountVo;
import com.joy.xxfy.informationaldxn.module.safe.domain.vo.PerMonthTotalCountVo;
import com.joy.xxfy.informationaldxn.publish.utils.DateUtil;

import java.math.BigDecimal;
import java.util.*;

/**
 * 本工具类目的是将返回数据包装为AntV需求的数据格式
 */
public class AntVFormatUtil {
    /**
     * 月度巡检柱状图,月份使用字符串形式，并添加补全0，以及月这样的信息
     */
    public static List<PerMonthStringTotalCountVo> formatWithKeyString(List<PerMonthTotalCountVo> data){
        List<PerMonthStringTotalCountVo> result = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            long ct = 0;
            for (PerMonthTotalCountVo datum : data) {
                if(datum.getMonth().equals(month)){
                    ct = datum.getCt();
                    data.remove(datum);
                    break;
                }
            }
            result.add(new PerMonthStringTotalCountVo(FormatToStringValueUtil.addLeftZero(month) + " 月", ct));
        }
        return result;
    }

    /**
     * 最近15日采煤
     * 示例数据：
     *   var data = [{
     *     name: '掘进',
     *     '2-1.': 18.9,
     *     '2-2.': 28.8,
     *     ...
     *   }, {
     *     name: '回采',
     *     '2-1.': 18.9,
     *     '2-2.': 28.8,
     *     ...
     *   }];
     */
    public static List<Map<String, Object>> formatNear15DayOutput(List<SkAndBvVo> driving, List<SkAndBvVo> mining, Date start){
        List<Map<String, Object>> res = new ArrayList<>();
        // 返回数据
        Map<String, Object> dr = new LinkedHashMap<>();
        Map<String, Object> mi = new LinkedHashMap<>();
        // 图例
        dr.put("name", "掘进");
        mi.put("name", "回采");
        // 数据
        for (int i = 0; i < 15; i++) {
            Date now = DateUtil.addDay(start, i);
            String md = DateUtil.getMDString(now, false); // 5-20
            BigDecimal drV = BigDecimal.ZERO;
            BigDecimal miV = BigDecimal.ZERO;
            for (SkAndBvVo v : driving) {
                if(v.getKey().equals(md)){
                    drV = v.getValue();
                    driving.remove(v);
                    break;
                }
            }
            for (SkAndBvVo v : mining) {
                if(v.getKey().equals(md)){
                    miV = v.getValue();
                    driving.remove(v);
                    break;
                }
            }

            dr.put(md, drV);
            mi.put(md, miV);
        }
        res.add(dr);
        res.add(mi);
        return res;
    }

    /**
     * 月度进尺格式化
     * }, {
     *     "name": "London",
     *     "月份": "Jul.",
     *     "月均降雨量": 24
     * }, {
     *     "name": "London",
     *     "月份": "Aug.",
     *     "月均降雨量": 35.6
     * }, {
     *     "name": "Berlin",
     *     "月份": "Jan.",
     *     "月均降雨量": 12.4
     * }, {
     *     "name": "Berlin",
     *     "月份": "Feb.",
     *     "月均降雨量": 23.2
     * },
     */
    public static List<Map<String, Object>> formatEveryMonthLength(List<IkAndBvVo> driving, List<IkAndBvVo> mining) {
        List<Map<String, Object>> res = new ArrayList<>();
        /**
         * 本年度掘进
         */
        for (int month = 1; month <= 12; month++) {
            Map<String, Object> map = new LinkedHashMap<>();
            BigDecimal v = BigDecimal.ZERO;
            for (IkAndBvVo datum : driving) {
                if(datum.getKey().equals(month)){
                    v = datum.getValue();
                    driving.remove(datum);
                    break;
                }
            }
            map.put("name","今年掘进");
            map.put("month", FormatToStringValueUtil.addLeftZero(month) + " 月");
            map.put("value", v);
            res.add(map);
        }
        /**
         * 本年度回采
         */
        for (int month = 1; month <= 12; month++) {
            Map<String, Object> map = new LinkedHashMap<>();
            BigDecimal v = BigDecimal.ZERO;
            for (IkAndBvVo datum : mining) {
                if(datum.getKey().equals(month)){
                    v = datum.getValue();
                    driving.remove(datum);
                    break;
                }
            }
            map.put("name","今年回采");
            map.put("month", FormatToStringValueUtil.addLeftZero(month) + " 月");
            map.put("value", v);
            res.add(map);
        }
        return res;
    }
}
