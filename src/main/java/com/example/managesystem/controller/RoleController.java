package com.example.managesystem.controller;


import javax.annotation.Resource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.example.managesystem.service.IRoleService;
import com.example.managesystem.entity.Role;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author YingChenxing
 * @since 2022-06-04
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private IRoleService roleService;

    @PostMapping
    public Boolean save(@RequestBody Role role) {
        return roleService.saveOrUpdate(role);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return roleService.removeById(id);
    }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return roleService.removeBatchByIds(ids);
    }

    @GetMapping
    public List<Role> findAll() {
        return roleService.list();
    }

    @GetMapping
    public Role findOne(@PathVariable Integer id) {
        return roleService.getById(id);
    }

    //分页查询mybatis-plus方式
    @GetMapping("/page")
    public Page<Role> findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize) {
        return roleService.page(new Page<>(pageNum, pageSize));
    }
}

