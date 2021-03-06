package com.huigege.pig.geteway.service;

import com.netflix.zuul.context.RequestContext;

/**
 * @author lengleng
 * @date 2017/11/16
 */
public interface LogSendService {
    /**
     * 往消息通道发消息
     * @param requestContext
     */
    void send(RequestContext requestContext);
}
