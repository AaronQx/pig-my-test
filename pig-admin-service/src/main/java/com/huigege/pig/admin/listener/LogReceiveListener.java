package com.huigege.pig.admin.listener;


import com.huigege.pig.admin.service.SysLogService;
import com.huigege.pig.common.constant.CommonConstant;
import com.huigege.pig.common.entity.SysLog;
import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.huigege.pig.common.util.UserUtils;
import com.huigege.pig.common.vo.LogVo;

/**
 * @author lengleng
 * @date 2017/11/17
 */
@Component
@RabbitListener(queues = CommonConstant.LOG_QUEUE)
public class LogReceiveListener {
    private static final String KEY_USER = "user";

    @Autowired
    private SysLogService sysLogService;

    @RabbitHandler
    public void receive(LogVo logVo) {
        SysLog sysLog = logVo.getSysLog();
        if (StringUtils.isNotEmpty(logVo.getToken())) {
            String username = UserUtils.getUserName(logVo.getToken());
            MDC.put(KEY_USER, username);
            sysLog.setCreateBy(username);
            sysLogService.insert(sysLog);
            MDC.remove(KEY_USER);
        }
    }
}
