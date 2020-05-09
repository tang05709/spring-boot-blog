package com.don.donaldblog.service.impl;

import com.don.donaldblog.choice.MoodLevel;
import com.don.donaldblog.mapper.MoodMapper;
import com.don.donaldblog.mapper.MoodRespository;
import com.don.donaldblog.model.Mood;
import com.don.donaldblog.service.MoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("moodService")
public class MoodServiceImpl implements MoodService {
    @Autowired
    private MoodMapper moodMapper;
    @Autowired
    private MoodRespository moodRespository;

    @Override
    public List<Mood> getAll() {
        return moodMapper.getAll();
    }

    @Override
    public List<Mood> getSearch(Map<String, Object> conditions) {
        return moodMapper.getSearch(conditions);
    }

    @Override
    public Mood getById(Integer id) {
        return moodMapper.getById(id);
    }

    @Override
    public int create(Mood record) {
        // 同步elasticsearch, 这里注意mapper要加useGeneratedKeys="true" keyProperty="id" keyColumn="id"
        int res = moodMapper.create(record);
        moodRespository.save(record);
        return res;
    }

    @Override
    public MoodLevel[] getLevels()
    {
        return MoodLevel.values();
    }

    @Override
    public int softDelete(Integer id) {
        Mood record = moodMapper.getById(id);
        Integer status = record.getStatus() == 9 ? 0 : 9;
        if (status == 9) {
            moodRespository.delete(record);
        } else {
            record.setStatus(status);
            moodRespository.save(record);
        }
        return moodMapper.softDelete(record.getId(), status);
    }

    public Page<Mood> getEsPageList(Integer start, Integer size)
    {
        return moodRespository.findAll(PageRequest.of(start, size, Sort.Direction.DESC, "id"));
    }
}
