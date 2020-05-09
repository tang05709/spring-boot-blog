package com.don.donaldblog.service.impl;

import com.don.donaldblog.mapper.AdvertMapper;
import com.don.donaldblog.model.Advert;
import com.don.donaldblog.service.AdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("advertService")
public class AdvertServiceImpl implements AdvertService {
    @Autowired
    private AdvertMapper advertMapper;
    @Override
    public List<Advert> getAll() {
        return advertMapper.getAll();
    }

    @Override
    public Advert getById(Integer id) {
        return advertMapper.getById(id);
    }

    @Override
    public int create(Advert record) {
        return advertMapper.create(record);
    }

    @Override
    public int update(Advert record) {
        return advertMapper.update(record);
    }

    @Override
    public int softDelete(Integer id) {
        Advert record = advertMapper.getById(id);
        Integer status = record.getStatus() == 9 ? 0 : 9;
        return advertMapper.softDelete(id, status);
    }
}
