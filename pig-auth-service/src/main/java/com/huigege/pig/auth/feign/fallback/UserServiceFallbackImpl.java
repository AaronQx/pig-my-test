package com.huigege.pig.auth.feign.fallback;

import com.huigege.pig.auth.feign.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.huigege.pig.common.vo.UserVo;

/**
 * @author lengleng
 * @date 2017/10/31
 * 用户服务的fallback
 */
public class UserServiceFallbackImpl implements UserService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public UserVo findUserByUsername(String username) {
        logger.error("调用{}异常:{}", "findUserByUsername", username);
        return null;
    }
}
