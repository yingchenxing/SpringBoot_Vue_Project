package com.example.managesystem.service;

import com.example.managesystem.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YingChenxing
 * @since 2022-06-04
 */
public interface IRoleService extends IService<Role> {

    void setRoleMenu(Integer roleId, List<Integer> menuIds);


    List<Integer> getRoleMenu(Integer roleId);
}
