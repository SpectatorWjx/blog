package com.wang.blog.service.impl;

import com.wang.blog.base.utils.BeanMapUtils;
import com.wang.blog.vo.CommentVO;
import com.wang.blog.vo.PostVO;
import com.wang.blog.vo.UserVO;
import com.wang.blog.repository.CommentRepository;
import com.wang.blog.service.CommentService;
import com.wang.blog.service.PostService;
import com.wang.blog.service.UserEventService;
import com.wang.blog.service.UserService;
import com.wang.common.common.utils.DateUtil;
import com.wang.common.common.utils.StringUtil;
import com.wang.common.entity.blog.CommentEntity;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @author wjx
 *
 */
@Service
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private UserEventService userEventService;
	@Autowired
	private PostService postService;

	@Override
	public Page<CommentVO> pagingByAuthorId(Pageable pageable, String authorId) {
		Page<CommentEntity> page = commentRepository.findAllByAuthorId(pageable, authorId);

		List<CommentVO> rets = new ArrayList<>();
		Set<String> parentIds = new HashSet<>();
		Set<String> uids = new HashSet<>();
		Set<String> postIds = new HashSet<>();

		page.getContent().forEach(po -> {
			CommentVO c = BeanMapUtils.copy(po);

			if (StringUtil.isEmpty(c.getPid())) {
				parentIds.add(c.getPid());
			}
			uids.add(c.getAuthorId());
			postIds.add(c.getPostId());

			rets.add(c);
		});

		// 加载父节点
		buildParent(rets, parentIds);

		buildUsers(rets, uids);
		buildPosts(rets, postIds);

		return new PageImpl<>(rets, pageable, page.getTotalElements());
	}

	@Override
	public Page<CommentVO> pagingByPostId(Pageable pageable, String postId) {
		Page<CommentEntity> page = commentRepository.findAllByPostId(pageable, postId);
		
		List<CommentVO> rets = new ArrayList<>();
		Set<String> parentIds = new HashSet<>();
		Set<String> uids = new HashSet<>();

		page.getContent().forEach(po -> {
			CommentVO c = BeanMapUtils.copy(po);
			c.setCreateTime(DateUtil.formatTime(po.getCreateTime()));
			if (!StringUtil.isEmpty(c.getPid())) {
				parentIds.add(c.getPid());
			}
			uids.add(c.getAuthorId());
			rets.add(c);
		});
		// 加载父节点
		buildParent(rets, parentIds);
		buildUsers(rets, uids);
		return new PageImpl<>(rets, pageable, page.getTotalElements());
	}

	@Override
	public Map<String, CommentVO> findByIds(Set<String> ids) {
		List<CommentEntity> list = commentRepository.findAllById(ids);
		Map<String, CommentVO> ret = new HashMap<>();
		Set<String> uids = new HashSet<>();

		list.forEach(po -> {
			uids.add(po.getAuthorId());
			ret.put(po.getId(), BeanMapUtils.copy(po));
		});

		buildUsers(ret.values(), uids);
		return ret;
	}

	@Override
	public CommentEntity findById(String id) {
		return commentRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public String post(CommentVO comment) {
		CommentEntity po = new CommentEntity();
		
		po.setAuthorId(comment.getAuthorId());
		po.setPostId(comment.getPostId());
		po.setContent(comment.getContent());
		po.setPid(comment.getPid());
		commentRepository.save(po);

		userEventService.identityComment(comment.getAuthorId(), true);
		return po.getId();
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public void delete(String id, String authorId) {
		Optional<CommentEntity> optional = commentRepository.findById(id);
		if (optional.isPresent()) {
			CommentEntity po = optional.get();
			// 判断文章是否属于当前登陆用户
			Assert.isTrue(po.getAuthorId() == authorId, "认证失败");
			commentRepository.deleteById(id);

			userEventService.identityComment(authorId, false);
		}
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public void deleteByPostId(String postId) {
		List<CommentEntity> list = commentRepository.removeByPostId(postId);
		if (CollectionUtils.isNotEmpty(list)) {
			Set<String> userIds = new HashSet<>();
			list.forEach(n -> userIds.add(n.getAuthorId()));
			userEventService.identityComment(userIds, false);
		}
	}

	@Override
	public long countByAuthorIdAndPostId(String authorId, String toId) {
		return commentRepository.countByAuthorIdAndPostId(authorId, toId);
	}

	private void buildUsers(Collection<CommentVO> posts, Set<String> uids) {
		Map<String, UserVO> userMap = userService.findMapByIds(uids);

		posts.forEach(p -> p.setAuthor(userMap.get(p.getAuthorId())));
	}

	private void buildPosts(Collection<CommentVO> comments, Set<String> postIds) {
		Map<String, PostVO> postMap = postService.findMapByIds(postIds);
		comments.forEach(p -> p.setPost(postMap.get(p.getPostId())));
	}

	private void buildParent(Collection<CommentVO> comments, Set<String> parentIds) {
		if (!parentIds.isEmpty()) {
			Map<String, CommentVO> pm = findByIds(parentIds);

			comments.forEach(c -> {
				if (!StringUtil.isEmpty(c.getPid())) {
					c.setParent(pm.get(c.getPid()));
				}
			});
		}
	}

}
