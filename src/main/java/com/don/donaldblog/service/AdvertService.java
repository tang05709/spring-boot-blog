package com.don.donaldblog.service;

import com.don.donaldblog.model.Advert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdvertService {
    public List<Advert> getAll();
    public Advert getById(Integer id);
    public int create(Advert record);
    public int update(Advert record);
    public int softDelete(Integer id);
}
