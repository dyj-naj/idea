package com.dyj.idle_admin.service;

import com.dyj.idle_admin.common.PageDTO;
import com.dyj.idle_admin.common.ResultData;
import com.dyj.idle_admin.domain.dto.GoodsQualityAddDTO;
import com.dyj.idle_admin.domain.po.GoodsQuality;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dyj
 * @since 2025-05-19
 */
public interface IGoodsQualityService extends IService<GoodsQuality> {

    ResultData<String> addGoodsQuality(GoodsQualityAddDTO goodsQualityAddDTO);

    ResultData<String> updateGoodsQuality(GoodsQualityAddDTO dto);

    ResultData<String> deleteGoodsQuality(Long id);

    ResultData<PageDTO<GoodsQuality>> getGoodsQualityList(Integer pageNum, Integer pageSize);
}
