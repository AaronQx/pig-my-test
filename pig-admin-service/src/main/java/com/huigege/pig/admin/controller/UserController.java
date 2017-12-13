package com.huigege.pig.admin.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.huigege.pig.admin.dto.UserInfo;
import com.huigege.pig.admin.entity.SysUser;
import com.huigege.pig.admin.service.SysMenuService;
import com.huigege.pig.admin.service.UserService;
import com.huigege.pig.common.constant.CommonConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.huigege.pig.common.util.UserUtils;
import com.huigege.pig.common.vo.UserVo;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.List;

@RequestMapping("user")
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private HttpServletRequest request;
    /**
     * 获取当前用户的用户名
     *
     * @return 用户名
     */
    @GetMapping("/info")
    public UserInfo user() {
        SysUser condition = new SysUser();
        condition.setUsername(UserUtils.getUserName());
        SysUser sysUser = userService.selectOne(new EntityWrapper<>(condition));

        UserInfo userInfo = new UserInfo();
        userInfo.setSysUser(sysUser);
        //设置角色列表
        String[] roles = getRole().toArray(new String[getRole().size()]);
        userInfo.setRoles(roles);
        //设置权限列表（menu.permission）
        String[] permissions = sysMenuService.findPermission(roles);
        userInfo.setPermissions(permissions);
        return userInfo;
    }

    /**
     * 根据请求heard中的token获取用户角色
     *
     * @return 角色名
     */
    public List<String> getRole() {
        String authorization = request.getHeader(CommonConstant.REQ_HEADER);
        String token = StringUtils.substringAfter(authorization, CommonConstant.TOKEN_SPLIT);
        String key = Base64.getEncoder().encodeToString(CommonConstant.SIGN_KEY.getBytes());
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        List<String> roleNames = (List<String>) claims.get("authorities");
        return roleNames;
    }

    /**
     * 通过用户名查询用户及其角色信息
     *
     * @param username 用户名
     * @return UseVo 对象
     */
    @GetMapping("/findUserByUsername/{username}")
    public UserVo findUserByUsername(@PathVariable String username) {
        return userService.findUserByUsername(username);
    }
}
