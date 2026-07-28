package com.dyj.idle.controller;

import com.dyj.idle.common.R;
import com.dyj.idle.entity.GoodsDataVO;
import com.dyj.idle.mapper.GoodsMapper;
import com.dyj.idle.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: 杜英杰
 * @CreateTime: 2025-06-24
 * @Description:
 * @Version: 1.0
 */

@RestController
@RequestMapping("/data")
public class DataController {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsService goodsService;
    /**
     * 得到商品种类发布的数量
     */
    @GetMapping("/getGoodsCategoryCount")
    public R getGoodsCategoryCount() {
        List<GoodsDataVO> goodsData = goodsMapper.getGoodsData();
        return R.success(goodsData);
    }

    @GetMapping("/getGoodsTimeData")
    public R getGoodsTimeData(@RequestParam("type") String type) {
        //得到商品时间数据
        return R.success(goodsService.getGoodsTimeData(type));
    }
}
