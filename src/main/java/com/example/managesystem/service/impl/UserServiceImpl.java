package com.example.managesystem.service.impl;

import com.example.managesystem.entity.User;
import com.example.managesystem.mapper.UserMapper;
import com.example.managesystem.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author YingChenxing
 * @since 2022-05-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
