package com.don.donaldblog.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class ToolUtils {
    /**
     * 生成文件名
     * @param file
     * @return
     */
    public static String makeFileName(String file)
    {
        // 获取文件后缀
        String fileExt = file.substring(file.lastIndexOf("."));
        // UUID
        String uuid = UUID.randomUUID().toString();
        // 去掉中间的-
        String newUrl = uuid.concat(fileExt);
        return newUrl;
    }

    /**
     * 生成当前日的文件名
     * @return
     */
    public static String makeDatePath()
    {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return formatter.format(date);
    }
}
