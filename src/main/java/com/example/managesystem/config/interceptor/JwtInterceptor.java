package com.example.managesystem.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.managesystem.common.Constants;
import com.example.managesystem.entity.User;
import com.example.managesystem.exception.ServiceException;
import com.example.managesystem.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        if (StrUtil.isBlank(token)) {
            throw new ServiceException(Constants.CODE_401, "Missing token, please login again.");
        }

        //获取token中的userID
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException e) {
            throw new ServiceException(Constants.CODE_401, "Token authentication failed!");
        }

        User user = userService.getById(userId);
        if (user == null) {
            throw new ServiceException(Constants.CODE_401, "User does not exist, please log in again!");
        }

        //verify token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e){
            throw new ServiceException(Constants.CODE_401,"Token verification failed, please log in again!");
        }

        return true;
    }

}
