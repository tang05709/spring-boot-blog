package com.don.donaldblog.mapper;

import com.don.donaldblog.model.Role;

import java.util.List;

public interface RoleMapper {
    public List<Role> getAll();
    public Role getById(Integer id);
    public int create(Role record);
    public int update(Role record);
    public int delete(Integer id);
}
