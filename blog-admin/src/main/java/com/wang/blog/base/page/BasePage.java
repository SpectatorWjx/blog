package com.wang.blog.base.page;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/***
 * @classname:
 * @description:
 * @author: wjx zhijiu
 * @date: 2019/10/24 10:29
 */
public class BasePage<T> extends PageInfo implements Serializable {

    private List<T> content;

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public BasePage() {
        super(0,0,0,0);
    }

    public BasePage(int number, int size, long total, int totalPages, List<T> content){
        super(number, size, total, totalPages);
        this.content = content;
    }


    public static<T> BasePage<T> getBasePage(Page page, List<T> list){
        return  new BasePage<>(page.getNumber(), page.getContent().size(), page.getTotalElements(), page.getTotalPages(), list);
    }

    public static<T> BasePage<T> getBasePage(Pageable pageable, List<T> list, Integer total){
        int totalPages = (total + pageable.getPageSize() - 1) / pageable.getPageSize();
        return  new BasePage<>(pageable.getPageNumber(), list.size(), total, totalPages, list);
    }

    public static <T> List<T> getPageList(Pageable pageable, List<T> list){
        if(CollectionUtils.isEmpty(list)){
            return Collections.emptyList();
        }
        int fromIndex = pageable.getPageNumber() * pageable.getPageSize();
        int toIndex = (pageable.getPageNumber()+1) * pageable.getPageSize();

        if (fromIndex >= list.size()) {
            return Collections.emptyList();
        }
        if(fromIndex<0){
            return Collections.emptyList();
        }
        if (toIndex >= list.size()) {
            toIndex = list.size();
        }
        return list.subList(fromIndex, toIndex);
    }
}
