package com.huigege.pig.admin.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.huigege.pig.admin.entity.SysMenu;
import com.huigege.pig.admin.mapper.SysMenuMapper;
import com.huigege.pig.admin.service.SysMenuService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.huigege.pig.common.vo.MenuVo;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author lengleng
 * @since 2017-10-29
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    @Cacheable(value = "menu_details", key = "#role + #type + '_menu'")
    public Set<MenuVo> findMenuByRole(String role, Integer type) {
        return sysMenuMapper.findMenuByRoleName(role, type);
    }

    @Override
    public String[] findPermission(String[] roles) {
        Set<MenuVo> menuVoSet = new HashSet<>();
        for (String role : roles) {
            Set<MenuVo> menuVos = findMenuByRole(role, 0);
            menuVoSet.addAll(menuVos);
        }

        Set<String> permissions = new HashSet<>();
        for (MenuVo menuVo : menuVoSet) {
            if (StringUtils.isNotEmpty(menuVo.getPermission())) {
                String permission = menuVo.getPermission();
                permissions.add(permission);
            }
        }

        return permissions.toArray(new String[permissions.size()]);
    }
}
