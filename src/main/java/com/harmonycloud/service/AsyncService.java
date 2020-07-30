package com.harmonycloud.service;

import com.harmonycloud.util.SyncInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AsyncService {

    @Async
    public void employeeAsync(){
        try{
            SyncInfo.LoadAllUserInfo();
        }catch (Exception e){
            log.error(e.toString());
        }
        log.info("员工同步异步执行完毕");
    }

    @Async
    public void asyncData() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.toString();
        }
        log.info("Execute method asynchronously, thread name = {}", Thread.currentThread().getName());
    }
}
