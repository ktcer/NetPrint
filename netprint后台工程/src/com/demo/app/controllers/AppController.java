package com.demo.app.controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Controller
@RequestMapping("/app")
public class AppController {

    @RequestMapping("/upload_resource")
    @ResponseBody
    public Object uploadreSource(HttpServletRequest request) {
        // 接受图片
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        Map<String, Object> result = new HashMap<String, Object>();
        // 判断是否有文件
        try {
            if (multipartResolver.isMultipart(request)) {
                // 转换成子类request
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                Iterator<String> iterator = multiRequest.getFileNames();
                while (iterator.hasNext()) {
                    MultipartFile file = multiRequest.getFile((String) iterator
                            .next());
                    System.out.println(file.getOriginalFilename());
                    if ((file != null) && (!file.isEmpty())) {
                        String base_path = request.getSession()
                                .getServletContext().getRealPath("");
                        String path = base_path + File.separator + "static"
                                + File.separator + "upload" + File.separator
                                + "project" + File.separator
                                + file.getOriginalFilename();
                        File file2 = new File("d:\\upload\\"
                                + file.getOriginalFilename());
                        file.transferTo(file2);
                        result.put("resultID", 1);
                        result.put("detail", "上传成功");
                    } else {
                        result.put("resultID", 0);
                        result.put("detail", "上传失败");
                    }
                }
            }

        } catch (Exception e) {
            result.put("resultID", 0);
            result.put("detail", "上传失败");
            System.out.println("文档上传出问题");
            e.printStackTrace();
        }

        return result;
    }

    @RequestMapping("/print")
    @ResponseBody
    public Object printFile(String fileName) throws IOException {
        Map<String, Object> map = new HashMap<>();
        Desktop desktop;
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
            try {
                desktop.print(new File("d:\\upload\\" + fileName));
                map.put("resultId", 1);
                map.put("detail", "success print");
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                map.put("resultId", 0);
                map.put("detail", e.getMessage());
            }
        } else {
            map.put("resultId", -1);
            map.put("detail", "not Support print");
        }
        return map;
    }
}
