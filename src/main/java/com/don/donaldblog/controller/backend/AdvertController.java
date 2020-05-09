package com.don.donaldblog.controller.backend;

import com.don.donaldblog.model.Advert;
import com.don.donaldblog.model.AdvertCategory;
import com.don.donaldblog.service.AdvertCategoryService;
import com.don.donaldblog.service.AdvertService;
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
@RequestMapping(value = "/backend/advert")
public class AdvertController {
    @Autowired
    private AdvertService advertService;
    @Autowired
    private AdvertCategoryService advertCategoryService;
    private String templatePrefix = "backend/advert/";
    private String redirectUrl = "redirect:/backend/advert/index";

    @GetMapping(value = "/index")
    public String index(Model model)
    {
        List<Advert> adverts = advertService.getAll();

        model.addAttribute("list", adverts);
        return this.templatePrefix + "index";
    }

    @GetMapping(value = "/add")
    public String add(Model model)
    {
        Advert pojo = (Advert)model.getAttribute("pojo");
        if (pojo == null) {
            pojo = new Advert();
            pojo.setSort(0);
        } else {
            List<FieldError> errors = (List<FieldError>)model.getAttribute("errors");
            model.addAttribute("errors", errors);
        }
        List<AdvertCategory> categories = advertCategoryService.getAll();

        model.addAttribute("pojo", pojo);
        model.addAttribute("categories", categories);
        model.addAttribute("action", "create");
        return this.templatePrefix + "form";
    }

    @PostMapping(value = "/create")
    public String create(@Valid Advert record, BindingResult result, RedirectAttributesModelMap model)
    {
        if(result.hasErrors()) {
            model.addFlashAttribute("pojo", record);
            model.addFlashAttribute("errors", result.getFieldErrors());
            return "redirect:/backend/advert/add";
        }
        Long currentTime = System.currentTimeMillis();
        record.setCreated(currentTime);
        record.setUpdated(currentTime);
        record.setStatus(0);
        int res = advertService.create(record);
        return this.redirectUrl;
    }

    @GetMapping(value = "/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model)
    {
        Advert pojo = (Advert)model.getAttribute("pojo");
        if (pojo == null) {
            pojo = advertService.getById(id);
        } else {
            List<FieldError> errors = (List<FieldError>)model.getAttribute("errors");
            model.addAttribute("errors", errors);
        }

        List<AdvertCategory> categories = advertCategoryService.getAll();
        model.addAttribute("pojo", pojo);
        model.addAttribute("categories", categories);
        model.addAttribute("action", "update");
        return this.templatePrefix + "form";
    }

    @PostMapping(value = "/update")
    public String update(@Valid Advert advert, BindingResult result, RedirectAttributesModelMap model)
    {
        if(result.hasErrors()) {
            model.addFlashAttribute("pojo", advert);
            model.addFlashAttribute("errors", result.getFieldErrors());
            return "redirect:/backend/advert/edit/" + advert.getId().toString();
        }
        advert.setUpdated(System.currentTimeMillis());
        int res = advertService.update(advert);
        return this.redirectUrl;
    }

    @PostMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") Integer id)
    {
        int res = advertService.softDelete(id);
        return this.redirectUrl;
    }
}
