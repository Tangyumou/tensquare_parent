package com.tensquare.recruit.controller;

import com.tensquare.recruit.pojo.Recruit;
import com.tensquare.recruit.service.RecruitService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/recruit")
public class RecruitController {
    @Autowired
    private RecruitService recruitService;
    @RequestMapping(value = "/search/recommend",method = RequestMethod.GET)
    public Result recommend(){
        List<Recruit> list = recruitService.recommend();
        return new Result(true, StatusCode.OK,"查询成功",list);
    }
    @RequestMapping(value = "/search/newlist",method = RequestMethod.GET)
    public Result newlist(){
        List<Recruit> list = recruitService.newList();
        return new Result(true, StatusCode.OK,"查询成功",list);
    }
}
