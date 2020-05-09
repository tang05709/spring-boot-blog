package com.don.donaldblog.controller.backend;

import com.don.donaldblog.model.Gallery;
import com.don.donaldblog.service.GalleryService;
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
@RequestMapping(value = "/backend/gallery")
public class GalleryController {
    @Autowired
    private GalleryService galleryService;
    private String templatePrefix = "backend/gallery/";

    @GetMapping(value = "/index/{articleId}")
    public String index(@PathVariable("articleId") Integer articleId, Model model)
    {
        if (articleId == null) {
            return "redirect:/backend/article/index";
        }
        List<Gallery> galleries = galleryService.getByArticle(articleId);
        model.addAttribute("list", galleries);
        model.addAttribute("articleId", articleId);
        return this.templatePrefix + "index";
    }

    @GetMapping(value = "/add/{articleId}")
    public String add(@PathVariable("articleId") Integer articleId, Model model)
    {
        if (articleId == null) {
            return "redirect:/backend/article/index";
        }
        Gallery pojo = (Gallery)model.getAttribute("pojo");;
        if (pojo == null) {
            pojo = new Gallery();
            pojo.setArticleId(articleId);
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
    public String create(@Valid Gallery gallery, BindingResult result, RedirectAttributesModelMap model)
    {
        if(result.hasErrors()) {
            model.addFlashAttribute("pojo", gallery);
            model.addFlashAttribute("errors", result.getFieldErrors());
            return "redirect:/backend/gallery/add/" + gallery.getArticleId().toString();
        }
        int res = galleryService.create(gallery);
        return "redirect:/backend/gallery/index/" + gallery.getArticleId().toString();
    }

    @GetMapping(value = "/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model)
    {
        Gallery pojo = (Gallery)model.getAttribute("pojo");;
        if (pojo == null) {
            pojo = pojo = galleryService.getById(id);
        } else {
            List<FieldError> errors = (List<FieldError>)model.getAttribute("errors");
            model.addAttribute("errors", errors);
        }
        model.addAttribute("pojo", pojo);
        model.addAttribute("action", "update");
        return this.templatePrefix + "form";
    }


    @PostMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") Integer id)
    {
        Gallery gojo = galleryService.getById(id);
        int res = galleryService.delete(id);
        return "redirect:/backend/gallery/index/" + gojo.getArticleId().toString();
    }
}
