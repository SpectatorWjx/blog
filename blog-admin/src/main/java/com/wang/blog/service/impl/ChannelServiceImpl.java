package com.wang.blog.service.impl;

import com.wang.blog.base.lang.Consts;
import com.wang.blog.repository.ChannelRepository;
import com.wang.blog.service.ChannelService;
import com.wang.common.entity.blog.ChannelEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author wjx
 *
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
	public Map<String, ChannelEntity> findMapByIds(Collection<String> ids) {
		List<ChannelEntity> list = channelRepository.findAllById(ids);
		Map<String, ChannelEntity> rets = new HashMap<>();
		list.forEach(po -> rets.put(po.getId(), po));
		return rets;
	}

	@Override
	public ChannelEntity getById(String id) {
		return channelRepository.findById(id).get();
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public void update(ChannelEntity channel) {
		Optional<ChannelEntity> optional = channelRepository.findById(channel.getId());
		ChannelEntity po = optional.orElse(new ChannelEntity());
		BeanUtils.copyProperties(channel, po);
		channelRepository.save(po);
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public void updateWeight(String id, int weighted) {
		ChannelEntity po = channelRepository.findById(id).get();

		int max = Consts.ZERO;
		if (Consts.FEATURED_ACTIVE == weighted) {
			max = channelRepository.maxWeight() + 1;
		}
		po.setWeight(max);
		channelRepository.save(po);
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public void delete(String id) {
		channelRepository.deleteById(id);
	}

	@Override
	public long count() {
		return channelRepository.count();
	}

}
