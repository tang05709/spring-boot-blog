package com.don.donaldblog.controller.backend;

import com.don.donaldblog.choice.MoodLevel;
import com.don.donaldblog.model.Mood;
import com.don.donaldblog.service.MoodService;
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
@RequestMapping(value = "/backend/mood")
public class MoodController {
    @Autowired
    private MoodService moodService;
    private String templatePrefix = "backend/mood/";
    private String redirectUrl = "redirect:/backend/mood/index";

    @GetMapping(value = "/index")
    public String index(HttpServletRequest request, Model model)
    {
        String page = request.getParameter("page");
        Integer pageNum = page == null ? 1: Integer.valueOf(page);
        String content = request.getParameter("content");
        String level = request.getParameter("level");
        String status = request.getParameter("status");
        Integer statusNum = status == null ? -1 : Integer.valueOf(status);

        Map<String, Object> conditions = new HashMap<String, Object>();
        conditions.put("content", content);
        conditions.put("level", level);
        conditions.put("status", statusNum);

        PageHelper.startPage(pageNum, 20);
        List<Mood> moods = moodService.getSearch(conditions);
        PageInfo pageInfo = new PageInfo(moods);

        model.addAttribute("list", moods);
        model.addAttribute("pageInfo", pageInfo);
        return this.templatePrefix + "index";
    }

    @GetMapping(value = "/add")
    public String add(Model model)
    {
        Mood pojo = (Mood)model.getAttribute("pojo");
        if (pojo == null) {
            pojo = new Mood();
            pojo.setLevel("HAPPINESS");
        } else {
            List<FieldError> errors = (List<FieldError>)model.getAttribute("errors");
            model.addAttribute("errors", errors);
        }

        MoodLevel[] levels = moodService.getLevels();
        model.addAttribute("pojo", pojo);
        model.addAttribute("levels", levels);
        model.addAttribute("action", "create");
        return this.templatePrefix + "form";
    }

    @PostMapping(value = "/create")
    public String create(@Valid Mood mood, BindingResult result, RedirectAttributesModelMap model)
    {
        if(result.hasErrors()) {
            model.addFlashAttribute("pojo", mood);
            model.addFlashAttribute("errors", result.getFieldErrors());
            return "redirect:/backend/config/add";
        }
        mood.setStatus(0);
        mood.setCreated(System.currentTimeMillis());
        int res = moodService.create(mood);
        return this.redirectUrl;
    }

    @PostMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") Integer id)
    {
        int res = moodService.softDelete(id);
        return this.redirectUrl;
    }
}
