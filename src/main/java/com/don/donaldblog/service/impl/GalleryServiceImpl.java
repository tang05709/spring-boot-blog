package com.don.donaldblog.service.impl;

import com.don.donaldblog.mapper.GalleryMapper;
import com.don.donaldblog.model.Gallery;
import com.don.donaldblog.service.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("galleryService")
public class GalleryServiceImpl implements GalleryService {
    @Autowired
    private GalleryMapper galleryMapper;
    @Override
    public List<Gallery> getAll() {
        return galleryMapper.getAll();
    }

    @Override
    public List<Gallery> getByArticle(Integer articleId) {
        return galleryMapper.getByArticle(articleId);
    }

    @Override
    public Gallery getById(Integer id) {
        return galleryMapper.getById(id);
    }

    @Override
    public int create(Gallery record) {
        return galleryMapper.create(record);
    }

    @Override
    public int delete(Integer id) {
        return galleryMapper.delete(id);
    }
}
