package com.tensquare.user.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * user实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="tb_user")
public class User implements Serializable{

	@Id
	private String id;//ID


	
	private String mobile;//手机号码
	private String password;//密码
	private String nickname;//昵称
	private String sex;//性别
	private java.util.Date birthday;//出生年月日
	private String image;//头像
	private String email;//E-Mail
	private java.util.Date regdate;//注册日期
	private java.util.Date updatedate;//修改日期
	private java.util.Date lastdate;//最后登陆日期
	private Long online;//在线时长（分钟）
	private String interest;//兴趣
	private String personality;//个性
	private Integer fanscount;//粉丝数
	private Integer followcount;//关注数


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public Date getLastdate() {
		return lastdate;
	}

	public void setLastdate(Date lastdate) {
		this.lastdate = lastdate;
	}

	public Long getOnline() {
		return online;
	}

	public void setOnline(Long online) {
		this.online = online;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getPersonality() {
		return personality;
	}

	public void setPersonality(String personality) {
		this.personality = personality;
	}

	public Integer getFanscount() {
		return fanscount;
	}

	public void setFanscount(Integer fanscount) {
		this.fanscount = fanscount;
	}

	public Integer getFollowcount() {
		return followcount;
	}

	public void setFollowcount(Integer followcount) {
		this.followcount = followcount;
	}
}
