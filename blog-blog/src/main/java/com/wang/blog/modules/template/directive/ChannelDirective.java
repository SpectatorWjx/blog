package com.wang.blog.modules.template.directive;

import com.wang.blog.service.ChannelService;
import com.wang.blog.modules.template.DirectiveHandler;
import com.wang.blog.modules.template.TemplateDirective;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wjx
 * @date 2019/08/27
 */
@Component
public class ChannelDirective extends TemplateDirective {
    @Autowired
    private ChannelService channelService;

    @Override
    public String getName() {
        return "channel";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        String id = handler.getString("id", "");
        handler.put(RESULT, channelService.getById(id)).render();
    }
}