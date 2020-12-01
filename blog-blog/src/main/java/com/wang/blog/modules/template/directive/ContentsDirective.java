/**
 *
 */package com.wang.blog.modules.template.directive;

import com.wang.blog.base.lang.Consts;
import com.wang.blog.base.utils.BeanMapUtils;
import com.wang.blog.vo.PostVO;
import com.wang.blog.service.ChannelService;
import com.wang.blog.service.PostService;
import com.wang.blog.modules.template.DirectiveHandler;
import com.wang.blog.modules.template.TemplateDirective;
import com.wang.common.common.utils.StringUtil;
import com.wang.common.entity.blog.ChannelEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author wjx
 * @date 2019/08/27
 */
@Component
public class ContentsDirective extends TemplateDirective {
    @Autowired
    private PostService postService;

    @Override
    public String getName() {
        return "contents";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        String channelId = handler.getString("channelId", null);
        String order = handler.getString("order", Consts.order.NEWEST);

        Pageable pageable = wrapPageable(handler, Sort.by(Sort.Direction.DESC, BeanMapUtils.postOrder(order)));
        Page<PostVO> result = postService.paging(pageable, channelId);
        handler.put(RESULTS, result).render();
    }
}
