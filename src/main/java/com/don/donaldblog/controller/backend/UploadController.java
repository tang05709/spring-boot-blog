package com.don.donaldblog.controller.backend;


import com.don.donaldblog.utils.AliOss;
import com.don.donaldblog.utils.JsonUtils;
import com.don.donaldblog.utils.ToolUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/backend")
public class UploadController {
    @Autowired
    private AliOss oss;
    @PostMapping("/upload")
    public JsonUtils upload(HttpServletRequest request)
    {
        String dir = request.getParameter("dir");
        if (dir == null) {
            dir = "common";
        }
        try {
            Map<String, String> policy = oss.getPolicy(dir);
            return JsonUtils.success(policy);
        } catch (Exception e) {
            String msg = "获取签名错误！";
            return JsonUtils.fail(msg);
        }
    }

    @PostMapping(value = "/detail-upload", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JsonUtils detailUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request)
    {
        try {
            String fileName = file.getOriginalFilename();
            String newFileName = ToolUtils.makeFileName(fileName);
            String dir = "detail/" + ToolUtils.makeDatePath();
            String fileKey = dir.concat("/").concat(newFileName);
            String url = oss.ossUpload(file, fileKey);
            if (url == null) {
                return JsonUtils.fail("上传失败！");
            }
            return JsonUtils.success(url);
        } catch (Exception e) {
            return JsonUtils.fail("上传失败！");
        }

    }
}
