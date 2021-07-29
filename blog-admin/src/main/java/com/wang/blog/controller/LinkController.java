package com.wang.blog.controller;

import com.wang.common.common.base.BaseException;
import com.wang.common.common.base.Result;
import com.wang.blog.base.page.BasePage;
import com.wang.blog.vo.LinkVo;
import com.wang.blog.service.link.LinkService;
import com.wang.common.entity.other.LinkEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wjx
 *
 * @email 1332504948@qq.com
 * @link http://blog.delpast.com
 */
@Controller
@RequestMapping("/admin/link")
public class LinkController  extends BaseController {

    @Resource
    private LinkService linkService;

    @RequestMapping("/list")
    public String list(ModelMap model) {
        Pageable pageable = wrapPageable();
        BasePage basePage = linkService.getBlogLinkPage(pageable);
        model.put("page", basePage);
        return "/admin/link/list";
    }

    @RequestMapping("/page")
    @ResponseBody
    public Result page() {
        Pageable pageable = wrapPageable();
        BasePage basePage = linkService.getBlogLinkPage(pageable);
        return Result.success(basePage);
    }

    /**
     * 友链添加
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestParam("linkType") Integer linkType,
                       @RequestParam("linkName") String linkName,
                       @RequestParam("linkUrl") String linkUrl,
                       @RequestParam("linkRank") Integer linkRank,
                       @RequestParam("linkDescription") String linkDescription) {
        if (linkType == null || linkType < 0 || linkRank == null || linkRank < 0 || StringUtils.isEmpty(linkName) || StringUtils.isEmpty(linkName) || StringUtils.isEmpty(linkUrl) || StringUtils.isEmpty(linkDescription)) {
           throw new BaseException("参数异常！");
        }
        LinkEntity link = new LinkEntity();
        link.setLinkType(linkType);
        link.setLinkRank(linkRank);
        link.setLinkName(linkName);
        link.setLinkUrl(linkUrl);
        link.setLinkDescription(linkDescription);
        linkService.saveLink(link);
        return Result.success();
    }

    /**
     * 详情
     */
    @GetMapping("/info/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") String id) {
        LinkEntity link = linkService.selectById(id);
        LinkVo linkVo = new LinkVo();
        BeanUtils.copyProperties(link, linkVo);
        return Result.success(linkVo);
    }

    /**
     * 友链修改
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestParam("linkId") String linkId,
                       @RequestParam("linkType") Integer linkType,
                       @RequestParam("linkName") String linkName,
                       @RequestParam("linkUrl") String linkUrl,
                       @RequestParam("linkRank") Integer linkRank,
                       @RequestParam("linkDescription") String linkDescription) {
        LinkEntity tempLink = linkService.selectById(linkId);
        if (tempLink == null) {
            throw new BaseException("无数据！");
        }
        if (linkType == null || linkType < 0 || linkRank == null || linkRank < 0 || StringUtils.isEmpty(linkName) || StringUtils.isEmpty(linkName) || StringUtils.isEmpty(linkUrl) || StringUtils.isEmpty(linkDescription)) {
            throw new BaseException("参数异常！");
        }
        tempLink.setLinkType(linkType);
        tempLink.setLinkName(linkName);
        tempLink.setLinkUrl(linkUrl);
        tempLink.setLinkRank(linkRank);
        tempLink.setLinkDescription(linkDescription);
        linkService.updateLink(tempLink);
        return Result.success();
    }

    /**
     * 友链删除
     */
    @RequestMapping(value = "/deleteBatch")
    @ResponseBody
    public Result deleteBatch(@RequestParam("id") List<String> id) {
        linkService.deleteBatch(id);
        return Result.success();
    }
}
