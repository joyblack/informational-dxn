package com.joy.xxfy.informationaldxn.position.service;

import com.joy.xxfy.informationaldxn.common.web.req.BasePageReq;
import com.joy.xxfy.informationaldxn.position.domain.entity.PositionEntity;
import com.joy.xxfy.informationaldxn.position.domain.repository.PositionRepository;
import com.joy.xxfy.informationaldxn.position.web.req.PositionAddReq;
import com.joy.xxfy.informationaldxn.position.web.req.PositionUpdateReq;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.JoyBeanUtil;
import com.joy.xxfy.informationaldxn.publish.utils.StringUtil;
import com.joy.xxfy.informationaldxn.publish.utils.project.JpaPagerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service
public class PositionService {
    @Autowired
    private PositionRepository positionRepository;

    public JoyResult add(PositionAddReq req) {
        // check name repeat.
        PositionEntity checkPosition = positionRepository.findAllByPositionName(req.getPositionName());
        if(checkPosition != null){
            return JoyResult.buildFailedResult(Notice.POSITION_NAME_ALREADY_EXIST);
        }
        // name
        PositionEntity positionInfo = new PositionEntity();
        positionInfo.setDescribes(req.getDescribes());
        positionInfo.setPositionName(req.getPositionName());
        positionInfo.setRemarks(req.getRemarks());
        return JoyResult.buildSuccessResultWithData(positionRepository.save(positionInfo));
    }

    public JoyResult update(PositionUpdateReq req) {
        // get older
        PositionEntity position = positionRepository.findAllById(req.getId());
        if(position == null){
            return JoyResult.buildFailedResult(Notice.POSITION_NOT_EXIST);
        }
        // check name repeat.
        PositionEntity checkPosition = positionRepository.findAllByPositionNameAndIdNot(position.getPositionName(), position.getId());
        if(checkPosition != null){
            return JoyResult.buildFailedResult(Notice.POSITION_NAME_ALREADY_EXIST);
        }
        //
        position.setPositionName(req.getPositionName());
        position.setDescribes(req.getDescribes());
        position.setRemarks(req.getRemarks());
        return JoyResult.buildSuccessResultWithData(positionRepository.save(position));
    }

    public JoyResult delete(Long id) {
        // get older
        PositionEntity position = positionRepository.findAllById(id);
        if(position == null){
            return JoyResult.buildFailedResult(Notice.POSITION_NOT_EXIST);
        }
        position.setIsDelete(true);
        // soft delete.

        return JoyResult.buildSuccessResultWithData(positionRepository.save(position));
    }

    /**
     * 获取数据
     */
    public JoyResult get(Long id) {
        return JoyResult.buildSuccessResultWithData(positionRepository.findAllById(id));
    }


    /**
     * 分页获取
     */
    public JoyResult getPagerList(BasePageReq req) {
        return JoyResult.buildSuccessResultWithData(positionRepository.findAll((Specification<PositionEntity>) (root, query, builder) -> {
            if(!StringUtil.isEmpty(req.getSearch())){
                Predicate positionName = builder.or(builder.like(root.get("positionName"), "%" + req.getSearch() + "%"));
                query.where(positionName);
            }
            return query.getRestriction();
        }, JpaPagerUtil.getPageable(req)));
    }

    /**
     * 获取全部
     */
    public JoyResult getAllList(BasePageReq req) {
        return JoyResult.buildSuccessResultWithData(positionRepository.findAll((Specification<PositionEntity>) (root, query, builder) -> {
            if(!StringUtil.isEmpty(req.getSearch())){
                Predicate positionName = builder.or(builder.like(root.get("positionName"), "%" + req.getSearch() + "%"));
                query.where(positionName);
            }
            return query.getRestriction();
        }));
    }



}
