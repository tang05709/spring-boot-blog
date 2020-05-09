package com.don.donaldblog.controller.frontend;

import com.don.donaldblog.model.Mood;
import com.don.donaldblog.service.MoodService;
import com.don.donaldblog.utils.PagerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Controller
public class EssaysController {
    @Autowired
    private MoodService moodService;

    @GetMapping(value = {"/essays/page/{page}", "/essays"})
    public String index(@PathVariable(value = "page", required = false) Integer page, Model model)
    {
        Integer pageNum = page == null ? 0: page - 1;
        Page<Mood> result = moodService.getEsPageList(pageNum, 20);
        List<Mood> moods = result.toList();
        PagerUtils<Mood> pager = new PagerUtils<Mood>();
        Map<String, Object> pageInfo = pager.getPageInfo(result);

        model.addAttribute("articles", moods);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("url", "/essays");
        model.addAttribute("seoTitle", "随笔");
        return "frontend/essays";
    }
}
