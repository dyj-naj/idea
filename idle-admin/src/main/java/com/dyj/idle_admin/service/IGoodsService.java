package com.dyj.idle_admin.service;

import com.dyj.idle_admin.common.PageDTO;
import com.dyj.idle_admin.common.ResultData;
import com.dyj.idle_admin.domain.dto.GoodsCategoryAddDTO;
import com.dyj.idle_admin.domain.dto.GoodsQualityAddDTO;
import com.dyj.idle_admin.domain.po.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dyj.idle_admin.domain.query.GoodsPageQuery;
import com.dyj.idle_admin.domain.vo.GoodsFirstCategory;
import com.dyj.idle_admin.domain.vo.GoodsVO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dyj
 * @since 2025-05-18
 */
public interface IGoodsService extends IService<Goods> {

    ResultData<PageDTO<GoodsVO>> getPages(GoodsPageQuery query);

    ResultData<String> updateStatus(Long goodsId, Integer status);

    ResultData<List<GoodsFirstCategory>> getGoodsCategory() throws JsonProcessingException;

    ResultData<String> addGoodsCategory(GoodsCategoryAddDTO goodsCategoryAddDTO);

    ResultData<String> deleteGoodsCategory(Long id, Integer type);

    ResultData<String> updateGoodsCategory(Long id, String name,Integer type);

}
