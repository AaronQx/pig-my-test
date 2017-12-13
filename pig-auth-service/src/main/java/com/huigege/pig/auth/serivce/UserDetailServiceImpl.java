package com.huigege.pig.auth.serivce;


import com.huigege.pig.auth.feign.UserService;
import com.huigege.pig.auth.util.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.huigege.pig.common.vo.UserVo;

import java.io.Serializable;

/**
 * @author lengleng
 * @date 2017/10/26
 * <p>
 * TODO 通过调用 admin模块
 */
@Service("userDetailService")
public class UserDetailServiceImpl implements UserDetailsService, Serializable {
    @Autowired
    private UserService userService;

    @Override
    public UserInfo loadUserByUsername(String username) throws UsernameNotFoundException {
        UserVo userVo = userService.findUserByUsername(username);
        return new UserInfo(userVo);
    }
}
