package com.joy.xxfy.informationaldxn.staff.domain.service;

import com.joy.xxfy.informationaldxn.publish.constant.ResultDataConstant;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.identity.IdNumberUtil;
import com.joy.xxfy.informationaldxn.staff.domain.repository.StaffPersonalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class StaffPersonalService {
    @Autowired
    private StaffPersonalRepository staffPersonalRepository;

    /**
     * 通过身份证获取个人信息
     */
    public JoyResult getPersonalByIdNumber(String idNumber) {
        return JoyResult.buildSuccessResultWithData(staffPersonalRepository.findAllByIdNumber(idNumber));
    }

    /**
     * 通过ID获取个人信息
     */
    public JoyResult get(Long id) {
        return JoyResult.buildSuccessResultWithData(staffPersonalRepository.findAllById(id));
    }

    /**
     * 根据身份证获取内部信息
     */
    public JoyResult explainIdNumber(String idNumber) {
        if(!IdNumberUtil.isIDNumber(idNumber)){
            return JoyResult.buildFailedResult(Notice.IDENTITY_NUMBER_ERROR);
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put(ResultDataConstant.BIRTH_DATE,IdNumberUtil.getBirthday(idNumber));
        data.put(ResultDataConstant.SEX,IdNumberUtil.getSex(idNumber).ordinal());
        return JoyResult.buildSuccessResultWithData(data);
    }

    public JoyResult getByUsername(String username) {
        return JoyResult.buildSuccessResultWithData(staffPersonalRepository.findAllByUsername(username));
    }
}
