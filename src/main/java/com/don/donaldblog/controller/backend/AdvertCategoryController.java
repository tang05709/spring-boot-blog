package com.don.donaldblog.controller.backend;

import com.don.donaldblog.model.AdvertCategory;
import com.don.donaldblog.service.AdvertCategoryService;
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

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/backend/advert-category")
public class AdvertCategoryController {
    @Autowired
    private AdvertCategoryService advertCategoryService;
    private String templatePrefix = "backend/advert-category/";
    private String redirectUrl = "redirect:/backend/advert-category/index";

    @GetMapping(value = "/index")
    public String index(Model model)
    {
        List<AdvertCategory> categories = advertCategoryService.getAll();
        model.addAttribute("list", categories);
        return this.templatePrefix + "index";
    }

    @GetMapping(value = "/add")
    public String add(Model model)
    {
        AdvertCategory pojo = (AdvertCategory)model.getAttribute("pojo");
        if (pojo == null) {
            pojo = new AdvertCategory();
        } else {
            List<FieldError> errors = (List<FieldError>)model.getAttribute("errors");
            model.addAttribute("errors", errors);
        }
        model.addAttribute("pojo", pojo);
        model.addAttribute("action", "create");
        return this.templatePrefix + "form";
    }

    @PostMapping(value = "/create")
    public String create(@Valid AdvertCategory record, BindingResult result, RedirectAttributesModelMap model)
    {
        if(result.hasErrors()) {
            model.addFlashAttribute("pojo", record);
            model.addFlashAttribute("errors", result.getFieldErrors());
            return "redirect:/backend/advert-category/add";
        }
        Long current = System.currentTimeMillis();
        record.setCreated(current);
        record.setUpdated(current);
        record.setStatus(0);
        int res = advertCategoryService.create(record);
        return this.redirectUrl;
    }

    @GetMapping(value = "/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model)
    {
        AdvertCategory pojo = (AdvertCategory)model.getAttribute("pojo");
        if (pojo == null) {
            pojo = advertCategoryService.getById(id);
        } else {
            List<FieldError> errors = (List<FieldError>)model.getAttribute("errors");
            model.addAttribute("errors", errors);
        }
        model.addAttribute("pojo", pojo);
        model.addAttribute("action", "update");
        return this.templatePrefix + "form";
    }

    @PostMapping(value = "/update")
    public String update(@Valid AdvertCategory record, BindingResult result, RedirectAttributesModelMap model)
    {
        if(result.hasErrors()) {
            model.addFlashAttribute("pojo", record);
            model.addFlashAttribute("errors", result.getFieldErrors());
            return "redirect:/backend/advert-category/edit/" + record.getId().toString();
        }
        record.setUpdated(System.currentTimeMillis());
        int res = advertCategoryService.update(record);
        return this.redirectUrl;
    }

    @PostMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") Integer id)
    {
        int res = advertCategoryService.softDelete(id);
        return this.redirectUrl;
    }
}
