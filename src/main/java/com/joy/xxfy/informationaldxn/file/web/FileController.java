package com.joy.xxfy.informationaldxn.file.web;

import com.joy.xxfy.informationaldxn.file.service.FileService;
import com.joy.xxfy.informationaldxn.file.web.req.FormDataParam;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("file")
@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 上传文件
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public JoyResult uploadFile(@RequestParam("file") MultipartFile file, String module) {
        if (null == file) {
            return JoyResult.buildFailedResult(Notice.UPLOAD_FILE_IS_NULL);
        } else {
            if(module == null){
                return JoyResult.buildFailedResult(Notice.UPLOAD_FILE_MODULE_IS_NULL);
            }
            System.out.println(file);
            System.out.println(module);
            return fileService.upload(file,module);
        }
    }

    /**
     * 下载文件
     */
    @RequestMapping(
            value = "/download/{id}",
            method = RequestMethod.GET)
    public void downloadFile(@PathVariable("id")Long id, HttpServletRequest req, HttpServletResponse resp) {
        fileService.download(id, req, resp);
    }
}
