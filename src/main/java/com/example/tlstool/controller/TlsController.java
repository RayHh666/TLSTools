package com.example.tlstool.controller;

import com.example.tlstool.entity.ro.TlsCreateTaskRO;
import com.example.tlstool.service.TlsTaskService;
import com.example.tlstool.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/tls")
@Slf4j
public class TlsController {

    // TODO
    //  1、通过目标列表创建任务
    //  2、上传目标列表文件创建任务

    @Resource
    private TlsTaskService tlsTaskService;

//    @PostMapping("/create")
//    public Result createTask(@RequestBody TlsCreateTaskRO tlsCreateTaskRO){
//        try {
//            tlsTaskService.createTask(tlsCreateTaskRO);
//        } catch (Exception e) {
//            return Result.error(e.getMessage());
//        }
//        return Result.success();// tlsTaskService.
//    }

    @PostMapping("/create")
    public Result createTask(@RequestBody TlsCreateTaskRO tlsCreateTaskRO){
        try {
            tlsTaskService.createTask(tlsCreateTaskRO);
            return Result.success();
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error(e.getMessage());
        }
    }
}
