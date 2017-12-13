package com.huigege.pig.geteway.service.impl;

import com.huigege.pig.geteway.feign.MenuService;
import com.huigege.pig.geteway.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import com.huigege.pig.common.vo.MenuVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * @author lengleng
 * @date 2017/10/28
 */
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private MenuService menuService;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        //ele-admin options 跨域配置，现在处理是通过前端配置代理，不使用这种方式，存在风险
//        if (HttpMethod.OPTIONS.name().equalsIgnoreCase(request.getMethod())) {
//            return true;
//        }
        Object principal = authentication.getPrincipal();
        List<SimpleGrantedAuthority> grantedAuthorityList = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
        boolean hasPermission = false;

        if (principal != null) {
            Set<MenuVo> urls = menuService.findMenuByRole(grantedAuthorityList.get(0).getAuthority());
            for (MenuVo menu : urls) {
                if (antPathMatcher.match(menu.getUrl(), request.getRequestURI())
                        && request.getMethod().equalsIgnoreCase(menu.getMethod())) {
                    hasPermission = true;
                    break;
                }
            }
        }
        return hasPermission;
    }
}
