package com.wang.blog.service.impl;

import com.wang.blog.base.lang.Consts;
import com.wang.blog.repository.ChannelRepository;
import com.wang.blog.service.ChannelService;
import com.wang.common.entity.blog.ChannelEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author wjx
 * @date 2019/08/13
 */
@Service
@Transactional(readOnly = true)
public class ChannelServiceImpl implements ChannelService {
	@Autowired
	private ChannelRepository channelRepository;

	@Override
	public List<ChannelEntity> findAll(int status) {
		Sort sort = Sort.by(Sort.Direction.DESC, "weight", "id");
		List<ChannelEntity> list;
		if (status > Consts.IGNORE) {
			list = channelRepository.findAllByStatus(status, sort);
		} else {
			list = channelRepository.findAll(sort);
		}
		return list;
	}

	@Override
	public ChannelEntity getById(String id) {
		Optional<ChannelEntity> optional = channelRepository.findById(id);
		return optional.orElse(null);
	}
}
