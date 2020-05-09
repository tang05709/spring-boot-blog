package com.don.donaldblog.mapper;

import com.don.donaldblog.model.Mood;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MoodRespository extends ElasticsearchRepository<Mood, Integer> {
}
