package com.don.donaldblog.controller.backend;

import com.don.donaldblog.mapper.ArticleRespository;
import com.don.donaldblog.mapper.MoodRespository;
import com.don.donaldblog.service.ArticleService;
import com.don.donaldblog.service.MoodService;
import com.don.donaldblog.utils.InitEsArticleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InitesController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleRespository articleRespository;
    @Autowired
    private MoodService moodService;
    private MoodRespository moodRespository;

    @GetMapping("/backend/inites/index")
    public String index()
    {
        InitEsArticleUtils esArticle = new InitEsArticleUtils(articleService, articleRespository);
        esArticle.start();
        //InitEsMoodUtils esMood = new InitEsMoodUtils(moodService, moodRespository);
        //esMood.start();
        return "redirect:/backend/dashboard/index";
    }
}
