package com.tensquare.recruit.controller;

import com.tensquare.recruit.pojo.Enterprise;
import com.tensquare.recruit.service.EnterpriseService;
import entity.Result;
import entity.StatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/enterprise")
@Api(tags = "Enterprise接口")
public class EnterpriseController {
    @Autowired
    private EnterpriseService enterpriseService;
    @RequestMapping(value = "/search/hotlist",method = RequestMethod.GET)
    @ApiOperation(value = "查询热门企业", httpMethod = "GET")
    public Result hotList(){
        List<Enterprise> list = enterpriseService.hotList("1");
        return new Result(true, StatusCode.OK,"查询成功",list);
    }
}
