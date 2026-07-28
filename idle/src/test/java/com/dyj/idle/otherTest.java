package com.dyj.idle;

import com.dyj.idle.entity.SecKillGoods;
import com.dyj.idle.service.GoodsService;
import com.dyj.idle.service.OssService;
import com.dyj.idle.utils.UploadTag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class otherTest {
    @Autowired
    private OssService ossService;

    @Autowired
    private GoodsService goodsService;

    @Test
    void test(){
        SecKillGoods secKillGoods = goodsService.getSecKillGoods(1L);
        System.out.println(secKillGoods);

//        String url="https://dyjdyj.oss-cn-beijing.aliyuncs.com/3755f31624a653f4af2c2af90f48544c.png";
//        ossService.deleteImage(extractFileName(url));

    }
    public static String extractFileName(String url) {
        int lastIndexOfSlash = url.lastIndexOf('/');
        if (lastIndexOfSlash != -1) {
            return url.substring(lastIndexOfSlash + 1);
        } else {
            return url; // 如果没有找到斜杠，返回整个字符串
        }
    }
}
