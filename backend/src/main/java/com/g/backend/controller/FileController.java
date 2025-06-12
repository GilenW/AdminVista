package com.g.backend.controller;


import cn.hutool.core.io.FileUtil;
import com.g.backend.common.Result;
import com.g.backend.exception.CustomException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/files")
public class FileController {

    private static final String URL_PREFIX = "http://localhost:9090/files/";

    private static final String filePath = System.getProperty("user.dir") + "/src/main/resources/static/files/";

    @PostMapping("/upload")
    public Result uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        if(!FileUtil.isDirectory(filePath)){
            FileUtil.mkdir(filePath);
        }
        String fileName =
                System.currentTimeMillis() + "_" + originalFileName;
        String realPath = filePath + fileName;
        try{
            FileUtil.writeBytes(file.getBytes(), realPath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException("500", "Failed to upload file");
        }
        String url = URL_PREFIX + "download/" + fileName;
        return Result.success(url);
    }


    @GetMapping("/download/{fileName}")
    public Result download(@PathVariable String fileName,
                               HttpServletResponse response) throws IOException {

        try{
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(fileName,
                            StandardCharsets.UTF_8));
            response.setContentType("application/octet-stream");
            OutputStream os = response.getOutputStream();
            String realPath = filePath + fileName;
            os.write(FileUtil.readBytes(realPath));
            os.flush();
            os.close();

        }catch (IOException e){
            e.printStackTrace();
            throw new CustomException("500", "Failed to download file");
        }
        return Result.success();
    }
}
