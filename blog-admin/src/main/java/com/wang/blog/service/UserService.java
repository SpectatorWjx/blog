package com.wang.blog.service;

import com.wang.blog.base.lang.Consts;
import com.wang.blog.vo.AccountProfile;
import com.wang.blog.vo.UserVO;
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
	 * 分页查询
	 * @param pageable
	 * @param name
	 */
	Page<UserVO> paging(Pageable pageable, String name);

	Map<String, UserVO> findMapByIds(Set<String> ids);

	/**
	 * 登录
	 * @param username
	 * @param password
	 * @return
	 */
	AccountProfile login(String username, String password);

	/**
	 * 登录,用于记住登录时获取用户信息
	 * @param id
	 * @return
	 */
	AccountProfile findProfile(String id);

	/**
	 * 注册
	 * @param user
	 */
	UserVO register(UserVO user);

	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	@CacheEvict(key = "#user.getId()")
	AccountProfile update(UserVO user);

	/**
	 * 修改用户信息
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

	UserVO getByUsername(String username);

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
	 * 修改用户状态
	 * @param id
	 * @param status
	 */
	void updateStatus(String id, int status);

	long count();

}
