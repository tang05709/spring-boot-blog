package com.don.donaldblog.service;

import com.don.donaldblog.model.Article;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    public List<Article> getAll();
    public List<Article> getSearch(Map<String, Object> conditions);
    public List<Article> getHotList();
    public List<Article> getNewList();
    public int getCategoryCount(Integer categoryId);
    public Article getById(Integer id);
    public int create(Article record);
    public int update(Article record);
    public int updateClick(Article article);
    public int softDelete(Integer id);
    public Page<Article> getEsPageList(Integer start, Integer size);
    public Page<Article> findEsCategoryPageList(Integer categoryId, Integer start, Integer size);
    public Page<Article> searchEsPageList(String keyword, Integer start, Integer size);
    public Article getEsById(Integer id);
}
