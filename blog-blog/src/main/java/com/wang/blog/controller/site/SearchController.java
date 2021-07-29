package com.wang.blog.controller.site;

import com.wang.blog.controller.BaseController;
import com.wang.blog.service.PostSearchService;
import com.wang.blog.vo.PostVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 文章搜索
 * @author wjx
 * @date 2019/08/13
 */
@Controller
public class SearchController extends BaseController {
	@Autowired
	private PostSearchService postSearchService;

	@RequestMapping("/search")
	public String search(String kw, ModelMap model) {
		Pageable pageable = wrapPageable();
		try {
			if (StringUtils.isNotEmpty(kw)) {
				Page<PostVO> page = postSearchService.search(pageable, kw);
				model.put("results", page);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.put("kw", kw);
		return view(Views.SEARCH);
	}
	
}
