package com.tensquare.user.service;

import com.tensquare.user.dao.UserDao;
import com.tensquare.user.pojo.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * user服务层
 *
 * @author Administrator
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public User checkUser(User user){
        User userDB = userDao.findByMobile(user.getMobile());
        if(userDB!=null&&encoder.matches(user.getPassword(),userDB.getPassword())){
            return userDB;
        }
        return null;
    }

    /**
     * 粉丝数加一或减一
     * @param userid
     * @param num
     */

    public void addFansCount(String userid,int num){
        User user = userDao.findById(userid).get();
        user.setFanscount(user.getFanscount()+ num);
        userDao.save(user);
    }

    /**
     * 关注数加一或减一
     * @param userid
     * @param num
     */

    public void addFollowCount(String userid,int num){
        User user = userDao.findById(userid).get();
        user.setFollowcount(user.getFollowcount()+num);
        userDao.save(user);
    }



    /**
     * 查询全部列表
     *
     * @return
     */
    public List<User> findAll() {
        return userDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<User> findSearch(Map whereMap, int page, int size) {
        Specification<User> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return userDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<User> findSearch(Map whereMap) {
        Specification<User> specification = createSpecification(whereMap);
        return userDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public User findById(String id) {
        return userDao.findById(id).get();
    }

    /**
     * 增加
     *
     * @param user
     */
    public void add(User user) {
        user.setId(idWorker.nextId() + ""); //雪花分布式ID生成器
        user.setFollowcount(0);//关注数
        user.setFanscount(0);//粉丝数
        user.setOnline(0L);//在线时长
        user.setRegdate(new Date());//注册日期
        user.setUpdatedate(new Date());//更新日期
        user.setLastdate(new Date());//最后登陆日期
        user.setPassword(encoder.encode(user.getPassword()));
        userDao.save(user);
    }

    /**
     * 修改
     *
     * @param user
     */
    public void update(User user) {
        userDao.save(user);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        userDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<User> createSpecification(Map searchMap) {

        return new Specification<User>() {

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                    predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
                }
                // 手机号码
                if (searchMap.get("mobile") != null && !"".equals(searchMap.get("mobile"))) {
                    predicateList.add(cb.like(root.get("mobile").as(String.class), "%" + (String) searchMap.get("mobile") + "%"));
                }
                // 密码
                if (searchMap.get("password") != null && !"".equals(searchMap.get("password"))) {
                    predicateList.add(cb.like(root.get("password").as(String.class), "%" + (String) searchMap.get("password") + "%"));
                }
                // 昵称
                if (searchMap.get("nickname") != null && !"".equals(searchMap.get("nickname"))) {
                    predicateList.add(cb.like(root.get("nickname").as(String.class), "%" + (String) searchMap.get("nickname") + "%"));
                }
                // 性别
                if (searchMap.get("sex") != null && !"".equals(searchMap.get("sex"))) {
                    predicateList.add(cb.like(root.get("sex").as(String.class), "%" + (String) searchMap.get("sex") + "%"));
                }
                // 头像
                if (searchMap.get("image") != null && !"".equals(searchMap.get("image"))) {
                    predicateList.add(cb.like(root.get("image").as(String.class), "%" + (String) searchMap.get("image") + "%"));
                }
                // E-Mail
                if (searchMap.get("email") != null && !"".equals(searchMap.get("email"))) {
                    predicateList.add(cb.like(root.get("email").as(String.class), "%" + (String) searchMap.get("email") + "%"));
                }
                // 兴趣
                if (searchMap.get("interest") != null && !"".equals(searchMap.get("interest"))) {
                    predicateList.add(cb.like(root.get("interest").as(String.class), "%" + (String) searchMap.get("interest") + "%"));
                }
                // 个性
                if (searchMap.get("personality") != null && !"".equals(searchMap.get("personality"))) {
                    predicateList.add(cb.like(root.get("personality").as(String.class), "%" + (String) searchMap.get("personality") + "%"));
                }

                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }

    public void sendSms(String mobile) {
        String checkcode = RandomStringUtils.randomNumeric(6);
        redisTemplate.opsForValue().set("checkcode_" + mobile, checkcode, 5, TimeUnit.MINUTES);
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("checkcode", checkcode);
//        rabbitTemplate.convertAndSend("sms", map);

        System.out.println("验证码为：" + checkcode);
    }

}
