package com.wang.blog.service.link;

import com.wang.blog.vo.LinkVo;
import com.wang.blog.repository.LinkRepository;
import com.wang.common.entity.other.LinkEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    private LinkRepository linkRepository;

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
}
