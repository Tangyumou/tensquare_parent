package com.tensquare.user.controller;
import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
/**
 * user控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private JwtUtil jwtUtil;
	@PutMapping("/addfans/{userid}/{num}")
	public Result addFansCount(@PathVariable String userid,@PathVariable int num){
		userService.addFansCount(userid,num);
		return new Result(true,StatusCode.OK,"成功");
	}
	@PutMapping("/addfllow/{userid}/{num}")
	public Result addFollowCount(@PathVariable String userid,@PathVariable int num){
		userService.addFollowCount(userid,num);
		return new Result(true,StatusCode.OK,"成功");
	}

	@PostMapping("/login")
	public Result login(@RequestBody User user){
		user = userService.checkUser(user);
		if(user!=null){
			String token = jwtUtil.createJWT(user.getId(),user.getNickname(),"user");
			Map<String,String> map = new HashMap<>();
			map.put("roles","user");
			map.put("token",token);
			return new Result(true,StatusCode.OK,"登录成功",map);
		}
		return new Result(false,StatusCode.LOGINERROR,"登录失败");
	}

	/**
	 * 发送短信验证码
	 */
	@PostMapping("/sendsms/{mobile}")
	public Result sendSms(@PathVariable String mobile){
		userService.sendSms(mobile);
		return new Result(true,StatusCode.OK,"发送成功");
	}

	/**
	 *
	 * @param code
	 * @param user
	 * @return
	 */
	@PostMapping("/register/{code}")
	public Result register(@PathVariable String code,@RequestBody User user){
//		System.out.println(user.getMobile());
		String checkcodeRedis = (String)redisTemplate.opsForValue().get("checkcode_"+user.getMobile());
//		System.out.println(checkcodeRedis);
		if(checkcodeRedis==null){
			return new Result(false,StatusCode.ERROR,"请先获取验证码");
		}
		if(!checkcodeRedis.equals(code)){
			return new Result(false,StatusCode.ERROR,"验证码错误");
		}
		userService.add(user);
		return new Result(true,StatusCode.OK,"注册成功");
	}
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",userService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",userService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<User> pageList = userService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<User>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",userService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param user
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody User user  ){
		userService.add(user);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param user
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody User user, @PathVariable String id ){
		user.setId(id);
		userService.update(user);
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id){
//		String authHeader = request.getHeader("Authorization");
//		if(authHeader==null){
//			return new Result(false,StatusCode.ACCESSERROR,"权限不足");
//		}
//		if(!authHeader.startsWith("bearer ")){
//			return new Result(false,StatusCode.ACCESSERROR,"权限不足");
//		}
//		String token = authHeader.substring(7);
//		Claims claims;
//		try{
//			claims = jwtUtil.parseJWT(token);
//		}catch (Exception e){
//			return new Result(false,StatusCode.ACCESSERROR,"权限不足");
//		}
//
//		if(!"admin".equals(claims.get("roles"))){
//			return new Result(false,StatusCode.ACCESSERROR,"权限不足");
//		}
		Claims claims = (Claims) request.getAttribute("admin_claims");
		if(claims==null){
			return new Result(false,StatusCode.ACCESSERROR,"权限不足");
		}

		userService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	
}
