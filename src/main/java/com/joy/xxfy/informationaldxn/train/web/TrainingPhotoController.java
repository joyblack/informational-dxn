package com.joy.xxfy.informationaldxn.train.web;


import com.joy.xxfy.informationaldxn.common.web.req.IdReq;
import com.joy.xxfy.informationaldxn.file.service.FileService;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.train.domain.entity.TrainingPhotoEntity;
import com.joy.xxfy.informationaldxn.train.service.TrainingPhotoService;
import com.joy.xxfy.informationaldxn.train.service.TrainingService;
import com.joy.xxfy.informationaldxn.train.web.req.TrainingAddReq;
import com.joy.xxfy.informationaldxn.train.web.req.TrainingGetListReq;
import com.joy.xxfy.informationaldxn.train.web.req.TrainingUpdateReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("train-photo")
public class TrainingPhotoController {

    @Autowired
    private TrainingPhotoService trainingPhotoService;

    /**
     * 上传文件
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public JoyResult uploadFile(@RequestParam("file") MultipartFile file, Long id) {
        if (null == file) {
            return JoyResult.buildFailedResult(Notice.UPLOAD_FILE_IS_NULL);
        } else {
            return trainingPhotoService.upload(file,id);
        }
    }

    /**
     * 下载文件
     */
    @RequestMapping(
            value = "/download/{id}",
            method = RequestMethod.GET)
    public void downloadFile(@PathVariable("id")Long id, HttpServletRequest req, HttpServletResponse resp) {
        trainingPhotoService.download(id, req, resp);
    }
}
