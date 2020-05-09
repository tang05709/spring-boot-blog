package com.don.donaldblog.service.impl;

import com.don.donaldblog.mapper.CategoryMapper;
import com.don.donaldblog.model.Category;
import com.don.donaldblog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public List<Category> getAll() {
        return categoryMapper.getAll();
    }

    @Override
    public List<Category> getNativeAll() {
        return categoryMapper.getNativeAll();
    }

    @Override
    public List<Category> getChildrenList(Integer id) {
        return categoryMapper.getChildrenList(id);
    }

    @Override
    public Category getById(Integer id) {
        return categoryMapper.getById(id);
    }

    @Override
    public int create(Category record) {
        redisTemplate.delete("categories");
        return categoryMapper.create(record);
    }

    @Override
    public int update(Category record) {
        redisTemplate.delete("categories");
        return categoryMapper.update(record);
    }

    @Override
    public int updateCount(Integer id, Integer count) {
        redisTemplate.delete("categories");
        return categoryMapper.updateCount(id, count);
    }

    @Override
    public int delete(Integer id) {
        redisTemplate.delete("categories");
        return categoryMapper.delete(id);
    }

    @Override
    public Integer getLevel(Integer parentId)
    {
        Integer level = 1;
        if (parentId > 0) {
            Category parent = categoryMapper.getById(parentId);
            level = parent.getLevel() + 1;
        }
        return level;
    }
}
