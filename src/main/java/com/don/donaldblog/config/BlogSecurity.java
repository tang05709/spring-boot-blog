package com.don.donaldblog.config;

import com.don.donaldblog.model.Admin;
import com.don.donaldblog.service.AdminService;
import com.don.donaldblog.utils.LoginFailHandler;
import com.don.donaldblog.utils.VerifyCaptchaFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class BlogSecurity extends WebSecurityConfigurerAdapter {
    @Autowired
    AdminService adminService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(adminService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/backend/images/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        VerifyCaptchaFilter verifyCaptchaFilter = new VerifyCaptchaFilter();
        LoginFailHandler loginFailHandler = new LoginFailHandler();
        verifyCaptchaFilter.setAuthenticationFailureHandler(loginFailHandler);

        http.addFilterBefore(verifyCaptchaFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage("/auth/login")// 自定义登录页面 默认/login
                .loginProcessingUrl("/auth/login-in") // 自定义登录post页面，默认/login，如果自定义了loginPage，则默认为loginPage的页面
                .defaultSuccessUrl("/backend/dashboard/index")// 自定义登录成功页面,
                .failureUrl("/auth/login?error=fail")
                .and()
                .logout() // 自定义跳出页面， 这里注意，如果么有禁用csrf， 需要用post跳出
                .logoutUrl("/auth/logout")
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        String username = authentication.getName();
                        if (username != null) {
                            Admin currentAdmin = adminService.getByName(authentication.getName());
                            Long currentTime = System.currentTimeMillis();
                            Integer loginCount = currentAdmin.getLoginCount() + 1;
                            String lastIp = httpServletRequest.getRemoteAddr();
                            adminService.updateLogin(currentAdmin.getId(), currentTime, lastIp, loginCount);
                        }
                        httpServletResponse.sendRedirect("/auth/login?error=logout");
                    }
                })
                .and()
                .authorizeRequests()
                .antMatchers("/backend/**")
                .hasRole("ADMIN")
                .and()
                .csrf()
                .ignoringAntMatchers(new String[]{"/backend/upload", "/backend/detail-upload"});
    }

}
