package com.joy.xxfy.informationaldxn.module.staff.web;

import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.module.staff.service.StaffReviewService;
import com.joy.xxfy.informationaldxn.module.staff.web.req.ReviewReq;
import com.joy.xxfy.informationaldxn.module.staff.web.req.StaffEntryGetListReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("staff-review")
public class StaffReviewController {
    @Autowired
    private StaffReviewService staffReviewService;

    /**
     * 获取分页数据
     */
    @PostMapping(
            value = "/getPagerList",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getPagerList(@RequestBody @Valid StaffEntryGetListReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return staffReviewService.getPagerList(req);
        }
    }

    /**
     * 获取所有数据
     */
    @PostMapping(
            value = "/getAllList",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getAllList(@RequestBody @Valid StaffEntryGetListReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return staffReviewService.getAllList(req);
        }
    }

    /**
     * 进行审核
     */
    @PostMapping(
            value = "review",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult review(@RequestBody @Valid ReviewReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return staffReviewService.review(req);
        }
    }

    
}
