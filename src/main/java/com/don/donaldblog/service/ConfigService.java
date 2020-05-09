package com.don.donaldblog.service;

import com.don.donaldblog.model.Config;

import java.util.List;

public interface ConfigService {
    public List<Config> getAll();
    public Config getById(Integer id);
    public int create(Config record);
    public int update(Config record);
    public int delete(Integer id);
}
