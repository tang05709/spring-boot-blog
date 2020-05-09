package com.don.donaldblog.controller.backend;

import com.don.donaldblog.model.Admin;
import com.don.donaldblog.service.AdminService;
import com.don.donaldblog.utils.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.OutputStream;

@Controller
@RequestMapping(value = "/auth")
public class AuthController {
    @Autowired
    private AdminService adminService;

    @GetMapping(value = "/login")
    public String login()
    {
        return "backend/login";
    }

    @GetMapping(value = "/verify-code")
    public String verityCode(HttpServletResponse response, HttpSession session)
    {
        Captcha captcha = new Captcha();
        BufferedImage image = captcha.getImageCode(68, 35);
        String code = captcha.getCode();
        session.setAttribute("verifyCode", code);
        try {
            OutputStream out = response.getOutputStream();
            ImageIO.write(image, "JPEG", out);
        }  catch (Exception e) {
            return "redirect:/backend/login?error=fail";
        }
        return null;
    }
}
