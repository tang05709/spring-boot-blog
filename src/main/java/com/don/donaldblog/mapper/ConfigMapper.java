package com.don.donaldblog.mapper;

import com.don.donaldblog.model.Config;

import java.util.List;

public interface ConfigMapper {
    public List<Config> getAll();
    public Config getById(Integer id);
    public int create(Config record);
    public int update(Config record);
    public int delete(Integer id);
}
