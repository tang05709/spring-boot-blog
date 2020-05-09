package com.don.donaldblog.controller.backend;

import com.don.donaldblog.model.Category;
import com.don.donaldblog.service.CategoryService;
import com.don.donaldblog.utils.JsonUtils;
import com.don.donaldblog.utils.TreeView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/backend/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    private String templatePrefix = "backend/category/";
    private String redirectUrl = "redirect:/backend/category/index";

    @GetMapping(value = "/index")
    public String index(Model model)
    {
        List<Category> categories = categoryService.getAll();
        // 获取树形结构
        TreeView tree = new TreeView(categories);
        List<Category> list = tree.makeTree();

        model.addAttribute("list", list);
        return this.templatePrefix + "index";
    }

    @PostMapping(value = "/children/{id}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JsonUtils children(@PathVariable("id") Integer id)
    {
        Category category = categoryService.getById(id);
        List<Category> children = categoryService.getChildrenList(id);

        // 限制只能3级
        if (category.getLevel() < 3) {
            if (children != null) {
                return JsonUtils.success(children);
            }
        }
        return JsonUtils.fail("没有数据");
    }

    @GetMapping(value = "/add")
    public String add(HttpServletRequest request, Model model)
    {
        Category pojo = (Category)model.getAttribute("pojo");
        if (pojo == null) {
            pojo = new Category();
            String pid = request.getParameter("pid");
            Integer parentId = 0;
            Integer level = 1;
            if (pid != null) {
                parentId = Integer.valueOf(pid);
                level = categoryService.getLevel(parentId);
            }
            pojo.setSort(0);
            pojo.setParentId(parentId);
            pojo.setLevel(level);
            pojo.setIsSingle(0);
        } else {
            List<FieldError> errors = (List<FieldError>)model.getAttribute("errors");
            model.addAttribute("errors", errors);
        }

        model.addAttribute("pojo", pojo);
        model.addAttribute("action", "create");
        return this.templatePrefix + "form";
    }

    @PostMapping(value = "/create")
    public String create(@Valid Category category, BindingResult result, RedirectAttributesModelMap model)
    {
        if(result.hasErrors()) {
            model.addFlashAttribute("pojo", category);
            model.addFlashAttribute("errors", result.getFieldErrors());
            return "redirect:/backend/category/add";
        }
        Long current = System.currentTimeMillis();
        category.setCreated(current);
        category.setUpdated(current);
        int res = categoryService.create(category);
        return this.redirectUrl;
    }

    @GetMapping(value = "/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model)
    {
        Category pojo = (Category)model.getAttribute("pojo");
        if (pojo == null) {
            pojo = categoryService.getById(id);
        } else {
            List<FieldError> errors = (List<FieldError>)model.getAttribute("errors");
            model.addAttribute("errors", errors);
        }

        model.addAttribute("pojo", pojo);
        model.addAttribute("action", "update");
        return this.templatePrefix + "form";
    }

    @PostMapping(value = "/update")
    public String update(@Valid Category category, BindingResult result, RedirectAttributesModelMap model)
    {
        if(result.hasErrors()) {
            model.addFlashAttribute("pojo", category);
            model.addFlashAttribute("errors", result.getFieldErrors());
            return "redirect:/backend/category/edit/" + category.getId().toString();
        }
        category.setUpdated(System.currentTimeMillis());
        int res = categoryService.update(category);
        return this.redirectUrl;
    }

    @PostMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") Integer id)
    {
        int res = categoryService.delete(id);
        return this.redirectUrl;
    }
}
