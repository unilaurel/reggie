package com.itheima.reggie.controller;

import com.itheima.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;


/**
 * ClassName: CommonController
 * Package: com.itheima.reggie.controller
 * Description：文件上传和下载
 *
 * @Author :zyp
 * @Create 2023/08/25 21:58
 * @Version 1.0
 */
@RestController
@RequestMapping("/common")
@Slf4j

public class CommonController {
    @Value("${reggie.path}")
    private String basePath;

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        //file是一个临时文件，需要转存到指定位置，否则本次请求完成后临时文件会删除
        log.info(file.toString());

        //原始文件名
        String originalFilename = file.getOriginalFilename();

        //使用UUID重新生成文件名，防止文件名重复造成覆盖。
        String filename = UUID.randomUUID().toString();
//        filename=filename+"."+originalFilename.split("\\.")[1];
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        filename += suffix;

        //创建一个目录对象
        File dir = new File(basePath);
        if (!dir.exists()) {
            //目录不存在，需要创建
            dir.mkdirs();
        }

        try {
//            file.transferTo(new File(basePath+originalFilename));
            file.transferTo(new File(basePath + filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return R.success(filename);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
        try {
            //输入流，通过输入流读取文件内容
            FileInputStream fis = new FileInputStream(new File(basePath + name));
            //输出流，通过输出流将文件写回浏览器，在浏览器中展示图片
            ServletOutputStream ops = response.getOutputStream();
            response.setContentType("image/jpeg");
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = fis.read(bytes)) != -1) {
                ops.write(bytes, 0, len);
            }

            //关闭流
            fis.close();
            ops.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
