package com.don.donaldblog.service;

import com.don.donaldblog.model.Feedback;

import java.util.List;
import java.util.Map;

public interface FeedbackService {
    public List<Feedback> getAll();
    public List<Feedback> getSearch(Map<String, Object> conditions);
    public Feedback getById(Integer id);
    public int create(Feedback record);
    public int softDelete(Integer id);
}
