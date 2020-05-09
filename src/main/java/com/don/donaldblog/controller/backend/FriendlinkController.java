package com.don.donaldblog.controller.backend;

import com.don.donaldblog.model.Friendlink;
import com.don.donaldblog.service.FriendlinkService;
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
@RequestMapping(value = "/backend/friendlink")
public class FriendlinkController {
    @Autowired
    private FriendlinkService friendlinkService;
    private String templatePrefix = "backend/friendlink/";
    private String redirectUrl = "redirect:/backend/friendlink/index";

    @GetMapping(value = "/index")
    public String index(Model model)
    {
        List<Friendlink> friendlinks = friendlinkService.getAll();
        model.addAttribute("list", friendlinks);
        return this.templatePrefix + "index";
    }

    @GetMapping(value = "/add")
    public String add(Model model)
    {
        Friendlink pojo = (Friendlink)model.getAttribute("pojo");
        if (pojo == null) {
            pojo = new Friendlink();
            pojo.setSort(0);
        } else {
            List<FieldError> errors = (List<FieldError>)model.getAttribute("errors");
            model.addAttribute("errors", errors);
        }

        model.addAttribute("pojo", pojo);
        model.addAttribute("action", "create");
        return this.templatePrefix + "form";
    }

    @PostMapping(value = "/create")
    public String create(@Valid Friendlink record, BindingResult result, RedirectAttributesModelMap model)
    {
        if(result.hasErrors()) {
            model.addFlashAttribute("pojo", record);
            model.addFlashAttribute("errors", result.getFieldErrors());
            return "redirect:/backend/friendlink/add";
        }
        Long current = System.currentTimeMillis();
        record.setCreated(current);
        record.setUpdated(current);
        record.setStatus(0);
        int res = friendlinkService.create(record);
        return this.redirectUrl;
    }

    @GetMapping(value = "/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model)
    {
        Friendlink pojo = (Friendlink)model.getAttribute("pojo");
        if (pojo == null) {
            pojo = friendlinkService.getById(id);
        } else {
            List<FieldError> errors = (List<FieldError>)model.getAttribute("errors");
            model.addAttribute("errors", errors);
        }
        model.addAttribute("pojo", pojo);
        model.addAttribute("action", "update");
        return this.templatePrefix + "form";
    }

    @PostMapping(value = "/update")
    public String update(@Valid Friendlink record, BindingResult result, RedirectAttributesModelMap model)
    {
        if(result.hasErrors()) {
            model.addFlashAttribute("pojo", record);
            model.addFlashAttribute("errors", result.getFieldErrors());
            return "redirect:/backend/friendlink/edit/" + record.getId().toString();
        }
        record.setUpdated(System.currentTimeMillis());
        int res = friendlinkService.update(record);
        return this.redirectUrl;
    }

    @PostMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") Integer id)
    {
        int res = friendlinkService.softDelete(id);
        return this.redirectUrl;
    }
}
