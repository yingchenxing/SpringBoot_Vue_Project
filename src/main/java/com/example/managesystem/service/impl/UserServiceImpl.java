package com.example.managesystem.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.managesystem.common.Constants;
import com.example.managesystem.common.Result;
import com.example.managesystem.controller.dto.UserDTO;
import com.example.managesystem.entity.User;
import com.example.managesystem.exception.ServiceException;
import com.example.managesystem.mapper.UserMapper;
import com.example.managesystem.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.managesystem.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    UserMapper userMapper;

    @Override
    public UserDTO login(UserDTO user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        queryWrapper.eq("password", user.getPassword());
        User one;
        try {
            one = getOne(queryWrapper);
        } catch (Exception e) {
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500, "System error!");
        }
        if (one != null) {
            BeanUtil.copyProperties(one, user, true);
            String token = TokenUtils.getToken(one.getId().toString(),one.getPassword());
            user.setToken(token);
            return user;
        } else {
            throw new ServiceException(Constants.CODE_600, "Incorrect username or password.");
        }
    }

    @Override
    public User register(UserDTO user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        List list = userMapper.selectList(queryWrapper);

        if (list.size() == 0) {
            User one = new User();
            BeanUtil.copyProperties(user,one,true);
            this.save(one);
            return one;
        }else{
            throw new ServiceException(Constants.CODE_500,"Username has been used!");
        }
    }



//    private User getUserInfo() {
//
//    }
}
