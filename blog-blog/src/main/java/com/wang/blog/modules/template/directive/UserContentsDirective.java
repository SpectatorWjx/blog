/**
 *
 */package com.wang.blog.modules.template.directive;

import com.wang.blog.vo.PostVO;
import com.wang.blog.service.PostService;
import com.wang.blog.modules.template.DirectiveHandler;
import com.wang.blog.modules.template.TemplateDirective;
import com.wang.blog.vo.UserMonthPostVO;
import com.wang.blog.vo.UserYearPostVO;
import com.wang.common.common.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 根据作者取文章列表
 *
 * @author wjx
 *
 */
@Component
public class UserContentsDirective extends TemplateDirective {
    @Autowired
	private PostService postService;

	@Override
	public String getName() {
		return "user_contents";
	}

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        String userId = handler.getString("userId", "");

        List<PostVO> postVOList = postService.listByAuthorId(userId);
        List<UserYearPostVO> result = new ArrayList<>();

        Map<String, List<PostVO>> yearMap = new TreeMap<>(new MapKeyComparator());
        yearMap.putAll(postVOList.stream().collect(Collectors.groupingBy(p -> DateUtil.formatYearTime(p.getCreateTime()),Collectors.toList())));
        yearMap.forEach((year,monthPostVOs)->{
                UserYearPostVO yearPostVO = new UserYearPostVO();
                yearPostVO.setYear(year);
                List<UserMonthPostVO> monthPostVOList = new ArrayList<>();
                Map<String, List<PostVO>> monthMap = new TreeMap<>(new MapKeyComparator());
                monthMap.putAll(monthPostVOs.stream().collect(Collectors.groupingBy(p -> DateUtil.getMonth(p.getCreateTime()),Collectors.toList())));
                monthMap.forEach((month,postVOs)->{
                            UserMonthPostVO monthPostVO = new UserMonthPostVO();
                            monthPostVO.setMonth(month);
                            monthPostVO.setPostVOList(postVOs);
                            monthPostVOList.add(monthPostVO);
                        });
                yearPostVO.setMonthPostVoList(monthPostVOList);
                result.add(yearPostVO);
            });
        handler.put(RESULTS, result).render();
    }

    /**
     * 自定义比较器
     */
    class MapKeyComparator implements Comparator<String> {

        @Override
        public int compare(String str1, String str2) {
            return str2.compareTo(str1);
        }
    }
}
