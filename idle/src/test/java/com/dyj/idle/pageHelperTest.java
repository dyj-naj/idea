package com.dyj.idle;

import com.dyj.idle.entity.GoodsDetail;
import com.dyj.idle.entity.GoodsInfo;
import com.dyj.idle.mapper.GoodsMapper;
import com.dyj.idle.service.GoodsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class pageHelperTest {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsMapper goodsMapper;
    @Test
    void test(){

        List<GoodsInfo> goodsInfoList = goodsMapper.getGoodsInfoList(583708623612411904l, 1, 100);
        goodsInfoList.forEach(e-> System.out.println(e));

    }
}
