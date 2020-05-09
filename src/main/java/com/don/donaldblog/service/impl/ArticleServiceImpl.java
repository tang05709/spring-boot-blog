package com.don.donaldblog.service.impl;

import com.don.donaldblog.mapper.ArticleMapper;
import com.don.donaldblog.mapper.ArticleRespository;
import com.don.donaldblog.model.Article;
import com.don.donaldblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service("articleService")
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleRespository articleRespository;

    @Override
    public List<Article> getAll() {
        return articleMapper.getAll();
    }

    @Override
    public List<Article> getSearch(Map<String, Object> conditions) {
        return articleMapper.getSearch(conditions);
    }

    @Override
    public List<Article> getNewList() {
        List<Article> articles = articleRespository.findTop10ByOrderByIdDesc(PageRequest.of(0, 10, Sort.Direction.DESC, "id"));
        if (articles == null) {
            articles = articleMapper.getNewList();
        }
        return articles;
    }

    @Override
    public List<Article> getHotList() {
        List<Article> articles = articleRespository.findTop10ByOrderByClickDesc(PageRequest.of(0, 10, Sort.Direction.DESC, "click"));
        if (articles == null) {
            articles = articleMapper.getHotList();
        }
        return articles;
    }

    @Override
    public int getCategoryCount(Integer categoryId)
    {
        return articleMapper.getCategoryCount(categoryId);
    }

    @Override
    public Article getById(Integer id) {
        return articleMapper.getById(id);
    }

    @Override
    public int create(Article record) {
        // 同步elasticsearch, 这里注意mapper要加useGeneratedKeys="true" keyProperty="id" keyColumn="id"
        int res = articleMapper.create(record);
        articleRespository.save(record);
        return res;
    }

    @Override
    public int update(Article record) {
        articleRespository.save(record);
        return articleMapper.update(record);
    }

    @Override
    public int updateClick(Article record) {
        Integer click = record.getClick();
        if (click == null) {
            click = 1;
        } else {
            click += 1;
        }
        record.setClick(click);
        articleRespository.save(record);
        return articleMapper.updateClick(record.getId(), click);
    }

    @Override
    public int softDelete(Integer id) {
        Article record = articleMapper.getById(id);
        Integer status = record.getStatus() == 9 ? 0 : 9;
        if (status == 9) {
            articleRespository.delete(record);
        } else {
            record.setStatus(status);
            articleRespository.save(record);
        }
        return articleMapper.softDelete(id, status);
    }

    public Page<Article> getEsPageList(Integer start, Integer size)
    {
        return articleRespository.findAll(PageRequest.of(start, size, Sort.Direction.DESC, "id"));
    }

    public Page<Article> findEsCategoryPageList(Integer categoryId, Integer start, Integer size)
    {
        return articleRespository.findByCategoryId(categoryId, PageRequest.of(start, size, Sort.Direction.DESC, "id"));
    }

    public Page<Article> searchEsPageList(String keyword, Integer start, Integer size)
    {
        return articleRespository.findByTitleContaining(keyword, PageRequest.of(start, size, Sort.Direction.DESC, "id"));
    }

    public Article getEsById(Integer id)
    {
        Optional<Article> result = articleRespository.findById(id);
        if(result.isPresent()) {
            Article article = result.get();
            if (article.getCategory() == null) {
                article = articleMapper.getById(id);
                articleRespository.save(article);
            }
            return article;
        }
        return null;
    }
}
