package com.don.donaldblog.mapper;

import com.don.donaldblog.model.Feedback;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface FeedbackMapper {
    public List<Feedback> getAll();
    public List<Feedback> getSearch(Map<String, Object> conditions);
    public Feedback getById(Integer id);
    public int create(Feedback record);
    public int softDelete(@Param("id") Integer id, @Param("status") Integer status);
}
