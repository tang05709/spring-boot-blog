package com.don.donaldblog.mapper;

import com.don.donaldblog.model.Mood;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MoodMapper {
    public List<Mood> getAll();
    public List<Mood> getSearch(Map<String, Object> conditions);
    public Mood getById(Integer id);
    public int create(Mood record);
    public int softDelete(@Param("id") Integer id, @Param("status") Integer status);
}
