package com.dyj.idle.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectRequest;
import com.dyj.idle.utils.UploadTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class OssService {

    @Autowired
    private OSS ossClient;

    @Value("${aliyun.oss.bucket-name}")
    private String bucketName;

    public String uploadFile(MultipartFile file,String fileName) throws IOException {


        // 创建一个上传请求
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, file.getInputStream());
        // 上传文件
        ossClient.putObject(putObjectRequest);

        // 返回文件的访问 URL
        return String.format("https://%s.%s/%s", bucketName, "oss-cn-beijing.aliyuncs.com", fileName);
    }

    public boolean deleteImage(String filePath){
        try {
            ossClient.deleteObject(bucketName, filePath);
        }catch (Exception e){
            return  false;
        }
        return  true;
    }
}
