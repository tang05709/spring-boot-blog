package com.don.donaldblog.controller.backend;

import com.don.donaldblog.model.Config;
import com.don.donaldblog.service.ConfigService;
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
@RequestMapping("/backend/config")
public class ConfigController {
    @Autowired
    private ConfigService configService;
    private String templatePrefix = "backend/config/";
    private String redirectUrl = "redirect:/backend/config/index";

    @GetMapping("/index")
    public String index(Model model)
    {
        List<Config> configs = configService.getAll();
        model.addAttribute("list", configs);
        return this.templatePrefix + "index";
    }

    @GetMapping("/add")
    public String add(Model model)
    {
        Config pojo = (Config)model.getAttribute("pojo");
        if (pojo == null) {
            pojo = new Config();
        } else {
            List<FieldError> errors = (List<FieldError>)model.getAttribute("errors");
            model.addAttribute("errors", errors);
        }

        model.addAttribute("pojo", pojo);
        model.addAttribute("action", "create");
        return this.templatePrefix + "form";
    }

    @PostMapping("/create")
    public String create(@Valid Config record, BindingResult result, RedirectAttributesModelMap model)
    {
        if(result.hasErrors()) {
            model.addFlashAttribute("pojo", record);
            model.addFlashAttribute("errors", result.getFieldErrors());
            return "redirect:/backend/config/add";
        }
        int res = configService.create(record);
        return this.redirectUrl;
    }

    @GetMapping(value = "/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model)
    {
        Config pojo = (Config)model.getAttribute("pojo");
        if (pojo == null) {
            pojo = configService.getById(id);
        } else {
            List<FieldError> errors = (List<FieldError>)model.getAttribute("errors");
            model.addAttribute("errors", errors);
        }

        model.addAttribute("pojo", pojo);
        model.addAttribute("action", "update");
        return this.templatePrefix + "form";
    }

    @PostMapping(value = "/update")
    public String update(@Valid Config record, BindingResult result, RedirectAttributesModelMap model)
    {
        if(result.hasErrors()) {
            model.addFlashAttribute("pojo", record);
            model.addFlashAttribute("errors", result.getFieldErrors());
            return "redirect:/backend/config/edit/" + record.getId().toString();
        }
        int res = configService.update(record);
        return this.redirectUrl;
    }

    @PostMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") Integer id)
    {
        int res = configService.delete(id);
        return this.redirectUrl;
    }
}
