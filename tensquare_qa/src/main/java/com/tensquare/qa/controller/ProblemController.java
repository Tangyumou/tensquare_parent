package com.tensquare.qa.controller;

import com.tensquare.qa.pojo.Problem;
import com.tensquare.qa.service.ProblemService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController {
    @Autowired
    private ProblemService problemService;
    @GetMapping("/newlist/{labelid}/{page}/{size}")
    public Result newlist(@PathVariable String labelid, @PathVariable int page, @PathVariable int size){
        Page<Problem> pageData =  problemService.newlist(labelid,page,size);
        return new Result(true, StatusCode.OK,"查询成功",new PageResult<Problem>(pageData.getTotalElements(),pageData.getContent()));
    }
    @GetMapping("/hotlist/{labelid}/{page}/{size}")
    public Result hotlist(String labelid,int page,int size){
        Page<Problem> pageData =  problemService.newlist(labelid,page,size);
        return new Result(true, StatusCode.OK,"查询成功",new PageResult<Problem>(pageData.getTotalElements(),pageData.getContent()));
    }
    @GetMapping("/waitlist/{labelid}/{page}/{size}")
    public Result waitlist(String labelid,int page,int size){
        Page<Problem> pageData =  problemService.newlist(labelid,page,size);
        return new Result(true, StatusCode.OK,"查询成功",new PageResult<Problem>(pageData.getTotalElements(),pageData.getContent()));
    }

}
