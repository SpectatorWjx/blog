package com.wang.blog.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wang.blog.base.aspect.PostStatusFilter;
import com.wang.blog.base.lang.Consts;
import com.wang.blog.base.utils.BeanMapUtils;
import com.wang.blog.base.utils.MarkdownUtils;
import com.wang.blog.base.utils.PreviewTextUtils;
import com.wang.blog.base.utils.ResourceLock;
import com.wang.blog.vo.ChannelVo;
import com.wang.blog.vo.PostVO;
import com.wang.blog.vo.UserVO;
import com.wang.blog.modules.event.PostUpdateEvent;
import com.wang.blog.repository.PostAttributeRepository;
import com.wang.blog.repository.PostRepository;
import com.wang.blog.repository.PostResourceRepository;
import com.wang.blog.repository.ResourceRepository;
import com.wang.blog.service.*;
import com.wang.common.common.utils.BeanCopyUtils;
import com.wang.common.common.utils.StringUtil;
import com.wang.common.entity.blog.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author wjx
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PostServiceImpl implements PostService {
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private PostAttributeRepository postAttributeRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private FavoriteService favoriteService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private TagService tagService;
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private PostResourceRepository postResourceRepository;
	@Autowired
	private ResourceRepository resourceRepository;

	@Override
	@PostStatusFilter
	public Page<PostVO> paging(Pageable pageable, String channelId) {
		Page<Map<String, Object>> mapPage = postRepository.findPostPageByChannel(channelId, pageable);
		return new PageImpl<>(toPosts(mapPage.getContent()), pageable, mapPage.getTotalElements());
	}

	@Override
	@PostStatusFilter
	public List<PostVO> listByAuthorId(String userId) {
		Sort sort = Sort.by(new Sort.Order(Sort.Direction.DESC, "createTime"));
		List<PostEntity> list = postRepository.findAllByAuthorId(userId, sort);
		List<PostVO> voList = new ArrayList<>();
		list.forEach(e ->{
			voList.add(BeanMapUtils.copy(e));
		});
		return voList;
	}

	@Override
	@PostStatusFilter
	public List<PostVO> findLatestPosts(int maxResults) {
		return find("createTime", maxResults).stream().map(BeanMapUtils::copy).collect(Collectors.toList());
	}
	
	@Override
	@PostStatusFilter
	public List<PostVO> findHottestPosts(int maxResults) {
		return find("views", maxResults).stream().map(BeanMapUtils::copy).collect(Collectors.toList());
	}
	
	@Override
	@PostStatusFilter
	public Map<String, PostVO> findMapByIds(Set<String> ids) {
		if (ids == null || ids.isEmpty()) {
			return Collections.emptyMap();
		}

		List<PostEntity> list = postRepository.findAllById(ids);
		Map<String, PostVO> rets = new HashMap<>();

		HashSet<String> uids = new HashSet<>();

		list.forEach(po -> {
			PostVO postVO = BeanMapUtils.copy(po);
			ChannelEntity channel = channelService.getById(postVO.getChannelId());
			postVO.setChannelName(channel.getName());
			rets.put(po.getId(), postVO);
			uids.add(po.getAuthorId());
		});
		
		// 加载用户信息
		buildUsers(rets.values(), uids);
		return rets;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String post(PostVO post) {
		PostEntity po = new PostEntity();

		BeanCopyUtils.copyPropertiesIgnoreNull(post, po);

		po.setStatus(post.getStatus());

		// 处理摘要
		if (StringUtils.isBlank(post.getSummary())) {
			po.setSummary(trimSummary(post.getEditor(), post.getContent()));
		} else {
			po.setSummary(post.getSummary());
		}

		postRepository.save(po);
		tagService.batchUpdate(po.getTags(), po.getId());

        String key = ResourceLock.getPostKey(po.getId());
        AtomicInteger lock = ResourceLock.getAtomicInteger(key);
        try {
            synchronized (lock){
                PostAttributeEntity attr = new PostAttributeEntity();
                attr.setContent(post.getContent());
                attr.setEditor(post.getEditor());
                attr.setPostId(po.getId());
                postAttributeRepository.save(attr);

                countResource(po.getId(), null,  attr.getContent());
                onPushEvent(po, PostUpdateEvent.ACTION_PUBLISH);
                return po.getId();
            }
        }finally {
            ResourceLock.giveUpAtomicInteger(key);
        }
	}
	
	@Override
	public PostVO get(String id) {
		Optional<PostEntity> po = postRepository.findById(id);
		if (po.isPresent()) {
			PostVO d = BeanMapUtils.copy(po.get());
			d.setAuthor(userService.get(d.getAuthorId()));
			d.setChannelName(channelService.getById(d.getChannelId()).getName());
			PostAttributeEntity attr = postAttributeRepository.findByPostId(d.getId());
			d.setContent(attr.getContent());
			d.setEditor(attr.getEditor());
			return d;
		}
		return null;
	}

	/**
	 * 更新文章方法
	 * @param p
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(PostVO p){
		Optional<PostEntity> optional = postRepository.findById(p.getId());

		if (optional.isPresent()) {
            String key = ResourceLock.getPostKey(p.getId());
            AtomicInteger lock = ResourceLock.getAtomicInteger(key);
            try {
                synchronized (lock){
                    PostEntity po = optional.get();
					//标题
                    po.setTitle(p.getTitle());
                    po.setChannelId(p.getChannelId());
                    po.setThumbnail(p.getThumbnail());
                    po.setStatus(p.getStatus());

                    // 处理摘要
                    if (StringUtils.isBlank(p.getSummary())) {
                        po.setSummary(trimSummary(p.getEditor(), p.getContent()));
                    } else {
                        po.setSummary(p.getSummary());
                    }

					//标签
                    po.setTags(p.getTags());

                    // 保存扩展
                    PostAttributeEntity attributeOptional = postAttributeRepository.findByPostId(po.getId());
                    String originContent = "";
                    if (Optional.ofNullable(attributeOptional).isPresent()){
                        originContent = p.getContent();
						attributeOptional.setContent(originContent);
						attributeOptional.setEditor(p.getEditor());
						postAttributeRepository.save(attributeOptional);
                    }

                    tagService.batchUpdate(po.getTags(), po.getId());

                    countResource(po.getId(), originContent, p.getContent());
                }
            }finally {
                ResourceLock.giveUpAtomicInteger(key);
            }
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(String id, String authorId) {
		PostEntity po = postRepository.findById(id).get();
		// 判断文章是否属于当前登陆用户
		Assert.isTrue(po.getAuthorId().equals(authorId), "认证失败");

        String key = ResourceLock.getPostKey(po.getId());
        AtomicInteger lock = ResourceLock.getAtomicInteger(key);
		try	{
			synchronized (lock){
				postRepository.deleteById(id);
				postAttributeRepository.deleteByPostId(id);
				cleanResource(po.getId());
				onPushEvent(po, PostUpdateEvent.ACTION_DELETE);
			}
		}finally {
			ResourceLock.giveUpAtomicInteger(key);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void identityViews(String id) {
		// 次数不清理缓存, 等待文章缓存自动过期
		postRepository.updateViews(id, Consts.IDENTITY_STEP);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void identityComments(String id) {
		postRepository.updateComments(id, Consts.IDENTITY_STEP);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void favor(String userId, String postId) {
		postRepository.updateFavors(postId, Consts.IDENTITY_STEP);
		favoriteService.add(userId, postId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void unFavor(String userId, String postId) {
		postRepository.updateFavors(postId,  Consts.DECREASE_STEP);
		favoriteService.delete(userId, postId);
	}

	@PostStatusFilter
	private List<PostEntity> find(String orderBy, int size) {
		Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, orderBy));

		Set<String> excludeChannelIds = new HashSet<>();

		List<ChannelEntity> channels = channelService.findAll(Consts.STATUS_CLOSED);
		if (channels != null) {
			channels.forEach((c) -> excludeChannelIds.add(c.getId()));
		}

		Page<PostEntity> page = postRepository.findAll((root, query, builder) -> {
			Predicate predicate = builder.conjunction();
			if (excludeChannelIds.size() > 0) {
				predicate.getExpressions().add(
						builder.not(root.get("channelId").in(excludeChannelIds)));
			}
			return predicate;
		}, pageable);
		return page.getContent();
	}

	/**
	 * 截取文章内容
	 * @param text
	 * @return
	 */
	private String trimSummary(String editor, final String text){
		if (Consts.EDITOR_MARKDOWN.endsWith(editor)) {
			return PreviewTextUtils.getText(MarkdownUtils.renderMarkdown(text), 126);
		} else {
			return PreviewTextUtils.getText(text, 126);
		}
	}

	private List<PostVO> toPosts(List<Map<String, Object>> posts) {
		List<PostVO> voList = new ArrayList<>();
		posts.forEach(m -> {
			PostVO vo = JSONObject.parseObject(JSONObject.toJSONString(m), PostVO.class);
			UserVO author = new UserVO();
			author.setId((String)m.get("userId"));
			author.setAvatar((String)m.get("avatar"));
			author.setName((String)m.get("userName"));
			vo.setAuthor(author);
			voList.add(vo);
		});
		return voList;
	}

	private void buildUsers(Collection<PostVO> posts, Set<String> uids) {
		Map<String, UserVO> userMap = userService.findMapByIds(uids);
		posts.forEach(p -> p.setAuthor(userMap.get(p.getAuthorId())));
	}

	private void onPushEvent(PostEntity post, int action) {
		PostUpdateEvent event = new PostUpdateEvent(System.currentTimeMillis());
		event.setPostId(post.getId());
		event.setUserId(post.getAuthorId());
		event.setAction(action);
		applicationContext.publishEvent(event);
	}

	private void countResource(String postId, String originContent, String newContent){
	    if (StringUtils.isEmpty(originContent)){
	        originContent = "";
        }
        if (StringUtils.isEmpty(newContent)){
	        newContent = "";
        }

		Set<String> exists = extractImageMd5(originContent);
		Set<String> news = extractImageMd5(newContent);

        List<String> adds = ListUtils.removeAll(news, exists);
		List<String> deleteds = ListUtils.removeAll(exists, news);

		if (adds.size() > 0) {
			List<ResourceEntity> resources = resourceRepository.findByMd5In(adds);

			List<PostResourceEntity> prs = resources.stream().map(n -> {
				PostResourceEntity pr = new PostResourceEntity();
				pr.setResourceId(n.getId());
				pr.setPostId(postId);
				pr.setPath(n.getPath());
				return pr;
			}).collect(Collectors.toList());
			postResourceRepository.saveAll(prs);

			resourceRepository.updateAmount(adds, 1);
		}

		if (deleteds.size() > 0) {
			List<ResourceEntity> resources = resourceRepository.findByMd5In(deleteds);
			List<String> rids = resources.stream().map(ResourceEntity::getId).collect(Collectors.toList());
			postResourceRepository.deleteByPostIdAndResourceIdIn(postId, rids);
			resourceRepository.updateAmount(deleteds, -1);
		}
	}

	private void cleanResource(String postId) {
		List<PostResourceEntity> list = postResourceRepository.findByPostId(postId);
		if (null == list || list.isEmpty()) {
			return;
		}
		List<String> rids = list.stream().map(PostResourceEntity::getResourceId).collect(Collectors.toList());
		resourceRepository.updateAmountByIds(rids, -1);
		postResourceRepository.deleteByPostId(postId);
	}

	private Set<String> extractImageMd5(String text) {
		String compile = "(?<=/_signature/)(.+?)(?=\\.)";
		Pattern pattern = Pattern.compile(compile);
		Set<String> md5s = new HashSet<>();

		Matcher originMatcher = pattern.matcher(text);
		while (originMatcher.find()) {
			String key = originMatcher.group();
			md5s.add(key);
		}
		return md5s;
	}
}
