package com.wang.blog.service;

import com.wang.blog.base.lang.Consts;
import com.wang.blog.param.UserParam;
import com.wang.blog.vo.AccountProfile;
import com.wang.blog.vo.UserVO;
import com.wang.common.entity.user.UserEntity;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Set;

/**
 * @author wjx
 *
 */
@CacheConfig(cacheNames = Consts.CACHE_USER)
public interface UserService {

	/**
	 * 根据ids查询map
	 * @param ids
	 * @return
	 */
	Map<String, UserVO> findMapByIds(Set<String> ids);

	/**
	 * 登陆
	 * @param username
	 * @param password
	 * @return
	 */
	AccountProfile login(String username, String password);

	/**
	 * 登陆
	 * @param username
	 * @param openId
	 * @return
	 */
	AccountProfile loginOther(String username, String openId);


	/**
	 * 登陆,用于记住登陆时获取用户信息
	 * @param id
	 * @return
	 */
	AccountProfile findProfile(String id);

	/**
	 * 注册
	 * @param user
	 * @param active
	 * @return
	 */
	UserEntity register(UserParam user, Boolean active);

	/**
	 * 激活
	 * @param userId
	 * @param state
	 * @return
	 */
	UserEntity activation(String userId, String state);

	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	@CacheEvict(key = "#user.getId()")
	AccountProfile update(UserVO user);

	/**
	 * 修改用户信息
	 * @param id
	 * @param email
	 * @return
	 */
	@CacheEvict(key = "#id")
	AccountProfile updateEmail(String id, String email);

	/**
	 * 查询单个用户
	 * @param userId
	 * @return
	 */
	@Cacheable(key = "#userId")
	UserVO get(String userId);

	/**
	 * 根据用户名查询
	 * @param userName
	 * @return
	 */
	UserEntity findByUsername(String userName);

	/**
	 * 根据用户名查询
	 * @param username
	 * @return
	 */
	UserVO getByUsername(String username);

	/**
	 * 根据邮件查询
	 * @param email
	 * @return
	 */
	UserVO getByEmail(String email);

	/**
	 * 修改头像
	 * @param id
	 * @param path
	 * @return
	 */
	@CacheEvict(key = "#id")
	AccountProfile updateAvatar(String id, String path);

	/**
	 * 修改密码
	 * @param id
	 * @param newPassword
	 */
	void updatePassword(String id, String newPassword);

	/**
	 * 修改密码
	 * @param id
	 * @param oldPassword
	 * @param newPassword
	 */
	void updatePassword(String id, String oldPassword, String newPassword);


	/**
	 * 修改密码
	 * @param user
	 */
	void updateUser(UserEntity user);

}
