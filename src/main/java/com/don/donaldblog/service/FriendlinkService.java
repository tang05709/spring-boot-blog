package com.don.donaldblog.service;

import com.don.donaldblog.model.Friendlink;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FriendlinkService {
    public List<Friendlink> getAll();
    public List<Friendlink> getNativeAll();
    public Friendlink getById(Integer id);
    public int create(Friendlink record);
    public int update(Friendlink record);
    public int softDelete(Integer id);
}
