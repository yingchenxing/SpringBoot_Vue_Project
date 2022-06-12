package com.example.managesystem.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.managesystem.entity.Menu;
import com.example.managesystem.entity.Role;
import com.example.managesystem.entity.RoleMenu;
import com.example.managesystem.mapper.RoleMapper;
import com.example.managesystem.mapper.RoleMenuMapper;
import com.example.managesystem.service.IMenuService;
import com.example.managesystem.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.beans.Transient;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author YingChenxing
 * @since 2022-06-04
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private IMenuService menuService;

    @Override
    @Transactional
    public void setRoleMenu(Integer roleId, List<Integer> menuIds) {

        roleMenuMapper.deleteByRoleId(roleId);

        List<Integer> menuIdsCopy = CollUtil.newArrayList(menuIds);
        for(Integer menuId:menuIds){
            Menu menu = menuService.getById(menuId);
            if(menu.getPid()!=null && !menuIdsCopy.contains(menu.getPid())){
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menu.getPid());
                roleMenuMapper.insert(roleMenu);
                menuIdsCopy.add(menu.getPid());
            }

            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuMapper.insert(roleMenu);
        }
    }

    @Override
    public List<Integer> getRoleMenu(Integer roleId) {
        return roleMenuMapper.selectByRoleId(roleId);
    }
}
