package com.dyj.idle.service;

import com.dyj.idle.entity.Category;
import com.dyj.idle.entity.PriCategory;
import com.dyj.idle.entity.SubCategory;
import com.dyj.idle.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    public List<PriCategory> getAllCategory(){//得到所有分类
        List<Category> allPriCategory = categoryMapper.getAllPriCategory();//找到所有一级分类
        List<PriCategory> categoryInfo=new ArrayList<>();//初始化容器
        for(Category category:allPriCategory){
            PriCategory priCategory=new PriCategory();
            priCategory.setLabel(category.getName());//一级标签
            priCategory.setId(category.getId());//id
            priCategory.setImageUrl(category.getImageUrl());
            List<SubCategory> SubCategoryList=new ArrayList<>();;

            List<Category> allSecCategory = categoryMapper.getAllSecCategory(category.getId());//查询对应的子类
            for(Category category1:allSecCategory){
                SubCategory subCategory=new SubCategory();
                subCategory.setLabel(category1.getName());//二级标签
                subCategory.setId(category1.getId());
                List<SubCategory> ThiCategoryList=new ArrayList<>();;

                List<Category> allThiCategory=categoryMapper.getAllThiCategory(category1.getId());

                for(Category category2:allThiCategory){
                    SubCategory subCategory1=new SubCategory();
                    subCategory1.setLabel(category2.getName());
                    subCategory1.setId(category2.getId());
                    ThiCategoryList.add(subCategory1);
                }
                subCategory.setChildren(ThiCategoryList);
                SubCategoryList.add(subCategory);
            }
            priCategory.setChildren(SubCategoryList);
            categoryInfo.add(priCategory);
        }
        return categoryInfo;
    }
}
