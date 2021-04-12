package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping("/spit")
public class spitController {
    @Autowired
    private SpitService spitService;
    @GetMapping
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功",spitService.findAll());
    }
    @GetMapping("/{spitId}")
    public Result findById(@PathVariable String spitId){
        return new Result(true, StatusCode.OK,"查询成功",spitService.findById(spitId));
    }
    @PostMapping
    public Result save(@RequestBody Spit spit){
        spit.setPublishtime(new Date());
        spitService.save(spit);
        return new Result(true,StatusCode.OK,"保存成功");
    }

    @PutMapping("/{spitId}")
    public Result update(@PathVariable String spitId, @RequestBody Spit spit){
        spit.set_id(spitId);
        spitService.update(spit);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    @DeleteMapping("/{spitId}")
    public Result deleteById(@PathVariable String spitId){
        spitService.deleteById(spitId);
        return new Result(true,StatusCode.OK,"删除成功");
    }
    @GetMapping("/comment/{parentId}/{pageNum}/{size}")
    public Result findById(@PathVariable String parentId,@PathVariable int pageNum,@PathVariable int size){
        Page<Spit> pageData = spitService.findByParentid(parentId,pageNum,size);
        return new Result(true, StatusCode.OK,"查询成功",new PageResult<Spit>(pageData.getTotalElements(),pageData.getContent()));
    }

    @PutMapping("/thumbup/{spitId}")
    public Result thumbup(@PathVariable String spitId){
        spitService.thumbup(spitId);
        return new Result(true,StatusCode.OK,"点赞成功");
    }


}
