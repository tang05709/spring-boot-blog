package com.don.donaldblog.service;

import com.don.donaldblog.model.Admin;
import com.don.donaldblog.model.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface AdminService extends UserDetailsService {
    public Admin getByName(String name);
    public int updateLogin(@Param("id") Integer id, @Param("lastLogin") Long lastLogin, @Param("lastIp") String lastIp, @Param("loginCount") Integer loginCount);
    public int updatePassword(@Param("id") Integer id, @Param("password") String password);
    public List<Role> getUserRolesById(Integer id);
}
