package com.wang.blog.base.config;

import com.wang.blog.base.lang.Consts;
import com.wang.blog.service.ChannelService;
import com.wang.blog.service.MailService;
import com.wang.blog.service.OptionsService;
import com.wang.common.entity.blog.OptionsEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.Map;

/**
 * 加载配置信息到系统
 * @author wjx
 * @date 2019/12/10
 */
@Slf4j
@Order(2)
@Component
public class ContextStartup implements ApplicationRunner, ServletContextAware {
    @Autowired
    private OptionsService optionsService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private MailService mailService;
    @Autowired
    private SiteOptions siteOptions;

    private ServletContext servletContext;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        log.info("initialization ...");
        reloadOptions(true);
        resetChannels();
        log.info("OK, completed");
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public void reloadOptions(boolean startup) {
        List<OptionsEntity> options = optionsService.findAll();

        log.info("find options ({})...", options.size());

        Map<String, String> map = siteOptions.getOptions();
        options.forEach(opt -> {
            if (StringUtils.isNoneBlank(opt.getKey(), opt.getValue())) {
                map.put(opt.getKey(), opt.getValue());
            }
        });
        servletContext.setAttribute("options", map);
        servletContext.setAttribute("site", siteOptions);
        mailService.config();

        System.setProperty("site.location", siteOptions.getLocation());
    }

    public void resetChannels() {
        servletContext.setAttribute("channels", channelService.findAll(Consts.STATUS_NORMAL));
    }

}
