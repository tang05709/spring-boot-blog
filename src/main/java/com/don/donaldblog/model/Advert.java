package com.don.donaldblog.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class Advert implements Serializable {
    private Integer id;
    @NotBlank(message = "名称不能为空")
    private String title;
    @Min(value = 0, message = "分类不能为空")
    private Integer advertCategoryId;
    private AdvertCategory advertCategory;
    private String image;
    private String url;
    private String intro;
    @Min(value = 0, message = "排序不能为空且必须正数")
    private Integer sort;
    private Integer status;
    private Long created;
    private Long updated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getAdvertCategoryId() {
        return advertCategoryId;
    }

    public void setAdvertCategoryId(Integer advertCategoryId) {
        this.advertCategoryId = advertCategoryId;
    }

    public AdvertCategory getAdvertCategory() {
        return advertCategory;
    }

    public void setAdvertCategory(AdvertCategory advertCategory) {
        this.advertCategory = advertCategory;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }
}
