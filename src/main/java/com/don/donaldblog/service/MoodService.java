package com.don.donaldblog.service;

import com.don.donaldblog.choice.MoodLevel;
import com.don.donaldblog.model.Mood;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface MoodService {
    public List<Mood> getAll();
    public List<Mood> getSearch(Map<String, Object> conditions);
    public Mood getById(Integer id);
    public int create(Mood record);
    public MoodLevel[] getLevels();
    public int softDelete(Integer id);
    public Page<Mood> getEsPageList(Integer start, Integer size);
}
