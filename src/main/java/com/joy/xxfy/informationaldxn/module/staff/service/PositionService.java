package com.joy.xxfy.informationaldxn.module.staff.service;

import com.joy.xxfy.informationaldxn.module.common.web.req.BasePageReq;
import com.joy.xxfy.informationaldxn.module.staff.domain.enetiy.PositionEntity;
import com.joy.xxfy.informationaldxn.module.staff.domain.enetiy.StaffEntryEntity;
import com.joy.xxfy.informationaldxn.module.staff.domain.enetiy.StaffLeaveEntity;
import com.joy.xxfy.informationaldxn.module.staff.domain.repository.PositionRepository;
import com.joy.xxfy.informationaldxn.module.staff.domain.repository.StaffEntryRepository;
import com.joy.xxfy.informationaldxn.module.staff.domain.repository.StaffLeaveRepository;
import com.joy.xxfy.informationaldxn.module.staff.web.req.PositionAddReq;
import com.joy.xxfy.informationaldxn.module.staff.web.req.PositionUpdateReq;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.StringUtil;
import com.joy.xxfy.informationaldxn.publish.utils.project.JpaPagerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.Date;

@Service
@Transactional
public class PositionService {
    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private StaffEntryRepository staffEntryRepository;

    @Autowired
    private StaffLeaveRepository staffLeaveRepository;

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
        // 检查职位是否在用
        StaffEntryEntity entryInfo = staffEntryRepository.findFirstByPosition(position);
        if(entryInfo != null){
            return JoyResult.buildFailedResult(Notice.POSITION_IN_USED);
        }

        StaffLeaveEntity leaveInfo = staffLeaveRepository.findFirstByPosition(position);
        if(leaveInfo != null){
            return JoyResult.buildFailedResult(Notice.POSITION_IN_USED);
        }

        position.setIsDelete(true);
        position.setUpdateTime(new Date());
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
