package com.don.donaldblog.mapper;

import com.don.donaldblog.model.Admin;
import com.don.donaldblog.model.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminMapper {
    public Admin getByName(String name);
    public int updateLogin(@Param("id") Integer id, @Param("lastLogin") Long lastLogin, @Param("lastIp") String lastIp, @Param("loginCount") Integer loginCount);
    public int updatePassword(@Param("id") Integer id, @Param("password") String password);
    public List<Role> getUserRolesById(Integer id);
}
