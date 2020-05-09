package com.don.donaldblog.service.impl;

import com.don.donaldblog.mapper.AdminMapper;
import com.don.donaldblog.model.Admin;
import com.don.donaldblog.model.Role;
import com.don.donaldblog.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("adminService")
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin getByName(String name) {
        return adminMapper.getByName(name);
    }

    @Override
    public int updateLogin(Integer id, Long lastLogin, String lastIp, Integer loginCount) {
        return adminMapper.updateLogin(id, lastLogin, lastIp, loginCount);
    }

    @Override
    public int updatePassword(Integer id, String password) {
        return adminMapper.updatePassword(id, password);
    }

    @Override
    public List<Role> getUserRolesById(Integer id) {
        return adminMapper.getUserRolesById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (null == username) {
            throw new UsernameNotFoundException("请输入用户名");
        }
        Admin admin = adminMapper.getByName(username);
        if (admin  == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<Role> roles = adminMapper.getUserRolesById(admin.getId());
        admin.setRoles(roles);
        return admin;
    }
}
