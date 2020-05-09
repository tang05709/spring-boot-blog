package com.don.donaldblog.mapper;

import com.don.donaldblog.model.AdvertCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdvertCategoryMapper {
    public List<AdvertCategory> getAll();
    public AdvertCategory getById(Integer id);
    public int create(AdvertCategory record);
    public int update(AdvertCategory record);
    public int softDelete(@Param("id") Integer id, @Param("status") Integer status);
}
