package com.dyj.idle_admin.service;

import com.dyj.idle_admin.common.PageDTO;
import com.dyj.idle_admin.common.ResultData;
import com.dyj.idle_admin.domain.dto.GoodsTransportAddDTO;
import com.dyj.idle_admin.domain.po.GoodsTransport;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dyj
 * @since 2025-05-20
 */
public interface IGoodsTransportService extends IService<GoodsTransport> {

    ResultData<PageDTO<GoodsTransport>> getGoodsTransport(Integer pageNum, Integer pageSize);

    ResultData<String> addGoodsTransport(GoodsTransportAddDTO dto);

    ResultData<String> updateGoodsTransport(GoodsTransportAddDTO dto);

    ResultData<String> deleteGoodsTransport(Long id);
}
