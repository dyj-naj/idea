package com.dyj.idle.service;

import com.dyj.idle.entity.GoodsInfo;
import com.dyj.idle.entity.SearchWord;
import com.dyj.idle.mapper.SearchMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class SearchService {
    @Autowired
    private SearchMapper searchMapper;

    public List<SearchWord> getSearchWord(String text){
        text="%"+text+"%";
        List<String> searchWord = searchMapper.getSearchWord(text);

        List<SearchWord> searchWords=new LinkedList<>();
        for(String s:searchWord){
            SearchWord sw=new SearchWord();
            sw.setValue(s);
            sw.setLink("/search/"+s);

            searchWords.add(sw);
        }
        return searchWords;
    }

    public  List<GoodsInfo> getSearchGoods(String text){
        return searchMapper.getSearchGoods("%"+text+"%");
    }
}
