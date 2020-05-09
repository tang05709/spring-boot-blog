package com.don.donaldblog.utils;

import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PagerUtils<T> {
    /**
     * 分页
     * @param result
     * @return
     */
    public Map<String, Object> getPageInfo(Page<T> result)
    {
        Map<String, Object> pageInfo = new HashMap<String, Object>();
        int currentPage = result.getNumber() + 1;
        int pages = result.getTotalPages();
        boolean hasPrevious = result.hasPrevious();
        int prevPage = -1;
        String prevStyle = "";
        boolean hasNext = result.hasNext();
        int nextPage = -1;
        String nextStyle = "";


        // 分页数, // 左右2条
        // 从第二页开始，因为第一个页码表示首页，默认显示 1 23456
        int min = 2;
        int prevNum = currentPage - 2;
        // 从倒数第二页开始，因为最后一个页码表示最后一页，默认显示 6789 10
        int max = pages - 1;
        int nextNum = currentPage + 2;

        if (hasPrevious) {
            prevPage = result.previousPageable().getPageNumber() + 1;
            if (prevNum > 1) {
                min = prevNum;
            }
        }
        if (min > 2) {
            prevStyle = "...";
        }

        if (hasNext) {
            nextPage = result.nextPageable().getPageNumber() + 1;
            if (nextNum < pages) {
                max = nextNum;
            }
        }
        if (max < (pages - 1)) {
            nextStyle = "...";
        }
        List<Integer> numberPages = new LinkedList<Integer>();
        for(int i = min; i <= max; i++) {
            numberPages.add(i);
        }

        pageInfo.put("currentPage", currentPage); // 当前页
        pageInfo.put("total", result.getTotalElements()); // 总条数
        pageInfo.put("pages", pages); // 总页数
        pageInfo.put("size", result.getSize()); // 每页多少条
        pageInfo.put("hasPrevious", hasPrevious); // 是否有上一页
        pageInfo.put("prevPage", prevPage); // 上一页
        pageInfo.put("prevStyle", prevStyle); // 样式
        pageInfo.put("hasNext", hasNext); // 是否有下一页
        pageInfo.put("nextPage", nextPage); // 下一页
        pageInfo.put("nextStyle", nextStyle); // 样式
        pageInfo.put("numberPages", numberPages); // 页码

        if (pages <= 1) {
            return null;
        }
        return pageInfo;
    }
}