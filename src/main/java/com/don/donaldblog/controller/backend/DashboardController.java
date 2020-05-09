package com.don.donaldblog.controller.backend;

import com.don.donaldblog.model.Admin;
import com.don.donaldblog.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/backend/dashboard")
public class DashboardController {
    @Autowired
    private AdminService adminService;

    @GetMapping(value = "/index")
    public String index(Authentication authentication, Model model)
    {
        Admin admin = adminService.getByName(authentication.getName());
        model.addAttribute("admin", admin);
        return "backend/dashboard/index";
    }
}
