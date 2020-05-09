package com.don.donaldblog.controller.backend;

import com.don.donaldblog.model.Role;
import com.don.donaldblog.service.RoleService;
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
@RequestMapping("/backend/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    private String templatePrefix = "backend/role/";
    private String redirectUrl = "redirect:/backend/role/index";

    @GetMapping(value = "/index")
    public String index(Model model)
    {
        List<Role> menus = roleService.getAll();
        model.addAttribute("list", menus);
        return this.templatePrefix + "index";
    }

    @GetMapping(value = "/add")
    public String add(Model model)
    {
        Role pojo = (Role)model.getAttribute("pojo");
        if (pojo == null) {
            pojo = new Role();
        } else {
            List<FieldError> errors = (List<FieldError>)model.getAttribute("errors");
            model.addAttribute("errors", errors);
        }
        model.addAttribute("pojo", pojo);
        model.addAttribute("action", "create");
        return this.templatePrefix + "form";
    }

    @PostMapping(value = "/create")
    public String create(@Valid Role record, BindingResult result, RedirectAttributesModelMap model)
    {
        if(result.hasErrors()) {
            model.addFlashAttribute("pojo", record);
            model.addFlashAttribute("errors", result.getFieldErrors());
            return "redirect:/backend/menu/add";
        }
        int res = roleService.create(record);
        return this.redirectUrl;
    }

    @GetMapping(value = "/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model)
    {
        Role pojo = (Role)model.getAttribute("pojo");
        if (pojo == null) {
            pojo = roleService.getById(id);
        } else {
            List<FieldError> errors = (List<FieldError>)model.getAttribute("errors");
            model.addAttribute("errors", errors);
        }
        model.addAttribute("pojo", pojo);
        model.addAttribute("action", "update");
        return this.templatePrefix + "form";
    }

    @PostMapping(value = "/update")
    public String update(@Valid Role record, BindingResult result, RedirectAttributesModelMap model)
    {
        if(result.hasErrors()) {
            model.addFlashAttribute("pojo", record);
            model.addFlashAttribute("errors", result.getFieldErrors());
            return "redirect:/backend/menu/edit/" + record.getId().toString();
        }
        int res = roleService.update(record);
        return this.redirectUrl;
    }

    @PostMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") Integer id)
    {
        int res = roleService.delete(id);
        return this.redirectUrl;
    }
}
