package com.don.donaldblog.mapper;

import com.don.donaldblog.model.Gallery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GalleryMapper {
    public List<Gallery> getAll();
    public List<Gallery> getByArticle(Integer articleId);
    public Gallery getById(Integer id);
    public int create(Gallery record);
    public int delete(@Param("id") Integer id);
}
