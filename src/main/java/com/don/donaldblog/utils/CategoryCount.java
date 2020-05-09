package com.don.donaldblog.utils;

import com.don.donaldblog.model.Category;
import com.don.donaldblog.service.ArticleService;
import com.don.donaldblog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
public class CategoryCount {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleService articleService;

    /**
     * 统计分类文章数量
     * 每天23:15触发
     */
    @Scheduled(cron = "0 55 12 ? * *")
    public void statisticalCategoryCount()
    {
        List<Category> categories = categoryService.getAll();
        for(Category cate : categories) {
            int count = articleService.getCategoryCount(cate.getId());
            categoryService.updateCount(cate.getId(), count);
        }
    }
}
