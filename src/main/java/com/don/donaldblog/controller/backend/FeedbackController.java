package com.don.donaldblog.controller.backend;

import com.don.donaldblog.model.Feedback;
import com.don.donaldblog.service.FeedbackService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/backend/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @GetMapping(value = "/index")
    public String index(HttpServletRequest request, Model model)
    {
        String page = request.getParameter("page");
        Integer pageNum = page == null ? 1: Integer.valueOf(page);
        // 处理name和title
        String title = request.getParameter("title");
        String name = request.getParameter("name");
        String content = request.getParameter("content");
        String status = request.getParameter("status");
        Integer statusNum = status == null ? -1: Integer.valueOf(status);
        Map<String, Object> conditions = new HashMap<String, Object>();
        conditions.put("title", title);
        conditions.put("name", name);
        conditions.put("content", content);
        conditions.put("status", statusNum);

        PageHelper.startPage(pageNum, 20);
        List<Feedback> feedbacks = feedbackService.getSearch(conditions);
        PageInfo pageInfo = new PageInfo(feedbacks);

        model.addAttribute("list", feedbacks);
        model.addAttribute("pageInfo", pageInfo);
        return "backend/feedback/index";
    }

    @PostMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") Integer id)
    {
        int res = feedbackService.softDelete(id);
        return "redirect:/backend/feedback/index";
    }
}
