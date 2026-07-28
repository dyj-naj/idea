package com.dyj.idle.mapper;

import com.dyj.idle.entity.GoodsInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SearchMapper {
    
    @Select("select text from search_hot_word where text like #{text} limit 6")
    List<String> getSearchWord(String text);

    List<GoodsInfo> getSearchGoods(@Param("text") String text);

}
