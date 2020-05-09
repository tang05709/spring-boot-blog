package com.don.donaldblog.service.impl;

import com.don.donaldblog.mapper.RoleMapper;
import com.don.donaldblog.model.Role;
import com.don.donaldblog.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public List<Role> getAll() {
        return roleMapper.getAll();
    }

    @Override
    public Role getById(Integer id) {
        return roleMapper.getById(id);
    }

    @Override
    public int create(Role record) {
        return roleMapper.create(record);
    }

    @Override
    public int update(Role record) {
        return roleMapper.update(record);
    }

    @Override
    public int delete(Integer id) {
        return roleMapper.delete(id);
    }
}
