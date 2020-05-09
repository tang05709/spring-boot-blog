package com.don.donaldblog.service.impl;

import com.don.donaldblog.mapper.AdvertCategoryMapper;
import com.don.donaldblog.model.AdvertCategory;
import com.don.donaldblog.service.AdvertCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("advertCategoryService")
public class AdvertCategoryServiceImpl implements AdvertCategoryService {
    @Autowired
    AdvertCategoryMapper advertCategoryMapper;

    @Override
    public List<AdvertCategory> getAll() {
        return advertCategoryMapper.getAll();
    }

    @Override
    public AdvertCategory getById(Integer id) {
        return advertCategoryMapper.getById(id);
    }

    @Override
    public int create(AdvertCategory record) {
        return advertCategoryMapper.create(record);
    }

    @Override
    public int update(AdvertCategory record) {
        return advertCategoryMapper.update(record);
    }

    @Override
    public int softDelete(Integer id) {
        AdvertCategory record = advertCategoryMapper.getById(id);
        Integer status = record.getStatus() == 9 ? 0 : 9;
        return advertCategoryMapper.softDelete(id, status);
    }
}
