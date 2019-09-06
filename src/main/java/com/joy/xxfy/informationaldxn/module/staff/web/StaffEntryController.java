package com.joy.xxfy.informationaldxn.module.staff.web;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.joy.xxfy.informationaldxn.module.common.constant.ExcelImportConstant;
import com.joy.xxfy.informationaldxn.module.common.web.BaseController;
import com.joy.xxfy.informationaldxn.module.common.web.req.IdReq;
import com.joy.xxfy.informationaldxn.module.staff.domain.vo.StaffExcelVo;
import com.joy.xxfy.informationaldxn.module.staff.service.ImportService;
import com.joy.xxfy.informationaldxn.publish.exception.JoyException;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.module.staff.service.StaffEntryService;
import com.joy.xxfy.informationaldxn.module.staff.web.req.IdNumberReq;
import com.joy.xxfy.informationaldxn.module.staff.web.req.StaffEntryAddReq;
import com.joy.xxfy.informationaldxn.module.staff.web.req.StaffEntryGetListReq;
import com.joy.xxfy.informationaldxn.module.staff.web.req.StaffEntryUpdateReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("staff-entry")
public class StaffEntryController extends BaseController {
    @Autowired
    private StaffEntryService staffEntryService;

    @Autowired
    private ImportService importService;


    /**
     * 导入
     */
    @RequestMapping(
            value = "/import",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    public JoyResult importEntry(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        if (file.isEmpty()) {
            return JoyResult.buildFailedResult(Notice.IMPORT_FILE_IS_NULL);
        }
        try {
            ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
            Map<String, String> headerAlias = new HashMap<String, String>(ExcelImportConstant.STAFF_EXCEL_COLUMN_NAMES.length);
            for (int i = 0; i < ExcelImportConstant.STAFF_EXCEL_COLUMN_NAMES.length; i++) {
                headerAlias.put(ExcelImportConstant.STAFF_EXCEL_COLUMN_NAMES[i], ExcelImportConstant.STAFF_VO_FIELDS[i]);
            }
            reader.setHeaderAlias(headerAlias);
            List<StaffExcelVo> staffExcelVo = reader.readAll(StaffExcelVo.class);
            return importService.importEntry(staffExcelVo, getLoginUser(request));
        } catch (IOException e) {
            e.printStackTrace();
            return JoyResult.buildFailedResult(Notice.EXECUTE_IS_FAILED);
        }
    }

    /**
     * 获取
     */
    @PostMapping(
            value = "/get",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult get(@RequestBody @Valid IdReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return staffEntryService.get(req.getId());
        }
    }

    /**
     * 获取（通过身份证）
     */
    @PostMapping(
            value = "/getByIdNumber",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getByIdNumber(@RequestBody @Valid IdNumberReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return staffEntryService.getByIdNumber(req.getIdNumber());
        }
    }

    /**
     * 获取（通过身份证）
     */
    @PostMapping(
            value = "/getPassListByIdNumber",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getPassListByIdNumber(@RequestBody @Valid IdNumberReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return staffEntryService.getPassListByIdNumber(req.getIdNumber());
        }
    }

    /**
     * 获取分页数据
     */
    @PostMapping(
            value = "/getPagerList",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getPagerList(@RequestBody @Valid StaffEntryGetListReq req, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return staffEntryService.getPagerList(req, getLoginUser(request));
        }
    }

    /**
     * 获取所有数据
     */
    @PostMapping(
            value = "/getAllList",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getAllList(@RequestBody @Valid StaffEntryGetListReq req, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return staffEntryService.getAllList(req, getLoginUser(request));
        }
    }

    /**
     * 添加
     */
    @PostMapping(
            value = "/add",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult add(@RequestBody @Valid StaffEntryAddReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return staffEntryService.add(req);
        }
    }

    /**
     * 更新
     */
    @PostMapping(
            value = "/update",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult update(@RequestBody @Valid StaffEntryUpdateReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return staffEntryService.update(req);
        }
    }

    /**
     * 删除
     */
    @PostMapping(
            value = "/delete",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult update(@RequestBody @Valid IdReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return staffEntryService.delete(req.getId());
        }
    }

    /**
     * 导出查询结果的数据
     */
    @RequestMapping("exportData")
    public void update(@RequestBody @Valid StaffEntryGetListReq req, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            throw new JoyException(Notice.REQUEST_PARAMETER_IS_ERROR);
        } else {
            // copy
            staffEntryService.exportData(req, getLoginUser(request), request, response);
        }
    }


}
