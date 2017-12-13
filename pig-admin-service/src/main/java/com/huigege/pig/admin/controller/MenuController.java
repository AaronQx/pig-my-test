package com.huigege.pig.admin.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.huigege.pig.admin.dto.MenuTree;
import com.huigege.pig.admin.entity.SysMenu;
import com.huigege.pig.admin.service.SysMenuService;
import com.huigege.pig.admin.util.TreeUtil;
import com.huigege.pig.common.constant.CommonConstant;
import com.huigege.pig.common.vo.MenuVo;
import com.huigege.pig.common.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author lengleng
 * @date 2017/10/31
 */
@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController {
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 通过用户名查询用户菜单
     *
     * @param role 角色名称
     * @return 菜单列表
     */
    @GetMapping("/findMenuByRole/{role}")
    public Set<MenuVo> findMenuByRole(@PathVariable String role) {
        return sysMenuService.findMenuByRole(role, 0);
    }

    /**
     * 返回树形菜单集合
     *
     * @return 树形菜单
     */
    @GetMapping(value = "/tree")
    public List<MenuTree> getTree() {
        SysMenu condition = new SysMenu();
        condition.setDelFlag(CommonConstant.STATUS_NORMAL);
        return getMenuTree(sysMenuService.selectList(new EntityWrapper<>(condition)), -1);
    }

    /**
     * 返回当前用户树形菜单集合
     *
     * @return 树形菜单
     */
    @GetMapping("/userTree/{type}")
    public List<Integer> userTree(@PathVariable Integer type) {
        Set<MenuVo> menus = sysMenuService.findMenuByRole(getRole().get(0), type);
        List<Integer> menuList = new ArrayList<>();
        for (MenuVo menuVo : menus) {
            menuList.add(menuVo.getMenuId());
        }
        return menuList;
    }

    /**
     * 返回角色的菜单集合
     *
     * @param roleName 角色名称
     * @return 属性集合
     */
    @GetMapping("/roleTree/{roleName}")
    public List<Integer> roleTree(@PathVariable String roleName) {
        Set<MenuVo> menus = sysMenuService.findMenuByRole(roleName, 0);
        List<Integer> menuList = new ArrayList<>();
        for (MenuVo menuVo : menus) {
            menuList.add(menuVo.getMenuId());
        }
        return menuList;
    }

    /**
     * 通过ID查询菜单的详细信息
     *
     * @param id 菜单ID
     * @return 菜单详细信息
     */
    @GetMapping("/{id}")
    public SysMenu menu(@PathVariable Integer id) {
        return sysMenuService.selectById(id);
    }

    /**
     * 新增菜单
     *
     * @param sysMenu 菜单信息
     * @return success/false
     */
    @PostMapping
    public Boolean menu(@RequestBody SysMenu sysMenu) {
        return sysMenuService.insert(sysMenu);
    }

    /**
     * 删除菜单
     *
     * @param id 菜单ID
     * @return success/false
     * TODO  级联删除下级节点
     */
    @DeleteMapping("/{id}")
    @CacheEvict(value = "menu_details", allEntries = true)
    public Boolean menuDel(@PathVariable Integer id) {
        // 删除当前节点
        SysMenu condition1 = new SysMenu();
        condition1.setMenuId(id);
        condition1.setDelFlag(CommonConstant.STATUS_DEL);
        sysMenuService.updateById(condition1);

        // 删除父节点为当前节点的节点
        SysMenu conditon2 = new SysMenu();
        conditon2.setParentId(id);
        SysMenu sysMenu = new SysMenu();
        sysMenu.setDelFlag(CommonConstant.STATUS_DEL);
        sysMenuService.update(sysMenu, new EntityWrapper<>(conditon2));
        return Boolean.TRUE;
    }

    @PutMapping
    @CacheEvict(value = "menu_details", allEntries = true)
    public Boolean menuUpdate(@RequestBody SysMenu sysMenu) {
        return sysMenuService.updateById(sysMenu);
    }

    private List<MenuTree> getMenuTree(List<SysMenu> menus, int root) {
        List<MenuTree> trees = new ArrayList<MenuTree>();
        MenuTree node = null;
        for (SysMenu menu : menus) {
            node = new MenuTree();
            node.setId(menu.getMenuId());
            node.setParentId(menu.getParentId());
            node.setName(menu.getName());
            node.setUrl(menu.getUrl());
            node.setPath(menu.getPath());
            node.setCode(menu.getPermission());
            node.setLabel(menu.getName());
            node.setComponent(menu.getComponent());
            node.setIcon(menu.getIcon());
            trees.add(node);
        }
        return TreeUtil.bulid(trees, root);
    }
}
