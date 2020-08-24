package com.wang.blog.service;

import com.wang.blog.vo.OptionVO;
import com.wang.common.entity.blog.OptionsEntity;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Map;


/**
 * @author wjx
 *
 */
public interface OptionsService {
	/**
	 * 查询所有配置
	 * @return list
	 */
	List<OptionsEntity> findAll();

	/**
	 * 添加或修改配置
	 * - 修改时根据key判断唯一性
	 * @param options
	 */
	void update(Map<String, String> options);

	/**
	 * 查询配置
	 * @return
	 */
	OptionVO findOption();

	/**
	 * 刷新缓存
	 */
    void reloadCache();

	/**
	 * 初始化配置
	 * @param resource
	 */
	void initSettings(Resource resource);
}
