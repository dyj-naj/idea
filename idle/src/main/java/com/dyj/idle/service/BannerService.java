package com.dyj.idle.service;

import com.dyj.idle.entity.Banner;
import com.dyj.idle.mapper.BannerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerService {
    @Autowired
    private BannerMapper bannerMapper;

    public List<Banner> getBanner(){
        return bannerMapper.getBanner();
    }
}
