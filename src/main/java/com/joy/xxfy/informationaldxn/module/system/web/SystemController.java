package com.joy.xxfy.informationaldxn.module.system.web;

import com.joy.xxfy.informationaldxn.module.system.service.SystemService;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("system")
public class SystemController {

    @Autowired
    private SystemService systemService;

    /**
     * 初始化系统
     */
    @RequestMapping("start")
    public JoyResult start(){
        return systemService.start();
    }

}