package com.example.tlstool.service;

import com.example.tlstool.entity.ro.TlsCreateTaskRO;

public interface AsyncService {

    void createSslyzeTask (TlsCreateTaskRO tlsCreateTaskRO, Long taskId) throws Exception;
}
