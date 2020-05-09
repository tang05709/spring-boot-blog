package com.don.donaldblog.service;

import com.don.donaldblog.model.AdvertCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdvertCategoryService {
    public List<AdvertCategory> getAll();
    public AdvertCategory getById(Integer id);
    public int create(AdvertCategory record);
    public int update(AdvertCategory record);
    public int softDelete(Integer id);
}
