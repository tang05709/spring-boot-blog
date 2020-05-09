package com.don.donaldblog.service.impl;

import com.don.donaldblog.mapper.FeedbackMapper;
import com.don.donaldblog.model.Feedback;
import com.don.donaldblog.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("feedbackService")
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackMapper feedbackMapper;
    @Override
    public List<Feedback> getAll() {
        return feedbackMapper.getAll();
    }

    @Override
    public List<Feedback> getSearch(Map<String, Object> conditions) {
        return feedbackMapper.getSearch(conditions);
    }

    @Override
    public Feedback getById(Integer id) {
        return feedbackMapper.getById(id);
    }

    @Override
    public int create(Feedback record) {
        return feedbackMapper.create(record);
    }

    @Override
    public int softDelete(Integer id) {
        Feedback record = feedbackMapper.getById(id);
        Integer status = record.getStatus() == 9 ? 0 : 9;
        return feedbackMapper.softDelete(id, status);
    }
}
