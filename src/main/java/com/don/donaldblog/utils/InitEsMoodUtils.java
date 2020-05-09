package com.don.donaldblog.utils;

import com.don.donaldblog.mapper.MoodRespository;
import com.don.donaldblog.model.Mood;
import com.don.donaldblog.service.MoodService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InitEsMoodUtils implements Runnable {
    private MoodService moodService;
    private MoodRespository moodRespository;

    private static final Logger LOG = LoggerFactory.getLogger(InitEsMoodUtils.class);
    Thread t;

    public InitEsMoodUtils(MoodService moodService, MoodRespository moodRespository)
    {
        this.moodService = moodService;
        this.moodRespository = moodRespository;
    }

    @Override
    public void run() {
        LOG.info("==========elasticsearch导入mood开始  ==========");
        Map<String, Object> conditions = new HashMap<String, Object>();
        conditions.put("status", 0);
        try {
            for(int i = 1; i< 100; i++) {
                PageHelper.startPage(i, 20);
                List<Mood> moods = moodService.getSearch(conditions);
                for(Mood mood : moods) {
                    LOG.info("导入 Mood " + mood.getId().toString());
                    moodRespository.save(mood);
                }
                Thread.sleep(100);
            }
        } catch (Exception e) {
            LOG.info(e.getMessage());
        }
        LOG.info("==========elasticsearch导入mood结束  ==========");
    }

    public void start()
    {
        if (t == null) {
            t = new Thread(this, "mood_index");
            t.start();
            return ;
        }
        this.start();
    }
}
