package com.don.donaldblog.controller.backend;

import com.don.donaldblog.model.Menu;
import com.don.donaldblog.service.MenuService;
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
@RequestMapping("/backend/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;
    private String templatePrefix = "backend/menu/";
    private String redirectUrl = "redirect:/backend/menu/index";

    @GetMapping(value = "/index")
    public String index(Model model)
    {
        List<Menu> menus = menuService.getAll();
        model.addAttribute("list", menus);
        return this.templatePrefix + "index";
    }

    @GetMapping(value = "/add")
    public String add(Model model)
    {
        Menu pojo = (Menu)model.getAttribute("pojo");
        if (pojo == null) {
            pojo = new Menu();
        } else {
            List<FieldError> errors = (List<FieldError>)model.getAttribute("errors");
            model.addAttribute("errors", errors);
        }
        model.addAttribute("pojo", pojo);
        model.addAttribute("action", "create");
        return this.templatePrefix + "form";
    }

    @PostMapping(value = "/create")
    public String create(@Valid Menu record, BindingResult result, RedirectAttributesModelMap model)
    {
        if(result.hasErrors()) {
            model.addFlashAttribute("pojo", record);
            model.addFlashAttribute("errors", result.getFieldErrors());
            return "redirect:/backend/menu/add";
        }
        int res = menuService.create(record);
        return this.redirectUrl;
    }

    @GetMapping(value = "/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model)
    {
        Menu pojo = (Menu)model.getAttribute("pojo");
        if (pojo == null) {
            pojo = menuService.getById(id);
        } else {
            List<FieldError> errors = (List<FieldError>)model.getAttribute("errors");
            model.addAttribute("errors", errors);
        }
        model.addAttribute("pojo", pojo);
        model.addAttribute("action", "update");
        return this.templatePrefix + "form";
    }

    @PostMapping(value = "/update")
    public String update(@Valid Menu record, BindingResult result, RedirectAttributesModelMap model)
    {
        if(result.hasErrors()) {
            model.addFlashAttribute("pojo", record);
            model.addFlashAttribute("errors", result.getFieldErrors());
            return "redirect:/backend/menu/edit/" + record.getId().toString();
        }
        int res = menuService.update(record);
        return this.redirectUrl;
    }

    @PostMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") Integer id)
    {
        int res = menuService.delete(id);
        return this.redirectUrl;
    }
}
