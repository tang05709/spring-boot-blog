package com.don.donaldblog.service.impl;

import com.don.donaldblog.mapper.ConfigMapper;
import com.don.donaldblog.model.Config;
import com.don.donaldblog.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("configService")
public class ConfigServiceImpl implements ConfigService {
    @Autowired
    private ConfigMapper configMapper;
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public List<Config> getAll() {
        return configMapper.getAll();
    }

    @Override
    public Config getById(Integer id) {
        return configMapper.getById(id);
    }

    @Override
    public int create(Config record) {
        redisTemplate.delete("config");
        return configMapper.create(record);
    }

    @Override
    public int update(Config record) {
        redisTemplate.delete("config");
        return configMapper.update(record);
    }

    @Override
    public int delete(Integer id) {
        redisTemplate.delete("config");
        return configMapper.delete(id);
    }
}
