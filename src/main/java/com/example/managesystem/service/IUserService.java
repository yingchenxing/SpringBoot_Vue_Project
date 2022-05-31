package com.example.managesystem.service;

import com.example.managesystem.common.Result;
import com.example.managesystem.controller.dto.UserDTO;
import com.example.managesystem.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author YingChenxing
 * @since 2022-05-28
 */
public interface IUserService extends IService<User> {

    UserDTO login(UserDTO user);

    User register(UserDTO userDTO);
}
