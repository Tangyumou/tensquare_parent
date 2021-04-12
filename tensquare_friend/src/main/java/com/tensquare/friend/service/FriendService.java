package com.tensquare.friend.service;

import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FriendService {
    @Autowired
    private FriendDao friendDao;
    @Autowired
    private NoFriendDao noFriendDao;
    @Autowired
    private UserClient userClient;

    /**
     * 关注
     *
     * @param userid
     * @param friendid
     */
    public void likeSomeone(String userid, String friendid) {
        Friend friend = friendDao.findByUseridAndFriendid(friendid, userid);
        Friend newFriend = new Friend();
        newFriend.setUserid(userid);
        newFriend.setFriendid(friendid);
        if (friend == null) {
            newFriend.setIslike("0");
        } else {
            newFriend.setIslike("1");
            friend.setIslike("1");
            friendDao.save(friend);
        }
        friendDao.save(newFriend);
        userClient.addFansCount(friendid,1);
        userClient.addFollowCount(userid,1);
    }

    /**
     * 拉黑
     *
     * @param userid
     * @param friendid
     */

    public void dislikeSomeone(String userid, String friendid) {
        Friend first = friendDao.findByUseridAndFriendid(userid,friendid);
        if(first!=null){
            userClient.addFollowCount(userid,-1);
            userClient.addFansCount(friendid,-1);
            friendDao.deleteByUseridAndFriendid(userid, friendid);
        }
        Friend second = friendDao.findByUseridAndFriendid(friendid,userid);
        if(second!=null){
            userClient.addFollowCount(friendid,-1);
            userClient.addFansCount(userid,-1);
            friendDao.deleteByUseridAndFriendid(friendid, userid);
        }
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
    }

    /**
     * 取关
     *
     * @param userid
     * @param friendid
     */

    public void deleteFriend(String userid, String friendid) {
        Friend friend = friendDao.findByUseridAndFriendid(friendid, userid);
        if (friend != null) {
            friend.setIslike("0");
            friendDao.save(friend);
        }
        friendDao.deleteByUseridAndFriendid(userid, friendid);
        userClient.addFollowCount(userid,-1);
        userClient.addFansCount(friendid,-1);
    }

}
