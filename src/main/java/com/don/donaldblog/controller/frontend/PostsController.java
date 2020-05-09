package com.don.donaldblog.controller.frontend;

import com.don.donaldblog.model.Article;
import com.don.donaldblog.model.Category;
import com.don.donaldblog.service.ArticleService;
import com.don.donaldblog.service.CategoryService;
import com.don.donaldblog.utils.PagerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class PostsController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    RedisTemplate redisTemplate;
    //@Autowired(required = false)
    //private ElasticsearchTemplate  elasticsearchTemplate;

    /**
     * 首页
     * @param page
     * @param model
     * @return
     */
    @GetMapping(value = {"/page/{page}", "/"})
    public String index(@PathVariable(value = "page", required = false) Integer page, Model model)
    {
        Integer pageNum = page == null ? 0: page - 1;

        Page<Article> result = articleService.getEsPageList(pageNum, 20);
        List<Article> articles = result.toList();
        PagerUtils<Article> pager = new PagerUtils<Article>();
        Map<String, Object> pageInfo = pager.getPageInfo(result);

        model.addAttribute("articles", articles);
        model.addAttribute("category", null);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("url", "");
        model.addAttribute("seoTitle", "我的博客");

        return "frontend/list";
    }

    /**
     * 分类页
     * @param cid
     * @param page
     * @param model
     * @return
     */
    @GetMapping(value = {"/category/{cid}/page/{page}", "/category/{cid}"})
    public String category(@PathVariable("cid") Integer cid, @PathVariable(value = "page", required = false) Integer page, Model model)
    {
        Integer pageNum = page == null ? 0: page - 1;
        Category category = getCategory(cid);
        Page<Article> result = articleService.findEsCategoryPageList(cid, pageNum, 20);
        List<Article> articles = result.toList();
        PagerUtils<Article> pager = new PagerUtils<Article>();
        Map<String, Object> pageInfo = pager.getPageInfo(result);

        model.addAttribute("articles", articles);
        model.addAttribute("category", category);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("url", "/category/" + cid);
        model.addAttribute("seoTitle", category.getSeoTitle());
        model.addAttribute("seoKeywords", category.getSeoKeywords());
        model.addAttribute("seoDescription", category.getSeoDescription());

        return "frontend/list";
    }

    /**
     * 搜索页
     * @param page
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = {"/search/page/{page}", "/search"})
    public String search(@PathVariable(value = "page", required = false) Integer page, HttpServletRequest request, Model model)
    {
        Integer pageNum = page == null ? 0: page - 1;
        String keyword = request.getParameter("kw");
        if (keyword == null || keyword.isEmpty()) {
            return "redirect:/";
        }
        keyword =  keyword.trim().replace(" ", "+");

        Page<Article> result = articleService.searchEsPageList(keyword, pageNum, 20);
        List<Article> articles = result.toList();
        PagerUtils<Article> pager = new PagerUtils<Article>();
        Map<String, Object> pageInfo = pager.getPageInfo(result);

        model.addAttribute("articles", articles);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("url", "/search");
        model.addAttribute("kw", keyword);
        model.addAttribute("seoTitle", keyword);
        model.addAttribute("seoKeywords", keyword);
        model.addAttribute("seoDescription", keyword);

        return "frontend/search";
    }

    /**
     * 详情页
     * @param id
     * @param model
     * @return
     */
    @GetMapping(value = "/article/{id}")
    public String article(@PathVariable("id") Integer id, Model model)
    {

        Article article = (Article)articleService.getEsById(id);
        Category category = getCategory(article.getCategoryId());
        articleService.updateClick(article);
        model.addAttribute("article", article);
        model.addAttribute("category", category);
        model.addAttribute("seoTitle", article.getSeoTitle());
        model.addAttribute("seoKeywords", article.getSeoKeywords());
        model.addAttribute("seoDescription", article.getSeoDescription());

        return "frontend/detail";
    }

    /**
     * 归档页
     * @return
     */
    @GetMapping(value = "/archive")
    public String archive()
    {
        return "frontend/archive";
    }

    /**
     * 获取栏目
     * @param id
     * @return
     */
    private Category getCategory(Integer id)
    {
        List<Category> categories = (List<Category>)redisTemplate.opsForValue().get("categories");
        if (categories != null) {
            for(Category category : categories) {
                if (category.getId().equals(id)) {
                    return category;
                }
            }
        }
        Category category = categoryService.getById(id);
        return category;
    }
}
