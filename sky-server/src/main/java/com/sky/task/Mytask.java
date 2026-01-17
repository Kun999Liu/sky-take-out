package com.sky.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Date;

/**
 * @Author {liukun}
 * @e-mail:liukunjsj@163.com
 * @Date: 2026/01/17/ 21:52
 * @description
 */
@Component
@Slf4j
public class Mytask {

    /**
     * 定时任务测试
     */
//    @Scheduled(cron = "0/10 * * * * ?")
    public void executeTsak(){

        log.info("执行定时任务:{}",new Date());
    }
}
