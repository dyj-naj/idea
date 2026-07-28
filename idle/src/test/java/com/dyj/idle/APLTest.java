package com.dyj.idle;

import com.dyj.idle.entity.Banner;
import com.dyj.idle.service.BannerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class APLTest {

    @Autowired
    private BannerService bannerService;

    @Test
    void test(){
        List<Banner> banner = bannerService.getBanner();
        for(Banner be:banner){
            System.out.println(be.getId()+" "+be.getImgUrl());
        }
    }

}
