package com.example.managesystem.exception;

import com.example.managesystem.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public Result handle(ServiceException se){
        return Result.error(se.getCode(),se.getMsg());
    }
}
