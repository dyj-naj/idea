package com.dyj.idle_admin.mapper;

import com.dyj.idle_admin.domain.po.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dyj.idle_admin.domain.query.GoodsPageQuery;
import com.dyj.idle_admin.domain.vo.GoodsFirstCategory;
import com.dyj.idle_admin.domain.vo.GoodsSecondCategory;
import com.dyj.idle_admin.domain.vo.GoodsThirdCategory;
import com.dyj.idle_admin.domain.vo.GoodsVO;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dyj
 * @since 2025-05-18
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {
     List<GoodsVO> getGoodsList(@Param("query") GoodsPageQuery query);


     Long getGoodsCount(@Param("query") GoodsPageQuery query);

     @Select("select id,name from pricategory")
     List<GoodsFirstCategory> getAllPriCategory();//得到所有一级分类标签

     @Select("select id,name from seccategory where fa_id=#{id}")
     List<GoodsSecondCategory> getAllSecCategory(Long id);//通过id查找所有的二级分类

     @Select("select id,name from thicategory where fa_id=#{id}")
     List<GoodsThirdCategory> getAllThiCategory(Long id);//通过id查找所有的三级分类

     @Insert("insert into pricategory(id,name) values (#{id},#{name})")
     void addPriCategory(@Param("id") Long id,@Param("name") String name);

     @Insert("insert into seccategory(id,fa_id,name) values (#{id},#{faId},#{name})")
     void addSecCategory(@Param("pos") String pos,@Param("id") Long id,@Param("faId") Long faId,@Param("name") String name);

     @Insert("insert into thicategory(id,fa_id,name) values (#{id},#{faId},#{name})")
     void addThiCategory(@Param("pos") String pos,@Param("id") Long id,@Param("faId") Long faId,@Param("name") String name);

     /**
      * 删除分类
      * @param id
      */
     @Delete("delete from pricategory where id=#{id}")
     void deletePriCategory(@Param("id") Long id);

     @Delete("delete from seccategory where id= #{id}")
     void deleteSecCategory(@Param("id") Long id);

     @Delete("delete from thicategory where id= #{id}")
     void deleteThiCategory(@Param("id") Long id);

     @Update("update pricategory set name=#{name} where id=#{id}")
     void updatePriCategory(@Param("id") Long id,@Param("name") String name);

     @Update("update seccategory set name= #{name} where id= #{id}")
     void updateSecCategory(@Param("id") Long id,@Param("name") String name);

     @Update("update thicategory set name= #{name} where id= #{id}")
     void updateThiCategory(@Param("id") Long id,@Param("name") String name);

     @Select("select first_picture from goods where id= #{id}")
     String getFirstPicture(@Param("id") Long id);

     @Select("select `desc` from goods where id= #{id}")
     String getDesc(@Param("id") Long id);
}
