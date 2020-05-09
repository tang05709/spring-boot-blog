package com.don.donaldblog.mapper;

import com.don.donaldblog.model.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper {
    public List<Menu> getAll();
    public Menu getById(Integer id);
    public int create(Menu record);
    public int update(Menu record);
    public int delete(@Param("id") Integer id);
}
