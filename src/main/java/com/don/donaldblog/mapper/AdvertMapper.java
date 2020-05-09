package com.don.donaldblog.mapper;

import com.don.donaldblog.model.Advert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdvertMapper {
    public List<Advert> getAll();
    public Advert getById(Integer id);
    public int create(Advert record);
    public int update(Advert record);
    public int softDelete(@Param("id") Integer id, @Param("status") Integer status);
}
