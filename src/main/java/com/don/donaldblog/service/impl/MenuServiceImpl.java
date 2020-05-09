package com.don.donaldblog.service.impl;

import com.don.donaldblog.mapper.MenuMapper;
import com.don.donaldblog.model.Menu;
import com.don.donaldblog.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("menuService")
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public List<Menu> getAll() {
        return menuMapper.getAll();
    }

    @Override
    public Menu getById(Integer id) {
        return menuMapper.getById(id);
    }

    @Override
    public int create(Menu record) {
        redisTemplate.delete("menus");
        return menuMapper.create(record);
    }

    @Override
    public int update(Menu record) {
        redisTemplate.delete("menus");
        return menuMapper.update(record);
    }

    @Override
    public int delete(Integer id) {
        redisTemplate.delete("menus");
        return menuMapper.delete(id);
    }
}
