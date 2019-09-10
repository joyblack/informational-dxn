package com.joy.xxfy.informationaldxn.publish.utils.format;

import com.joy.xxfy.informationaldxn.module.common.web.res.NameValueRes;
import com.joy.xxfy.informationaldxn.module.safe.domain.vo.PerMonthStringTotalCountVo;
import com.joy.xxfy.informationaldxn.module.safe.domain.vo.PerMonthTotalCountVo;
import com.joy.xxfy.informationaldxn.module.statistic.domain.vo.KeyAndValueVo;
import com.joy.xxfy.informationaldxn.publish.utils.DateUtil;

import java.math.BigDecimal;
import java.util.*;

/**
 * 本工具类目的是将返回数据包装为AntV需求的数据格式
 */
public class AntVFormatUtil {

    /**
     * 最近15日打钻
     */
    public static List<KeyAndValueVo<String,BigDecimal>> formatNear15DayLength(List<KeyAndValueVo<String,BigDecimal>> drill, Date start){
        List<KeyAndValueVo<String,BigDecimal>> res = new ArrayList<>();
        // 数据
        for (int i = 0; i < 15; i++) {
            Date now = DateUtil.addDay(start, i);
            String md = DateUtil.getMDString(now, false); // 5-20
            BigDecimal v = BigDecimal.ZERO;
            for (KeyAndValueVo<String,BigDecimal> d : drill) {
                if(d.getKey().equals(md)){
                    v = d.getValue();
                    drill.remove(v);
                    break;
                }
            }
            res.add(new KeyAndValueVo<>(md, v));
        }
        return res;
    }

    /**
     * 打钻月度格式化
     */
    public static List<NameValueRes<BigDecimal>> formatDrillEveryMonth(List<KeyAndValueVo<Integer,BigDecimal>> drill){
        List<NameValueRes<BigDecimal>> result = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            BigDecimal ct = BigDecimal.ZERO;
            for (KeyAndValueVo<Integer,BigDecimal> datum : drill) {
                if(datum.getKey().equals(month)){
                    ct = datum.getValue();
                    drill.remove(datum);
                    break;
                }
            }
            result.add(new NameValueRes<>(FormatToStringValueUtil.addLeftZero(month) + "月", ct));
        }
        return result;
    }

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
            result.add(new PerMonthStringTotalCountVo(FormatToStringValueUtil.addLeftZero(month) + "月", ct));
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
    public static List<Map<String, Object>> formatNear15DayOutput(List<KeyAndValueVo<String,BigDecimal>> driving, List<KeyAndValueVo<String,BigDecimal>> mining, Date start){
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
            for (KeyAndValueVo<String,BigDecimal> v : driving) {
                if(v.getKey().equals(md)){
                    drV = v.getValue();
                    driving.remove(v);
                    break;
                }
            }
            for (KeyAndValueVo<String,BigDecimal> v : mining) {
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
     * 最近15日采进尺
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
    public static List<Map<String, Object>> formatNear15DayLength(List<KeyAndValueVo<String,BigDecimal>> driving, List<KeyAndValueVo<String,BigDecimal>> mining, Date start){
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
            for (KeyAndValueVo<String,BigDecimal> v : driving) {
                if(v.getKey().equals(md)){
                    drV = v.getValue();
                    driving.remove(v);
                    break;
                }
            }
            for (KeyAndValueVo<String,BigDecimal> v : mining) {
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
     * 月度采煤格式化
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
    public static Map<String, Object> formatEveryMonthOutput(List<KeyAndValueVo<Integer,BigDecimal>> driving, List<KeyAndValueVo<Integer,BigDecimal>> mining) {
        /**
         * 本年度掘进
         */
        List<Map<String, Object>> drivingRes = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            Map<String, Object> map = new LinkedHashMap<>();
            BigDecimal v = BigDecimal.ZERO;
            for (KeyAndValueVo<Integer,BigDecimal> datum : driving) {
                if(datum.getKey().equals(month)){
                    v = datum.getValue();
                    driving.remove(datum);
                    break;
                }
            }
            map.put("name","今年掘进");
            map.put("month", FormatToStringValueUtil.addLeftZero(month) + "月");
            map.put("value", v);
            drivingRes.add(map);
        }
        /**
         * 本年度回采
         */
        List<Map<String, Object>> miningRes = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            Map<String, Object> map = new LinkedHashMap<>();
            BigDecimal v = BigDecimal.ZERO;
            for (KeyAndValueVo<Integer,BigDecimal> datum : mining) {
                if(datum.getKey().equals(month)){
                    v = datum.getValue();
                    mining.remove(datum);
                    break;
                }
            }
            map.put("name","今年回采");
            map.put("month", FormatToStringValueUtil.addLeftZero(month) + "月");
            map.put("value", v);
            miningRes.add(map);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("driving", drivingRes);
        result.put("mining", miningRes);
        return result;
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
    public static Map<String, Object> formatEveryMonthLength(List<KeyAndValueVo<Integer,BigDecimal>> driving, List<KeyAndValueVo<Integer,BigDecimal>> mining) {

        /**
         * 本年度掘进
         */
        List<Map<String, Object>> drivingRes = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            Map<String, Object> map = new LinkedHashMap<>();
            BigDecimal v = BigDecimal.ZERO;
            for (KeyAndValueVo<Integer,BigDecimal> datum : driving) {
                if(datum.getKey().equals(month)){
                    v = datum.getValue();
                    driving.remove(datum);
                    break;
                }
            }
            map.put("name","今年掘进");
            map.put("month", FormatToStringValueUtil.addLeftZero(month) + "月");
            map.put("value", v);
            drivingRes.add(map);
        }
        /**
         * 本年度回采
         */
        List<Map<String, Object>> miningRes = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            Map<String, Object> map = new LinkedHashMap<>();
            BigDecimal v = BigDecimal.ZERO;
            for (KeyAndValueVo<Integer,BigDecimal> datum : mining) {
                if(datum.getKey().equals(month)){
                    v = datum.getValue();
                    driving.remove(datum);
                    break;
                }
            }
            map.put("name","今年回采");
            map.put("month", FormatToStringValueUtil.addLeftZero(month) + "月");
            map.put("value", v);
            miningRes.add(map);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("driving", drivingRes);
        result.put("mining", miningRes);
        return result;
    }
}
