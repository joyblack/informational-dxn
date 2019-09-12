package com.joy.xxfy.informationaldxn.publish.utils.project;

import com.joy.xxfy.informationaldxn.module.common.web.req.BaseSaveReq;
import com.joy.xxfy.informationaldxn.validate.ValidList;

public class SaveReqUtil {

    /**
     * 将请求数据按ID进行正序排序，主要是要优先处理待删除数据
     */
    public static <T extends BaseSaveReq> void sort(ValidList<T> reqs){
        if(reqs != null && reqs.size() > 0){
            reqs.sort((a,b) -> {
                if(a.getId() == null || b.getId() == null){
                    return - 1;
                }else{
                    return a.getId().compareTo(b.getId());
                }
            });
        }
    }
}
