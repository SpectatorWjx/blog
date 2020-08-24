package com.wang.blog.service.link;

import com.wang.blog.base.page.BasePage;
import com.wang.blog.vo.LinkVo;
import com.wang.blog.repository.LinkRepository;
import com.wang.common.entity.other.LinkEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    private LinkRepository linkRepository;

    @Override
    public BasePage<LinkVo> getBlogLinkPage(Pageable pageable) {
        Page<LinkEntity> page = linkRepository.findAll(pageable);
        List<LinkEntity> linkList = page.getContent();
        List<LinkVo> linkVos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(linkList)) {
            linkList.forEach(l -> {
                LinkVo linkVo = new LinkVo();
                BeanUtils.copyProperties(l, linkVo);
                linkVos.add(linkVo);
            });
        }
        BasePage basePage = BasePage.getBasePage(page, linkVos);
        return basePage;
    }

    @Override
    public Long getTotalLinks() {
        return linkRepository.count();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveLink(LinkEntity link) {
        linkRepository.saveAndFlush(link);
    }

    @Override
    public LinkEntity selectById(String id) {
        Optional<LinkEntity> optional = linkRepository.findById(id);
        return optional.get();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateLink(LinkEntity tempLink) {
        linkRepository.saveAndFlush(tempLink);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(List<String> ids) {
        ids.forEach(id -> {
            linkRepository.deleteById(id);
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        linkRepository.deleteById(id);
    }

    @Override
    public Map<Integer, List<LinkVo>> getLinksForLinkPage() {
        //获取所有链接数据
        List<LinkEntity> links = linkRepository.findAll();
        if (!CollectionUtils.isEmpty(links)) {
            List<LinkVo> linkVos = new ArrayList<>();
            links.forEach(l -> {
                LinkVo linkVo = new LinkVo();
                BeanUtils.copyProperties(l, linkVo);
                linkVos.add(linkVo);
            });

            //根据type进行分组
            Map<Integer, List<LinkVo>> linksMap = linkVos.stream().collect(Collectors.groupingBy(LinkVo::getLinkType));
            return linksMap;
        }
        return null;
    }

    @Override
    public Long count() {
        return linkRepository.count();
    }
}
