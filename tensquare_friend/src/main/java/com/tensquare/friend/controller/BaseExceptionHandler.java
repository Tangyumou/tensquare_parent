package com.tensquare.friend.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class BaseExceptionHandler {
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result exception(Exception e){
        return new Result(false, StatusCode.ERROR,e.getMessage());
    }
}
