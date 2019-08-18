package com.joy.xxfy.informationaldxn.publish.utils.identity;
/**
 * Created by XiaoWen on 2019/8/3
 * 身份证验证
 */
public class IdNumberUtil {

    /**
     * 15位身份证号
     */
    private static final Integer FIFTEEN_ID_CARD = 15;
    /**
     * 18位身份证号
     */
    private static final Integer EIGHTEEN_ID_CARD = 18;

    /**
     * 检测是否是身份证
     * @param IDNumber
     * @return
     */
    public static boolean isIDNumber(String IDNumber) {
        if (IDNumber == null || "".equals(IDNumber)) {
            return false;
        }
        // 定义判别用户身份证号的正则表达式（15位或者18位，最后一位可以为字母）
        String regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
        //假设18位身份证号码:41000119910101123X  410001 19910101 123X
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //(18|19|20)                19（现阶段可能取值范围18xx-20xx年）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十七位奇数代表男，偶数代表女）
        //[0-9Xx] 0123456789Xx其中的一个 X（第十八位为校验值）
        //$结尾

        //假设15位身份证号码:410001910101123  410001 910101 123
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十五位奇数代表男，偶数代表女），15位身份证不含X
        //$结尾
        boolean flage = IDNumber.matches(regularExpression);
        //判断第18位校验值
        if (flage) {
            if (IDNumber.length() == 18) {
                try {
                    char[] charArray = IDNumber.toCharArray();
                    //前十七位加权因子
                    int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
                    //这是除以11后，可能产生的11位余数对应的验证码
                    String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
                    int sum = 0;
                    for (int i = 0; i < idCardWi.length; i++) {
                        int current = Integer.parseInt(String.valueOf(charArray[i]));
                        int count = current * idCardWi[i];
                        sum += count;
                    }
                    char idCardLast = charArray[17];
                    int idCardMod = sum % 11;
                    if (idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
                        return true;
                    } else {
                        System.out.println("身份证最后一位:" + String.valueOf(idCardLast).toUpperCase() +
                                "错误,正确的应该是:" + idCardY[idCardMod].toUpperCase());
                        return false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("异常:" + IDNumber);
                    return false;
                }
            }
        }
        return flage;
    }

    /**
     * 根据身份证号获取性别
     * @param idNumber
     * @return
     */
    public static SexType getSex(String idNumber){
        SexType sexType = null;
        if (IdNumberUtil.isIDNumber(idNumber)){
            //15位身份证号
            if (idNumber.length() == FIFTEEN_ID_CARD){
                if (Integer.parseInt(idNumber.substring(14, 15)) % 2 == 0) {
                    sexType = SexType.WOM;
                } else {
                    sexType = SexType.MAN;
                }
                //18位身份证号
            }else if(idNumber.length() == EIGHTEEN_ID_CARD){
                // 判断性别
                if (Integer.parseInt(idNumber.substring(16).substring(0, 1)) % 2 == 0) {
                    sexType = SexType.WOM;
                } else {
                    sexType = SexType.MAN;
                }
            }
        }
        return sexType;
    }

    /**
     * 根据身份证号获取出生日期 yyyy-MM-dd
     * @param idNumber
     * @return
     */
    public static String getBirthday(String idNumber){
        String birthday="";
        String year="";
        String month="";
        String day="";
        if (IdNumberUtil.isIDNumber(idNumber)){
            //15位身份证号
            if (idNumber.length() == FIFTEEN_ID_CARD){
                // 身份证上的年份(15位身份证为1980年前的)
                year = "19" + idNumber.substring(6, 8);
                //身份证上的月份
                month = idNumber.substring(8, 10);
                //身份证上的日期
                day= idNumber.substring(10, 12);
                //18位身份证号
            }else if(idNumber.length() == EIGHTEEN_ID_CARD){
                // 身份证上的年份
                year = idNumber.substring(6).substring(0, 4);
                // 身份证上的月份
                month = idNumber.substring(10).substring(0, 2);
                //身份证上的日期
                day=idNumber.substring(12).substring(0,2);
            }
            birthday=year+"-"+month+"-"+day;
        }
        return birthday;
    }


}
