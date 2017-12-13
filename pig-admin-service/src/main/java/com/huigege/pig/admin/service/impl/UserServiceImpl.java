package com.huigege.pig.admin.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.huigege.pig.admin.entity.SysUser;
import com.huigege.pig.admin.mapper.SysUserMapper;
import com.huigege.pig.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.huigege.pig.common.vo.UserVo;

/**
 * @author lengleng
 * @date 2017/10/31
 */
@Service
public class UserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements UserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    @Cacheable(value = "user_details", key = "#username")
    public UserVo findUserByUsername(String username) {
        return sysUserMapper.selectUserVoByUsername(username);
    }

    @Override
    public Page selectWithRolePage(Page<UserVo> page, SysUser sysUser) {
        page.setRecords(sysUserMapper.selectUserVoPage(page, sysUser));
        return page;
    }

    @Override
    @CacheEvict(value = "user_details", key = "#username")
    public void clearCache(String username) {

    }
}
