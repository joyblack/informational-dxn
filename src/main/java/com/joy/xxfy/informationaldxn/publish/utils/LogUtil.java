package com.joy.xxfy.informationaldxn.publish.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil{
    private static Logger log = LoggerFactory.getLogger(LogUtil.class);

    public static void info(String content){
        log.info(content);
    }

    public static void info(String content, Object obj){
        log.info("(joy) " + content, obj);
    }

    public static void info(String content, Object... obj){
        log.info("(joy)" + content, obj);
    }
}
