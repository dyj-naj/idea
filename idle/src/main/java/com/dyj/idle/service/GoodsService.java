package com.dyj.idle.service;

import com.dyj.idle.common.R;
import com.dyj.idle.entity.*;
import com.dyj.idle.mapper.GoodsMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;



    public void insert(Long id,Long seller,String desc,double price,String usage,String send,Integer stock,String firstPicture,String goodsCategory){

        Integer goodsTransportFreight = goodsMapper.getGoodsTransportFreight(send);
        goodsMapper.insert(id,seller,desc,price,usage,send,stock,goodsTransportFreight,firstPicture,goodsCategory);

    }

    public void saveGoodsImage(Long goods_id,String url){
        goodsMapper.saveGoodsImage(goods_id,url);
    }

    public void saveGoodsCategory(Long gid,Long p,Long s,Long t){
        goodsMapper.saveGoodsCategory(gid,p,s,t);
    }

    public List<GoodsInfo> getGoodsInfoList(Long categoryId,int skip,int size){//查询商品信息
        return goodsMapper.getGoodsInfoList(categoryId,skip,size);
    }

    public List<String> getGoodsAllImage(Long goodsId){
        return goodsMapper.getGoodsAllImage(goodsId);
    }
    public GoodsDetail getGoodsById(Long goodsId){
        return goodsMapper.getGoodsById(goodsId);
    }

    public List<MyGoods> getMyGoodsByTag(Long userId,int tag){
        return goodsMapper.getMyGoodsByTag(userId,tag);
    }

    public void addGoodsLook(Long goodsId){
        goodsMapper.addGoodsLook(goodsId);
    }
    public void addGoodsWant(Long goodsId){
        goodsMapper.addGoodsWant(goodsId);
    }

    public void insertGoodsHot(Long gid){
        goodsMapper.insertGoodsHot(gid);
    }

    //是否成功购买
    public Boolean isSuccessBuy(Long goodsId){
        if(goodsMapper.subStock(goodsId)>0) {
            return true;
        }else{
            return false;
        }
    }

    //得到用户收藏的所有商品
    public List<MyGoods> getMyCollect(Long userId){
        return goodsMapper.getMyCollect(userId);
    }

    //加库存
    public void plusStock(Long goodsId){
        goodsMapper.plusStock(goodsId);
    }

    public SecKillGoods getSecKillGoods(Long id){
        return goodsMapper.getSecKillGoods(id);
    }

    public void subSecKillStore(Long id){
        goodsMapper.subSecKillStore(id);
    }

    public List<GoodsQualityVO> getGoodsQuality() {
        return goodsMapper.getGoodsQuality();
    }

    public List<GoodsTransportVO> getGoodsTransport() {
        return goodsMapper.getGoodsTransport();
    }

    public List<GoodsTimeDataVO> getGoodsTimeData(String type) {
        List<GoodsTimeDataVO> goodsTimeData = new java.util.ArrayList<>();
        if(type.equals("day")){//本日




            for(int i=0;i<12;i++){
                GoodsTimeDataVO goodsTimeDataVO = new GoodsTimeDataVO();
                goodsTimeDataVO.setName((i*2)+"-"+(i+1)*2+"h");
                double random = Math.random();

                goodsTimeDataVO.setValue((int)(random*100));
                goodsTimeData.add(goodsTimeDataVO);
            }
            return goodsTimeData;
        }else if(type.equals("week")){
            for(int i=0;i<7;i++){
                GoodsTimeDataVO goodsTimeDataVO = new GoodsTimeDataVO();
                goodsTimeDataVO.setName("星期"+(i+1));
                double random = Math.random();
                goodsTimeDataVO.setValue((int)(random*1000));
                goodsTimeData.add(goodsTimeDataVO);
            }
            return goodsTimeData;
        } else if(type.equals("year")){
            for(int i=0;i<12;i++){
                GoodsTimeDataVO goodsTimeDataVO = new GoodsTimeDataVO();
                goodsTimeDataVO.setName(i+1+"月");
                double random = Math.random();
                goodsTimeDataVO.setValue((int)(random*10000));
                goodsTimeData.add(goodsTimeDataVO);
            }
            return goodsTimeData;
        }
        return goodsTimeData;
    }
}
