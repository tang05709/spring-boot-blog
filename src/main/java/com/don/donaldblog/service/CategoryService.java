package com.don.donaldblog.service;

import com.don.donaldblog.model.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryService {
    public List<Category> getAll();
    public List<Category> getNativeAll();
    public List<Category> getChildrenList(Integer id);
    public Category getById(Integer id);
    public int create(Category record);
    public int update(Category record);
    public int updateCount(Integer id, Integer count);
    public int delete(@Param("id") Integer id);
    public Integer getLevel(Integer parentId);
}
