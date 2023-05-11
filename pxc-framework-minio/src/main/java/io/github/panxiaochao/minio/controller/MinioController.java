package io.github.panxiaochao.minio.controller;

import io.github.panxiaochao.common.response.R;
import io.github.panxiaochao.minio.template.MinioTemplate;
import io.minio.StatObjectResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;

/**
 * {@code MinioController}
 * <p> description: MinioController 应用层
 *
 * @author Lypxc
 * @since 2023-05-11
 */
@RestController
@RequestMapping("/minio")
@RequiredArgsConstructor
public class MinioController {

    private final ApplicationContext applicationContext;

    private MinioTemplate minioTemplate() {
        return applicationContext.getBean(MinioTemplate.class);
    }

    @GetMapping("/makeBucket")
    public R<Boolean> makeBucket(@RequestParam(required = false) String bucketName) {
        if (StringUtils.hasText(bucketName)) {
            minioTemplate().makeBucket(bucketName);
        } else {
            minioTemplate().makeBucket();
        }
        return R.ok();
    }

    @GetMapping("/removeBucket")
    public R<Boolean> removeBucket(@RequestParam(required = false) String bucketName) {
        minioTemplate().deleteBucket(bucketName);
        return R.ok();
    }


    @PostMapping("/uploadFile")
    public R<String> uploadFile(@RequestPart(name = "file") MultipartFile file) throws Exception {
        minioTemplate().putObject(file.getInputStream(), file.getOriginalFilename());
        return R.ok();
    }

    @RequestMapping("/download/{fileName}")
    public void download(HttpServletResponse response, @PathVariable("fileName") String fileName) {
        try (InputStream in = minioTemplate().getObjectInputStream(fileName)) {
            // 获取对象信息
            StatObjectResponse stat = minioTemplate().statObject(fileName);
            response.setContentType(stat.contentType());
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            // 文件下载
            IOUtils.copy(in, response.getOutputStream());
        } catch (Exception e) {
            throw new RuntimeException("minio download is error!", e);
        }
    }

    @PostMapping("/removeObject/{fileName}")
    public R<String> removeObject(@PathVariable("fileName") String fileName) {
        minioTemplate().removeObject(fileName);
        return R.ok();
    }
}
