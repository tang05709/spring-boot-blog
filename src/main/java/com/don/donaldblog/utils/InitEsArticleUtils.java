package com.don.donaldblog.utils;

import com.don.donaldblog.mapper.ArticleRespository;
import com.don.donaldblog.model.Article;
import com.don.donaldblog.service.ArticleService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InitEsArticleUtils implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(InitEsArticleUtils.class);
    Thread t;
    private ArticleService articleService;
    private ArticleRespository articleRespository;

    public InitEsArticleUtils(ArticleService articleService, ArticleRespository articleRespository)
    {
        this.articleService = articleService;
        this.articleRespository = articleRespository;
    }

    @Override
    public void run() {
        LOG.info("==========elasticsearch导入article开始  ==========");
        Map<String, Object> conditions = new HashMap<String, Object>();
        conditions.put("status", 0);
        try {
            for(int i = 1; i < 28; i++) {
                PageHelper.startPage(i, 20);
                List<Article> articles = articleService.getSearch(conditions);
                for(Article article : articles) {
                    LOG.info("导入 Article " + article.getId().toString());
                    articleRespository.save(article);
                }
                Thread.sleep(100);
            }

        } catch (Exception e) {
            LOG.info(e.getMessage());
        }
        LOG.info("==========elasticsearch导入article结束  ==========");
    }

    public void start()
    {
        if (t == null) {
            t = new Thread(this, "article_index");
            t.start();
            return ;
        }
        this.start();
    }
}
