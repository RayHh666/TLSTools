package com.example.tlstool.service;

import com.example.tlstool.entity.ro.TlsCreateTaskRO;

public interface AsyncService {

    void createAsyncTask (TlsCreateTaskRO tlsCreateTaskRO, Long taskId) throws Exception;

//    void createCurlTask(TlsCreateTaskRO tlsCreateTaskRO, Long taskId, String command, Integer maxTimes);
}
