/**
 *
 */package com.wang.blog.modules.template.directive;

import com.wang.blog.vo.CommentVO;
import com.wang.blog.service.CommentService;
import com.wang.blog.modules.template.DirectiveHandler;
import com.wang.blog.modules.template.TemplateDirective;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * 根据作者取评论列表
 *
 * @author wjx
 * @date 2019/02/22
 */
@Component
public class UserCommentsDirective extends TemplateDirective {
    @Autowired
	private CommentService commentService;

	@Override
	public String getName() {
		return "user_comments";
	}

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        String userId = handler.getString("userId", "");
        Pageable pageable = wrapPageable(handler);

        Page<CommentVO> result = commentService.pagingByAuthorId(pageable, userId);
        handler.put(RESULTS, result).render();
    }

}
