package com.tensquare.friend.controller;

import com.tensquare.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/friend")
public class FriendController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private FriendService friendService;
    /**
     * 选择关注或拉黑
     * @param friendid
     * @param type
     * @return
     */
    @PostMapping("/like/{friendid}/{type}")
    public Result selectFriend(@PathVariable String friendid,@PathVariable String type){
        Claims claims = (Claims) request.getAttribute("user_claims");
        if(claims==null){
            return new Result(false, StatusCode.ACCESSERROR,"权限不足");
        }
        String userid = claims.getId();
        if(type.equals("1")){
            // 关注
            friendService.likeSomeone(userid,friendid);
            return new Result(true,StatusCode.OK,"关注成功");
        }else{
            // 拉黑
            friendService.dislikeSomeone(userid,friendid);
            return new Result(true,StatusCode.OK,"拉黑成功");
        }
    }

    /**
     * 取关
     * @param friendid
     * @return
     */
    @DeleteMapping("/{friendid}")
    public Result deleteFriend(@PathVariable String friendid){
        Claims claims = (Claims) request.getAttribute("user_claims");
        if(claims==null){
            return new Result(false, StatusCode.ACCESSERROR,"权限不足");
        }
        String userid = claims.getId();
        friendService.deleteFriend(userid,friendid);
        return new Result(true,StatusCode.OK,"删除成功");
    }
}
