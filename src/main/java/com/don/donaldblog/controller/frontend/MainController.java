package com.don.donaldblog.controller.frontend;

import com.don.donaldblog.model.*;
import com.don.donaldblog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice(basePackages = "com.don.donaldblog.controller.frontend")
public class MainController {
    @Autowired
    private ConfigService configService;
    @Autowired
    private FriendlinkService friendlinkService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private MenuService menuService;
    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 全局配置数据
     * @return
     */
    @ModelAttribute(name = "config")
    public Map<String, String> getConfig()
    {
        List<Config> configs = (List<Config>)redisTemplate.opsForValue().get("config");
        if(configs == null) {
            configs = configService.getAll();
            redisTemplate.opsForValue().set("config", configs);
        }
        System.out.println(configs);
        Map<String, String> configMap = new HashMap<String, String>();
        for(Config cg : configs) {
            configMap.put(cg.getCkey(), cg.getCvalue());
        }
        return configMap;
    }

    /**
     * 全局栏目树
     * @return
     */
    @ModelAttribute(name = "categoryTree")
    public  List<Category> getCategories()
    {
        List<Category> categories = (List<Category>)redisTemplate.opsForValue().get("categories");
        if (categories == null) {
            categories = categoryService.getNativeAll();
            redisTemplate.opsForValue().set("categories", categories);
        }
        return categories;
    }

    /**
     * 全局菜单
     * @return
     */
    @ModelAttribute(name = "menus")
    public List<Menu> getMenus()
    {
        List<Menu> menus = (List<Menu>)redisTemplate.opsForValue().get("menus");
        if (menus == null) {
            menus = menuService.getAll();
            redisTemplate.opsForValue().set("menus", menus);
        }

        return menus;
    }

    /**
     * 全局友情链接
     * @return
     */
    @ModelAttribute(name = "friendlinks")
    public List<Friendlink> getFriendlinks()
    {
        List<Friendlink> friendlinks = (List<Friendlink>)redisTemplate.opsForValue().get("friendlinks");
        if (friendlinks == null) {
            friendlinks = friendlinkService.getNativeAll();
            redisTemplate.opsForValue().set("friendlinks", friendlinks);
        }
        return friendlinks;
    }

    /**
     * 全局最热文章
     * @return
     */
    @ModelAttribute(name = "hotArticles")
    public List<Article> hotList()
    {
        return articleService.getHotList();
    }

    /**
     * 全局最新文章
     * @return
     */
    @ModelAttribute(name = "newArticles")
    public List<Article> newList()
    {
        return articleService.getNewList();
    }
}
