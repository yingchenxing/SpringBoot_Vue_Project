package com.example.managesystem.controller;


import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.managesystem.common.Constants;
import com.example.managesystem.common.Result;
import com.example.managesystem.controller.dto.UserDTO;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import com.example.managesystem.service.IUserService;
import com.example.managesystem.entity.User;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author YingChenxing
 * @since 2022-05-28
 */
@RestController
@RequestMapping("/user")
public class UserController {



    @Resource
    private IUserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody UserDTO user) {
        String username=user.getUsername();
        String password=user.getPassword();
        if(StrUtil.isBlank(username)|| StrUtil.isBlank(password))
            return Result.error(Constants.CODE_400,"Parameter error!");
        UserDTO dto = userService.login(user);
        return Result.success(dto);
    }


    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO) {
        String username=userDTO.getUsername();
        String password=userDTO.getPassword();
        if(StrUtil.isBlank(username)|| StrUtil.isBlank(password))
            return Result.error(Constants.CODE_400,"Parameter error!");
        return Result.success(userService.register(userDTO));
    }

    @PostMapping
    public Boolean save(@RequestBody User user) {
        return userService.saveOrUpdate(user);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return userService.removeById(id);
    }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return userService.removeBatchByIds(ids);
    }

    @GetMapping
    public List<User> findAll() {
        return userService.list();
    }

    @GetMapping("/username/{username}")
    public Result findOne(@PathVariable String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return Result.success(userService.getOne(queryWrapper));
    }

    @GetMapping("/{id}")
    public User findOne(@PathVariable Integer id) {
        return userService.getById(id);
    }

    //分页查询mybatis-plus方式
    @GetMapping("/page")
    public Page<User> findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam(defaultValue = "") String username,
                               @RequestParam(defaultValue = "") String email,
                               @RequestParam(defaultValue = "") String address) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (!username.isEmpty())
            queryWrapper.like("username", username);
        if (!email.isEmpty())
            queryWrapper.like("email", email);
        if (!address.isEmpty())
            queryWrapper.like("address", address);
        queryWrapper.orderByDesc("id");
        return userService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
        List<User> list = userService.list();
        ExcelWriter writer = ExcelUtil.getWriter(true);

        writer.addHeaderAlias("username","username");
        writer.addHeaderAlias("password","password");
        writer.addHeaderAlias("nickname","nickname");
        writer.addHeaderAlias("email","email");
        writer.addHeaderAlias("phone","phone");
        writer.addHeaderAlias("address","address");
        writer.addHeaderAlias("createTime","createTime");
        writer.addHeaderAlias("avatarUrl","avatarUrl");

        writer.write(list,true);

        //set the format of browser
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName= URLEncoder.encode("user_info","UTF-8");
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out,true);
        out.close();
        writer.close();
    }

    @PostMapping("/import")
    public boolean imp(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<User> list = reader.readAll(User.class);
        System.out.println(list);
        userService.saveBatch(list);
        return true;
    }

}