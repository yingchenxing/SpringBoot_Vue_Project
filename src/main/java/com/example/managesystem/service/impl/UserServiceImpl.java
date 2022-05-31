package com.example.managesystem.service.impl;

import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.managesystem.controller.dto.UserDTO;
import com.example.managesystem.entity.User;
import com.example.managesystem.mapper.UserMapper;
import com.example.managesystem.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author YingChenxing
 * @since 2022-05-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private static final Log LOG = Log.get();

    @Override
    public boolean login(UserDTO user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        queryWrapper.eq("password", user.getPassword());

        try {
            User one = getOne(queryWrapper);
            return one != null;
        }catch (Exception e){
            LOG.error(e);
            return false;
        }
    }
}
