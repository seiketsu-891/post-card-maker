package com.louie.coding.service;


import com.louie.coding.exception.BusinessException;
import com.louie.coding.exception.BusinessExceptionCode;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {
    @Value("${spring.qcloud.secretId}")
    private String SECRET_ID;
    @Value("${spring.qcloud.secretKey}")
    private String SECRET_KEY;
    @Value("${spring.qcloud.region}")
    private String REGION;
    @Value("${spring.qcloud.prefix}")
    private String PATH_PREFIX;
    @Value("${spring.qcloud.bucketName}")
    private String BUCKET_NAME;
    @Value("${spring.qcloud.url}")
    private String URL;
    
    public String uploadFile(MultipartFile file, String directory) {
        COSCredentials cred = new BasicCOSCredentials(SECRET_ID, SECRET_KEY);
        ClientConfig clientConfig = new ClientConfig(new Region(REGION));
        System.out.println("xxxxxx");
        System.out.println(SECRET_ID);
        System.out.println(REGION);
        if (file == null) {
            throw new BusinessException(BusinessExceptionCode.EMPTY_FILE);
        }

        // 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);

        // 设置文件名
        String oldFileName = file.getOriginalFilename();
        if (oldFileName == null) {
            oldFileName = "";
        }
        String extension = oldFileName.substring(oldFileName.lastIndexOf("."));
        String fileName = UUID.randomUUID() + extension;

        try {
            File localFile = File.createTempFile("temp", null);
            file.transferTo(localFile);
            // 指定路径
            String path = PATH_PREFIX + directory + fileName;
            PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, path, localFile);
            cosclient.putObject(putObjectRequest);
            return URL + putObjectRequest.getKey();
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(BusinessExceptionCode.FILE_UPLOAD_ERROR);
        } finally {
            cosclient.shutdown();
        }
    }
}

