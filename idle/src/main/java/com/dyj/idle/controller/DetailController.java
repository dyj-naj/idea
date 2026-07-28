package com.dyj.idle.controller;

import com.dyj.idle.common.R;
import com.dyj.idle.entity.GoodsDetail;
import com.dyj.idle.service.GoodsService;
import com.dyj.idle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/detail")
public class DetailController {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private UserService userService;

    //查看商品详情页面,顺便将这个商品的浏览量+1
    @GetMapping("goodsDetail")
    public R<GoodsDetail> getGoodsDetail(Long goodsId,Long userId){
        System.out.println("正在查找商品详细信息");
        try{
            GoodsDetail goodsInfo = goodsService.getGoodsById(goodsId);
            List<String> goodsImages=goodsService.getGoodsAllImage(goodsId);
            boolean collect = userService.isCollect(userId, goodsId);

            //如果不是自己的商品，才添加浏览量
            if(!userId.equals(goodsInfo.getSeller())){
                goodsService.addGoodsLook(goodsId);//添加浏览量
            }
            goodsInfo.setCollect(collect);
            goodsInfo.setImageUrl(goodsImages);

            return R.success(goodsInfo);
        }catch (Exception e){
            return R.error("无法查找到商品信息");
        }
    }

    @GetMapping("changeCollect")
    public R<String> changeCollect(Long goodsId,Long userId){
        System.out.println("更换收藏状态");
        try{
            boolean collect = userService.isCollect(userId, goodsId);
            userService.changeCollect(userId,goodsId,collect);
            return R.success("成功");
        }catch (Exception e){
            return R.error("错误");
        }
    }
}
