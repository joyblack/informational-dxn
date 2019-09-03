package com.joy.xxfy.informationaldxn.module.document.service;

import com.joy.xxfy.informationaldxn.module.common.enums.CommonYesEnum;
import com.joy.xxfy.informationaldxn.module.document.domain.entity.BorrowEntity;
import com.joy.xxfy.informationaldxn.module.document.domain.enums.ReturnStatusEnum;
import com.joy.xxfy.informationaldxn.module.document.domain.repository.BorrowRepository;
import com.joy.xxfy.informationaldxn.module.document.web.req.BorrowAddReq;
import com.joy.xxfy.informationaldxn.module.document.web.req.BorrowChangeStatus;
import com.joy.xxfy.informationaldxn.module.document.web.req.BorrowGetListReq;
import com.joy.xxfy.informationaldxn.module.document.web.req.BorrowUpdateReq;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.publish.constant.ResultDataConstant;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.DateUtil;
import com.joy.xxfy.informationaldxn.publish.utils.JoyBeanUtil;
import com.joy.xxfy.informationaldxn.publish.utils.StringUtil;
import com.joy.xxfy.informationaldxn.publish.utils.project.JpaPagerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class BorrowService {
    @Autowired
    private BorrowRepository borrowRepository;

    /**
     * 添加
     */
    public JoyResult add(BorrowAddReq req, UserEntity loginUser) {
        // 装配数据
        BorrowEntity borrowInfo = new BorrowEntity();
        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, borrowInfo);
        // 所属平台
        borrowInfo.setBelongCompany(loginUser.getCompany());
        // 是否超时
        borrowInfo.setIsOverTime(CommonYesEnum.NO);
        // 是否归还
        borrowInfo.setReturnStatus(ReturnStatusEnum.RETURN_STATUS_NO);
        // save.
        return JoyResult.buildSuccessResultWithData(borrowRepository.save(borrowInfo));
    }

    /**
     * 改
     */
    public JoyResult update(BorrowUpdateReq req, UserEntity loginUser) {
        // 信息是否存在
        BorrowEntity info = borrowRepository.findAllById(req.getId());
        if(info == null){
            return JoyResult.buildFailedResult(Notice.BORROW_INFO_NOT_EXIST);
        }
        // copy
        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, info);
        info.setUpdateTime(new Date());
        // save.
        return JoyResult.buildSuccessResultWithData(borrowRepository.save(info));
    }

    /**
     * 删除
     */
    public JoyResult delete(Long id, UserEntity loginUser) {
        // 信息是否存在
        BorrowEntity info = borrowRepository.findAllById(id);
        if(info == null){
            return JoyResult.buildFailedResult(Notice.BORROW_INFO_NOT_EXIST);
        }
        // 删除
        info.setIsDelete(true);
        info.setUpdateTime(new Date());
        borrowRepository.save(info);
        return JoyResult.buildSuccessResult(ResultDataConstant.MESSAGE_DELETE_SUCCESS);
    }

    /**
     * 获取数据
     */
    public JoyResult get(Long id, UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(borrowRepository.findAllById(id));
    }

    /**
     * 获取分页数据
     */
    public JoyResult getPagerList(BorrowGetListReq req, UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(borrowRepository.findAll(getPredicates(req,loginUser), JpaPagerUtil.getPageable(req)));
    }

    /**
     * 获取全部
     */
    public JoyResult getAllList(BorrowGetListReq req, UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(borrowRepository.findAll(getPredicates(req, loginUser)));
    }

    /**
     * 获取分页数据、全部数据的谓词条件
     */
    private Specification<BorrowEntity> getPredicates(BorrowGetListReq req, UserEntity loginUser){
        return (Specification<BorrowEntity>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("belongCompany"),loginUser.getCompany()));
            // 资料名称
            if(!StringUtil.isEmpty(req.getDocumentName())){
                predicates.add(builder.like(root.get("documentName"), "%" + req.getDocumentName() +"%"));
            }
            // 借阅人
            if(!StringUtil.isEmpty(req.getBorrowPeople())){
                predicates.add(builder.like(root.get("borrowPeople"), "%" + req.getBorrowPeople() +"%"));
            }
            // 归还期限 time_between
            if(req.getDeadTimeStart() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("deadTime"), req.getDeadTimeStart()));
            }
            if(req.getDeadTimeEnd() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("deadTime"), req.getDeadTimeEnd()));
            }
            // 归还状态
            if(req.getReturnStatus() != null){
                predicates.add(builder.equal(root.get("returnStatus"), req.getReturnStatus()));
            }
            // 超时归还
            if(req.getIsOverTime() != null){
                predicates.add(builder.equal(root.get("isOverTime"), req.getIsOverTime()));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    /**
     * 变更归还状态
     */
    public JoyResult changeReturnStatus(BorrowChangeStatus req, UserEntity loginUser) {
        List<BorrowEntity> borrows = new ArrayList<>();
        // 获取每一条信息
        for (Long id : req.getIds()) {
            BorrowEntity info = borrowRepository.findAllById(id);
            if(info != null){
                borrows.add(info);
            }
        }
        if(borrows.size() == 0){
            return JoyResult.buildFailedResult(Notice.BORROW_INFO_NOT_EXIST);
        }
        // 修改状态
        // 当前时间
        Date now = new Date();
        for (BorrowEntity borrow : borrows) {
            borrow.setReturnStatus(req.getReturnStatus());
            borrow.setUpdateTime(new Date());
        }
        // 保存所有信息
        return JoyResult.buildSuccessResultWithData(borrowRepository.saveAll(borrows));
    }

    public JoyResult getNotReturnNum(UserEntity loginUser) {
        // 是否超时、是否未归还
        return JoyResult.buildSuccessResultWithData(borrowRepository.getNotReturn(CommonYesEnum.YES, ReturnStatusEnum.RETURN_STATUS_NO, loginUser.getCompany()).size());
    }

    public JoyResult getNotReturn(UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(borrowRepository.getNotReturn(CommonYesEnum.YES, ReturnStatusEnum.RETURN_STATUS_NO, loginUser.getCompany()));
    }
}
