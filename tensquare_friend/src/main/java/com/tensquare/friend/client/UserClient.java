package com.tensquare.friend.client;

import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient("tensquare-user")
public interface UserClient {
    @PutMapping("/user/addfans/{userid}/{num}")
    Result addFansCount(@PathVariable("userid") String userid, @PathVariable("num") int num);

    @PutMapping("/user/addfllow/{userid}/{num}")
    Result addFollowCount(@PathVariable("userid") String userid, @PathVariable("num") int num);
}
