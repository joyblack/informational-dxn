package com.joy.xxfy.informationaldxn.module.staff.web;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.joy.xxfy.informationaldxn.module.common.constant.ExcelImportConstant;
import com.joy.xxfy.informationaldxn.module.common.web.BaseController;
import com.joy.xxfy.informationaldxn.module.staff.domain.vo.StaffExcelVo;
import com.joy.xxfy.informationaldxn.module.staff.service.ImportService;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("staff-export")
@RestController
public class ImportController extends BaseController {


    @Autowired
    private ImportService importService;

    /**
     *
     *
     * @param file
     * @return
     */
    @RequestMapping(
            value = "/importData",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    public JoyResult importData(@RequestParam("file") MultipartFile file,HttpServletRequest request) {
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
            return importService.importData(staffExcelVo, getLoginUser(request));
        } catch (IOException e) {
            e.printStackTrace();
            return JoyResult.buildFailedResult(Notice.EXECUTE_IS_FAILED);
        }
    }

}
