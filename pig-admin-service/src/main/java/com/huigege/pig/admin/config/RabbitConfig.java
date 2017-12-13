package com.huigege.pig.admin.config;

import com.huigege.pig.common.constant.CommonConstant;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author huigege
 * @date 2017/11/16
 * rabbit初始化配置
 */
@Configuration
public class RabbitConfig {
    /**
     * 初始化 log队列
     * @return
     */
    @Bean
    public Queue initLogQueue() {
        return new Queue(CommonConstant.LOG_QUEUE);
    }
}
