package com.joy.xxfy.informationaldxn.publish.utils.project;

import com.joy.xxfy.informationaldxn.module.common.web.req.BasePageReq;
import com.joy.xxfy.informationaldxn.publish.utils.StringUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class JpaPagerUtil {
    /**
     * 第一页: 0
     */
    public static final int PAGE_OFFSET;

    static {
        PAGE_OFFSET = 1;
    }

    public static<T extends BasePageReq> Pageable getPageable(T t){
        if(t == null){
            return null;
        }else{
            if(StringUtil.isEmpty(t.getSort())){
                return PageRequest.of(t.getPage() - PAGE_OFFSET, t.getSize(), new Sort(Sort.Direction.DESC,"createTime"));
            }else{
                return PageRequest.of(t.getPage() - PAGE_OFFSET, t.getSize(), new Sort(t.getOrder(),t.getSort()));
            }
        }

    }
}
