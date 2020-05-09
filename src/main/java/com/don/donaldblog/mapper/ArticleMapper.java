package com.don.donaldblog.mapper;

import com.don.donaldblog.model.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ArticleMapper {
    public List<Article> getAll();
    public List<Article> getSearch(Map<String, Object> conditions);
    public List<Article> getHotList();
    public List<Article> getNewList();
    public Article getById(Integer id);
    public int getCategoryCount(Integer categoryId);
    public int create(Article record);
    public int update(Article record);
    public int updateClick(@Param("id") Integer id, @Param("click") Integer click);
    public int softDelete(@Param("id") Integer id, @Param("status") Integer status);
}
