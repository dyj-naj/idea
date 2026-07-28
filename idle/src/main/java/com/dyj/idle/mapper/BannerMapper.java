package com.dyj.idle.mapper;

import com.dyj.idle.entity.Banner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BannerMapper {

    //获得轮播图的所有图片路径
    @Select("select * from banner")
    List<Banner> getBanner();

}
