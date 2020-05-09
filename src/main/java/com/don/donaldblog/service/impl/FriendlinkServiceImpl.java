package com.don.donaldblog.service.impl;

import com.don.donaldblog.mapper.FriendlinkMapper;
import com.don.donaldblog.model.Friendlink;
import com.don.donaldblog.service.FriendlinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("friendlinkService")
public class FriendlinkServiceImpl implements FriendlinkService {
    @Autowired
    private FriendlinkMapper friendlinkMapper;
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public List<Friendlink> getAll() {
        return friendlinkMapper.getAll();
    }

    @Override
    public List<Friendlink> getNativeAll() {
        return friendlinkMapper.getNativeAll();
    }

    @Override
    public Friendlink getById(Integer id) {
        return friendlinkMapper.getById(id);
    }

    @Override
    public int create(Friendlink record) {
        redisTemplate.delete("friendlinks");
        return friendlinkMapper.create(record);
    }

    @Override
    public int update(Friendlink record) {
        redisTemplate.delete("friendlinks");
        return friendlinkMapper.update(record);
    }

    @Override
    public int softDelete(Integer id) {
        redisTemplate.delete("friendlinks");
        Friendlink record = friendlinkMapper.getById(id);
        Integer status = record.getStatus() == 9 ? 0 : 9;
        return friendlinkMapper.softDelete(id, status);
    }
}
