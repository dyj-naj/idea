package com.dyj.idle;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectRequest;
import com.dyj.idle.entity.User;
import com.dyj.idle.service.UserService;
import org.apache.commons.codec.digest.Md5Crypt;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.util.UUID;

@SpringBootTest
class IdleApplicationTests {
    @Autowired
    private OSS ossClient;

    @Value("${aliyun.oss.bucket-name}")
    private String bucketName;

    @Test
    void contextLoads() {

//        File file=new File("D:\\cxdownload\\1154BF9BE93FEBD8B54193F39A6D7268.png");
//        String originalFilename=file.getName();
//        String fileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
        // 创建一个上传请求
//        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, file);
//        // 上传文件
//        ossClient.putObject(putObjectRequest);
//        String res= String.format("https://%s.%s/%s", bucketName, "oss-cn-beijing.aliyuncs.com", fileName);
//        System.out.println(fileName);
//        System.out.println(res);

    }

}
