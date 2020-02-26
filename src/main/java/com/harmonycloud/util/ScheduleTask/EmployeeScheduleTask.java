package com.harmonycloud.util.ScheduleTask;

import com.harmonycloud.bean.Threads.SynchroThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling   //开启定时任务
@EnableAsync        //开启多线程
@Slf4j
public class EmployeeScheduleTask {

    /**
     * 员工及其信息的同步
     * @throws Exception
     */
    @Async
    @Scheduled(cron = "0 30 23 * * ?")//每天23：30点执行
    public void synchronization() throws Exception {
        SynchroThread synchro = new SynchroThread();
        synchro.run();
    }
}
