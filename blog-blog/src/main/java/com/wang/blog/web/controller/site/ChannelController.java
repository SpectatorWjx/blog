package com.wang.blog.web.controller.site;

import com.wang.blog.base.lang.Consts;
import com.wang.blog.base.utils.MarkdownUtils;
import com.wang.blog.service.ChannelService;
import com.wang.blog.service.PostService;
import com.wang.blog.vo.PostVO;
import com.wang.blog.web.controller.BaseController;
import com.wang.common.entity.blog.ChannelEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Channel 主页
 * @author wjx
 * @date 2019/08/13
 */
@Controller
public class ChannelController extends BaseController {
	@Autowired
	private ChannelService channelService;
	@Autowired
	private PostService postService;

	private static final String MARKDOWN = "markdown";
	
	@RequestMapping("/channel/{id}")
	public String channel(@PathVariable String id, ModelMap model,
			HttpServletRequest request) {
		String order = ServletRequestUtils.getStringParameter(request, "order", Consts.order.NEWEST);
		int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);

		ChannelEntity channel = channelService.getById(id);
		// callback params
		model.put("channel", channel);
		model.put("order", order);
		model.put("pageNo", pageNo);
		return view(Views.POST_INDEX);
	}

	@RequestMapping("/post/{id}")
	public String view(@PathVariable String id, ModelMap model) {
		PostVO view = postService.get(id);

		if (Optional.ofNullable(view).isPresent() && MARKDOWN.endsWith(view.getEditor())) {
			view.setContent(MarkdownUtils.renderMarkdown(view.getContent()));
		}
		postService.identityViews(id);
		model.put("view", view);
		return view(Views.POST_VIEW);
	}
}
