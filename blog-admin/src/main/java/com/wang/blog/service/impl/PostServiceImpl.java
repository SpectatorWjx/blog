package com.wang.blog.service.impl;

import com.wang.blog.base.lang.Consts;
import com.wang.blog.base.utils.BeanMapUtils;
import com.wang.blog.base.utils.MarkdownUtils;
import com.wang.blog.base.utils.PreviewTextUtils;
import com.wang.blog.base.utils.ResourceLock;
import com.wang.blog.modules.aspect.PostStatusFilter;
import com.wang.blog.vo.PostVO;
import com.wang.blog.vo.UserVO;
import com.wang.blog.modules.event.PostUpdateEvent;
import com.wang.blog.repository.PostAttributeRepository;
import com.wang.blog.repository.PostRepository;
import com.wang.blog.repository.PostResourceRepository;
import com.wang.blog.repository.ResourceRepository;
import com.wang.blog.service.*;
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
	public Page<PostVO> paging(Pageable pageable, String channelId, Set<String> excludeChannelIds) {
		Page<PostEntity> page = postRepository.findAll((root, query, builder) -> {
			Predicate predicate = builder.conjunction();

			if (!StringUtil.isEmpty(channelId)) {
				predicate.getExpressions().add(
						builder.equal(root.get("channelId").as(String.class), channelId));
			}

			if (null != excludeChannelIds && !excludeChannelIds.isEmpty()) {
				predicate.getExpressions().add(
						builder.not(root.get("channelId").in(excludeChannelIds)));
			}

//			predicate.getExpressions().add(
//					builder.equal(root.get("featured").as(String.class), Consts.FEATURED_DEFAULT));

			return predicate;
		}, pageable);

		return new PageImpl<>(toPosts(page.getContent()), pageable, page.getTotalElements());
	}

	@Override
	public Page<PostVO> paging4Admin(Pageable pageable, String channelId, String title) {
		Page<PostEntity> page = postRepository.findAll((root, query, builder) -> {
            Predicate predicate = builder.conjunction();
			if (!StringUtil.isEmpty(channelId)) {
				predicate.getExpressions().add(
						builder.equal(root.get("channelId").as(String.class), channelId));
			}
			if (StringUtils.isNotBlank(title)) {
				predicate.getExpressions().add(
						builder.like(root.get("title").as(String.class), "%" + title + "%"));
			}
            return predicate;
        }, pageable);

		return new PageImpl<>(toPosts(page.getContent()), pageable, page.getTotalElements());
	}

	@Override
	@PostStatusFilter
	public Page<PostVO> pagingByAuthorId(Pageable pageable, String userId) {
		Page<PostEntity> page = postRepository.findAllByAuthorId(pageable, userId);
		return new PageImpl<>(toPosts(page.getContent()), pageable, page.getTotalElements());
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
			rets.put(po.getId(), BeanMapUtils.copy(po));
			uids.add(po.getAuthorId());
		});
		
		// 加载用户信息
		buildUsers(rets.values(), uids);
		return rets;
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public String post(PostVO post) {
		PostEntity po = new PostEntity();

		BeanUtils.copyProperties(post, po);

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
                attr.setId(po.getId());
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
			d.setChannel(channelService.getById(d.getChannelId()));

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
	@Transactional(rollbackFor = RuntimeException.class)
	public void update(PostVO p){
		Optional<PostEntity> optional = postRepository.findById(p.getId());

		if (optional.isPresent()) {
            String key = ResourceLock.getPostKey(p.getId());
            AtomicInteger lock = ResourceLock.getAtomicInteger(key);
            try {
                synchronized (lock){
                    PostEntity po = optional.get();
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

                    po.setTags(p.getTags());

                    // 保存扩展
                    PostAttributeEntity attributeOptional = postAttributeRepository.findByPostId(po.getId());
                    String originContent = "";
                    PostAttributeEntity attr = new PostAttributeEntity();
                    attr.setContent(p.getContent());
                    attr.setEditor(p.getEditor());
                    attr.setPostId(po.getId());
                    if(Optional.ofNullable(attributeOptional).isPresent()) {
						originContent = attributeOptional.getContent();
						attr.setId(attributeOptional.getId());
					}
                    postAttributeRepository.save(attr);

                    tagService.batchUpdate(po.getTags(), po.getId());

                    countResource(po.getId(), originContent, p.getContent());
                }
            }finally {
                ResourceLock.giveUpAtomicInteger(key);
            }
		}
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public void updateFeatured(String id, int featured) {
		PostEntity po = postRepository.findById(id).get();
		int status = Consts.FEATURED_ACTIVE == featured ? Consts.FEATURED_ACTIVE: Consts.FEATURED_DEFAULT;
		po.setFeatured(status);
		postRepository.save(po);
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public void updateWeight(String id, int weighted) {
		PostEntity po = postRepository.findById(id).get();

		int max = Consts.ZERO;
		if (Consts.FEATURED_ACTIVE == weighted) {
			max = postRepository.maxWeight() + 1;
		}
		po.setWeight(max);
		postRepository.save(po);
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public void delete(String id, String authorId) {
		PostEntity po = postRepository.findById(id).get();
		// 判断文章是否属于当前登录用户
		Assert.isTrue(po.getAuthorId() == authorId, "认证失败");

        String key = ResourceLock.getPostKey(po.getId());
        AtomicInteger lock = ResourceLock.getAtomicInteger(key);
		try	{
			synchronized (lock){
				postRepository.deleteById(id);
				postAttributeRepository.deleteById(id);
				cleanResource(po.getId());
				onPushEvent(po, PostUpdateEvent.ACTION_DELETE);
			}
		}finally {
			ResourceLock.giveUpAtomicInteger(key);
		}
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public void delete(Collection<String> ids) {
		if (CollectionUtils.isNotEmpty(ids)) {
			List<PostEntity> list = postRepository.findAllById(ids);
			list.forEach(po -> {
				String key = ResourceLock.getPostKey(po.getId());
				AtomicInteger lock = ResourceLock.getAtomicInteger(key);
				try	{
					synchronized (lock){
						postRepository.delete(po);
						postAttributeRepository.deleteById(po.getId());
						cleanResource(po.getId());
						onPushEvent(po, PostUpdateEvent.ACTION_DELETE);
					}
				}finally {
					ResourceLock.giveUpAtomicInteger(key);
				}
			});
		}
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public void identityViews(String id) {
		// 次数不清理缓存, 等待文章缓存自动过期
		postRepository.updateViews(id, Consts.IDENTITY_STEP);
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public void identityComments(String id) {
		postRepository.updateComments(id, Consts.IDENTITY_STEP);
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public void favor(String userId, String postId) {
		postRepository.updateFavors(postId, Consts.IDENTITY_STEP);
		favoriteService.add(userId, postId);
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public void unfavor(String userId, String postId) {
		postRepository.updateFavors(postId,  Consts.DECREASE_STEP);
		favoriteService.delete(userId, postId);
	}

	@Override
	@PostStatusFilter
	public long count() {
		return postRepository.count();
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

	private List<PostVO> toPosts(List<PostEntity> posts) {
		List<PostVO> rets = new ArrayList<>();

		HashSet<String> uids = new HashSet<>();
		HashSet<String> groupIds = new HashSet<>();

		posts.forEach(po -> {
			uids.add(po.getAuthorId());
			groupIds.add(po.getChannelId());
			rets.add(BeanMapUtils.copy(po));
		});

		// 加载用户信息
		buildUsers(rets, uids);
		buildGroups(rets, groupIds);

		return rets;
	}

	private void buildUsers(Collection<PostVO> posts, Set<String> uids) {
		Map<String, UserVO> userMap = userService.findMapByIds(uids);
		posts.forEach(p -> p.setAuthor(userMap.get(p.getAuthorId())));
	}

	private void buildGroups(Collection<PostVO> posts, Set<String> groupIds) {
		Map<String, ChannelEntity> map = channelService.findMapByIds(groupIds);
		posts.forEach(p -> p.setChannel(map.get(p.getChannelId())));
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
		String p = "(?<=/_signature/)(.+?)(?=\\.)";
		Pattern pattern = Pattern.compile(p);

		Set<String> md5s = new HashSet<>();

		Matcher originMatcher = pattern.matcher(text);
		while (originMatcher.find()) {
			String key = originMatcher.group();
			md5s.add(key);
		}

		return md5s;
	}
}
