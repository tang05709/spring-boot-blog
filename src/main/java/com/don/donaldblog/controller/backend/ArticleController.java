package com.don.donaldblog.controller.backend;

import com.don.donaldblog.model.Article;
import com.don.donaldblog.model.Category;
import com.don.donaldblog.service.ArticleService;
import com.don.donaldblog.service.CategoryService;
import com.don.donaldblog.utils.TreeView;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/backend/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryService categoryService;
    private String templatePrefix = "backend/article/";
    private String redirectUrl = "redirect:/backend/article/index";

    @GetMapping(value = "/index")
    public String index(HttpServletRequest request, Model model)
    {
        String page = request.getParameter("page");
        Integer pageNum = page == null ? 1: Integer.valueOf(page);
        String title = request.getParameter("title");
        String category = request.getParameter("categoryId");
        String status = request.getParameter("status");
        Integer statusNum = (status == null || status == "") ? -1 : Integer.valueOf(status);

        Integer categoryId = (category == null || category.length() == 0) ? 0: Integer.valueOf(category);
        Map<String, Object> conditions = new HashMap<String, Object>();
        conditions.put("title", title);
        conditions.put("categoryId", categoryId);
        conditions.put("status", statusNum);

        PageHelper.startPage(pageNum, 20);
        List<Article> articles = articleService.getSearch(conditions);
        PageInfo pageInfo = new PageInfo(articles);

        List<Category> categories = categoryService.getChildrenList(0);

        model.addAttribute("list", articles);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("categories", categories);
        return this.templatePrefix + "index";
    }

    @GetMapping(value = "/add")
    public String add(Model model)
    {
        Article pojo = (Article)model.getAttribute("pojo");
        if (pojo == null) {
            pojo = new Article();
            pojo.setSort(0);
        } else {
            List<FieldError> errors = (List<FieldError>)model.getAttribute("errors");
            model.addAttribute("errors", errors);
        }

        List<Category> categories = categoryService.getChildrenList(0);

        model.addAttribute("pojo", pojo);
        model.addAttribute("categories", categories);
        model.addAttribute("action", "create");
        return this.templatePrefix + "form";
    }

    @PostMapping(value = "/create")
    public String create(@Valid Article article, BindingResult result, RedirectAttributesModelMap model)
    {
        if(result.hasErrors()) {
            model.addFlashAttribute("pojo", article);
            model.addFlashAttribute("errors", result.getFieldErrors());
            return "redirect:/backend/article/add";
        }
        Long currentTime = System.currentTimeMillis();
        article.setCreated(currentTime);
        article.setUpdated(currentTime);
        article.setStatus(0);
        article.setClick(0);
        int res = articleService.create(article);
        return this.redirectUrl;
    }

    @GetMapping(value = "/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model)
    {
        Article pojo = (Article)model.getAttribute("pojo");
        if (pojo == null) {
            pojo = articleService.getById(id);
        } else {
            List<FieldError> errors = (List<FieldError>)model.getAttribute("errors");
            model.addAttribute("errors", errors);
        }

        List<Category> categories = categoryService.getChildrenList(0);

        model.addAttribute("pojo", pojo);
        model.addAttribute("categories", categories);
        model.addAttribute("action", "update");
        return this.templatePrefix + "form";
    }

    @PostMapping(value = "/update")
    public String update(@Valid Article article, BindingResult result, RedirectAttributesModelMap model)
    {
        if(result.hasErrors()) {
            model.addFlashAttribute("pojo", article);
            model.addFlashAttribute("errors", result.getFieldErrors());
            return "redirect:/backend/article/edit/" + article.getId().toString();
        }
        article.setUpdated(System.currentTimeMillis());
        int res = articleService.update(article);
        return this.redirectUrl;
    }

    @PostMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") Integer id)
    {
        int res = articleService.softDelete(id);
        return this.redirectUrl;
    }
}
