package com.dyj.idle_admin.service.impl;


import com.dyj.idle_admin.common.PageDTO;
import com.dyj.idle_admin.common.ResultData;
import com.dyj.idle_admin.domain.dto.GoodsCategoryAddDTO;
import com.dyj.idle_admin.domain.po.Goods;
import com.dyj.idle_admin.domain.po.User;
import com.dyj.idle_admin.domain.query.GoodsCondition;
import com.dyj.idle_admin.domain.query.GoodsPageQuery;
import com.dyj.idle_admin.domain.vo.GoodsFirstCategory;
import com.dyj.idle_admin.domain.vo.GoodsSecondCategory;
import com.dyj.idle_admin.domain.vo.GoodsThirdCategory;
import com.dyj.idle_admin.domain.vo.GoodsVO;
import com.dyj.idle_admin.mapper.GoodsMapper;
import com.dyj.idle_admin.service.IGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyj.idle_admin.utils.BeanUtils;
import com.dyj.idle_admin.utils.IdWorker;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dyj
 * @since 2025-05-18
 */
@Service
@RequiredArgsConstructor
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {



    private final StringRedisTemplate stringRedisTemplate;

    private static final String CATEGORY_KEY = "管理端物品分类";
    private static final long EXPIRATION_TIME = 30; // 缓存过期时间（分钟）
    private final ObjectMapper objectMapper; // JSON处理器

    @Override
    public ResultData<PageDTO<GoodsVO>> getPages(GoodsPageQuery query) {
        System.out.println(query);
        // 计算分页偏移量
        query.setOffset((query.getPageNum()-1)*query.getPageSize());

        // 获取当前页数据
        List<GoodsVO> goodsList = baseMapper.getGoodsList(query);

        // 获取总记录数
        Long total = baseMapper.getGoodsCount(query);

        // 计算总页数
        Long pages = (total + query.getPageSize() - 1) / query.getPageSize();

        // 构建PageDTO对象
        PageDTO<GoodsVO> pageDTO = new PageDTO<>(total, pages, goodsList);

        return ResultData.Success(pageDTO);
    }

    @Override
    public ResultData<String> updateStatus(Long goodsId, Integer status) {
        boolean b = this.lambdaUpdate().set(Goods::getState, status).eq(Goods::getId, goodsId).update();
        if(b){
            return ResultData.Success("修改成功");
        }else{
            return ResultData.Error("修改失败");
        }
    }

    @Override
    public ResultData<List<GoodsFirstCategory>> getGoodsCategory() throws JsonProcessingException {

        // 1. 尝试从Redis获取缓存
        String cachedJson = stringRedisTemplate.opsForValue().get(CATEGORY_KEY);
        if (cachedJson != null && !cachedJson.isEmpty()) {
            // 2. 如果缓存存在则直接返回
            List<GoodsFirstCategory> cachedList = objectMapper.readValue(cachedJson, new TypeReference<List<GoodsFirstCategory>>() {});
            return ResultData.Success(cachedList);
        }

        //找到所有一级分类
        List<GoodsFirstCategory> allCategory = baseMapper.getAllPriCategory();
        //找到每个一级分类的二级分类
        for(GoodsFirstCategory fCategory:allCategory){
            List<GoodsSecondCategory> secondCategory=baseMapper.getAllSecCategory(fCategory.getId());
            for(GoodsSecondCategory sCategory:secondCategory){
                //找到每个二级分类的三级分类
                List<GoodsThirdCategory> allThiCategory = baseMapper.getAllThiCategory(sCategory.getId());
                sCategory.setChildren(allThiCategory);
            }
            fCategory.setChildren(secondCategory);
        }

        // 4. 将查询结果序列化为JSON并存入Redis
        String jsonData = objectMapper.writeValueAsString(allCategory);
        stringRedisTemplate.opsForValue().set(
                CATEGORY_KEY,
                jsonData,
                EXPIRATION_TIME,
                TimeUnit.MINUTES
        );

        return ResultData.Success(allCategory);
    }

    @Override
    public ResultData<String> addGoodsCategory(GoodsCategoryAddDTO dto) {

        IdWorker idWorker=new IdWorker(0,0);
        if(dto.getType()==1){
            baseMapper.addPriCategory(idWorker.nextId(),dto.getName());
        }else if (dto.getType()==2){
            baseMapper.addSecCategory("seccategory",idWorker.nextId(),dto.getFaId(),dto.getName());
        }else{
            baseMapper.addThiCategory("thicategory",idWorker.nextId(),dto.getFaId(),dto.getName());
        }
        return ResultData.Success("添加成功");
    }




    @Override
    public ResultData<String> deleteGoodsCategory(Long id, Integer type) {
        //删除商品的分类

            if(type==1){
                baseMapper.deletePriCategory(id);
            }else if(type==2){
                baseMapper.deleteSecCategory(id);
            }else{
                baseMapper.deleteThiCategory(id);
            }
            return ResultData.Success("删除成功");

    }

    @Override
    public ResultData<String> updateGoodsCategory(Long id, String name, Integer type) {
        try{
            if (type==1)
                baseMapper.updatePriCategory(id,name);
            else if (type==2)
                baseMapper.updateSecCategory(id,name);
            else
                baseMapper.updateThiCategory(id,name);

            return ResultData.Success("修改成功");
        }catch (Exception e){
            return ResultData.Error("修改失败");
        }
    }
}
