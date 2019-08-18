package com.joy.xxfy.informationaldxn.publish.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    /**
     * 判断字符串是否为空串
     */
    public static boolean isEmpty(String value){
        if ((value == null) || (value.equals(""))) {
            return true;
        }
        return false;
    }
    public static boolean isNotEmpty(String value) {
        if ((value != null) && (!value.equals(""))) {
            return true;
        }
        return false;
    }

    /**
     * 判断两字符串是否相等
     */
    public static boolean equals(String str1, String str2) {
        if (str1.equals(str2)) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 下划线转驼峰法
     * @param line
     * @param smallCamel 大小驼峰,是否为小驼峰
     * @return
     */
    public static String toCamelCase(String line,boolean smallCamel){
        if(line==null||"".equals(line)){
            return "";
        }
        StringBuffer sb=new StringBuffer();
        Pattern pattern=Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher=pattern.matcher(line);
        while(matcher.find()){
            String word=matcher.group();
            sb.append(smallCamel&&matcher.start()==0?Character.toLowerCase(word.charAt(0)):Character.toUpperCase(word.charAt(0)));
            int index=word.lastIndexOf('_');
            if(index>0){
                sb.append(word.substring(1, index).toLowerCase());
            }else{
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }

    /**
     * 驼峰转下划线
     * @param str
     * @return
     */
    public static String toLine(String str){
        Pattern humpPattern = Pattern.compile("[A-Z]");
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb, "_"+matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
    public static String[] toLine(String [] strs){
        String[] res = new String[strs.length];
        for(int i = 0; i <strs.length; i ++){
            res[i] = toLine(strs[i]);
        }
        return res;
    }

}
