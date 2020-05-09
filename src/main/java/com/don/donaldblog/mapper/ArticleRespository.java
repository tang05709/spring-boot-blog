package com.don.donaldblog.mapper;

import com.don.donaldblog.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ArticleRespository extends ElasticsearchRepository<Article, Integer> {
    /**
     * 按标题和简介模糊查询
     * @param title
     * @param pageable
     * @return
     */
    public Page<Article> findByTitleContaining(String title, Pageable pageable);

    /**
     * 按分类获取
     * @param categoryId
     * @param pageable
     * @return
     */
    public Page<Article> findByCategoryId(Integer categoryId, Pageable pageable);

    public List<Article> findTop10ByOrderByIdDesc(Pageable pageable);

    public List<Article> findTop10ByOrderByClickDesc(Pageable pageable);
}
