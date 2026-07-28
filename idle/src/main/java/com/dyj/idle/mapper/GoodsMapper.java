package com.dyj.idle.mapper;


import com.dyj.idle.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GoodsMapper {

    @Insert("insert into goods(`id`, `seller`, `desc`, `price`, `usage`, `send`,`creat_time`,`state`,`stock`,`freight`,`first_picture`,`category`) values " +
            "(#{id},#{seller},#{desc},#{price},#{usage},#{send},NOW(),1,#{stock},#{freight},#{firstPicture},#{goodsCategory})")
    void insert(Long id,Long seller,String desc,double price,String usage,String send,Integer stock,double freight,String firstPicture,String goodsCategory);

    @Insert("insert into goods_images (goods_id,image_url) values (#{goods_id},#{url})")
    void saveGoodsImage(Long goods_id,String url);

    @Insert("insert into goods_category(goods_id,pricategory_id,seccategory_id,thicategory_id) values (#{gid},#{p},#{s},#{t})")
    void saveGoodsCategory(Long gid,Long p,Long s,Long t);

    List<GoodsInfo> getGoodsInfoList(@Param("categoryId") Long categoryId,@Param("skip") int skip,@Param("size") int size);

    List<String> getGoodsAllImage(@Param("goodsId") Long goodsId);

    GoodsDetail getGoodsById(@Param("goodsId") Long goodsId);

    List<MyGoods> getMyGoodsByTag(@Param("userId") Long userId,@Param("tag") int tag);

    @Update("UPDATE goods_hot set look_num=look_num+1 where goods_id=#{goodsId}")
    void addGoodsLook(Long goodsId);//增加商品的浏览量

    @Update("UPDATE goods_hot set want_num=want_num+1 where goods_id=#{goodsId}")
    void addGoodsWant(Long goodsId);//增加商品的想要量

    @Insert("insert into goods_hot(goods_id) values (#{gid})")
    void insertGoodsHot(Long gid);

    Integer subStock(@Param("goodsId") Long goodsId);

    List<MyGoods> getMyCollect(@Param("userId") Long userId);

    @Update("update goods set stock=stock+1 where id=#{goodsId}")
    void plusStock(Long goodsId);

    @Select("SELECT *, TIMESTAMPDIFF(SECOND, NOW(), start_time) AS start_count_down,TIMESTAMPDIFF(SECOND, NOW(), end_time) AS end_count_down FROM seckill WHERE id=#{id} AND end_time > NOW()")
    SecKillGoods getSecKillGoods(Long id);

    @Update("update seckill set sec_kill_num=sec_kill_num-1 where id=#{id}")
    void subSecKillStore(Long id);

    @Select("SELECT id,name,description,min_discount,max_discount FROM goods_quality")
    List<GoodsQualityVO> getGoodsQuality();

    @Select("SELECT id,name,description,company,estimated_days,freight from goods_transport where is_active=1")
    List<GoodsTransportVO> getGoodsTransport();

    @Select("select freight from goods_transport where name=#{name}")
    Integer getGoodsTransportFreight(String name);


    @Select("select name from pricategory where id=#{id}")
    String getPriCategory(Long id);//得到所有一级分类标签

    @Select("select name from seccategory where id=#{id}")
    String getSecCategory(Long id);//通过id查找所有的二级分类

    @Select("select name from thicategory where id=#{id}")
    String getThiCategory(Long id);//通过id查找所有的三级分类

    @Select("select id,name,`value` from goods_category_count")
    List<GoodsDataVO> getGoodsData();
}
