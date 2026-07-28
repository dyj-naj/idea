package com.dyj.idle.mapper;


import com.dyj.idle.entity.Category;
import com.dyj.idle.entity.PriCategory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Select("select * from pricategory")
    List<Category> getAllPriCategory();//得到所有一级分类标签

    @Select("select id,name from seccategory where fa_id=#{id}")
    List<Category> getAllSecCategory(Long id);//通过id查找所有的二级分类

    @Select("select id,name from thicategory where fa_id=#{id}")
    List<Category> getAllThiCategory(Long id);//通过id查找所有的三级分类

    @Insert("insert into thicategory(id,fa_id,name) values (#{id1},#{id2},#{name})")
    void test(Long id1,Long id2,String name);

}
