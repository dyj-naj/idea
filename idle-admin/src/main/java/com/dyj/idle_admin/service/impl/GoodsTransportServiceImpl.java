package com.dyj.idle_admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dyj.idle_admin.common.PageDTO;
import com.dyj.idle_admin.common.ResultData;
import com.dyj.idle_admin.domain.dto.GoodsTransportAddDTO;
import com.dyj.idle_admin.domain.po.GoodsTransport;
import com.dyj.idle_admin.mapper.GoodsTransportMapper;
import com.dyj.idle_admin.service.IGoodsTransportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyj.idle_admin.utils.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dyj
 * @since 2025-05-20
 */
@Service
public class GoodsTransportServiceImpl extends ServiceImpl<GoodsTransportMapper, GoodsTransport> implements IGoodsTransportService {

    @Override
    public ResultData<PageDTO<GoodsTransport>> getGoodsTransport(Integer pageNum, Integer pageSize) {
        Page<GoodsTransport> page = new Page<>(pageNum, pageSize);
        Page<GoodsTransport> goodsTransportPage = this.page(page);
        if(goodsTransportPage.getRecords()==null){
            return ResultData.Success(PageDTO.empty(goodsTransportPage));
        }
        return ResultData.Success(PageDTO.of(goodsTransportPage));
    }

    @Override
    public ResultData<String> addGoodsTransport(GoodsTransportAddDTO dto) {
        GoodsTransport goodsTransport = BeanUtils.copyBean(dto, GoodsTransport.class);
        this.save(goodsTransport);
        return ResultData.Success("添加成功");
    }

    @Override
    public ResultData<String> updateGoodsTransport(GoodsTransportAddDTO dto) {
        GoodsTransport goodsTransport = BeanUtils.copyBean(dto, GoodsTransport.class);
        boolean b = this.updateById(goodsTransport);
        if(b)
            return ResultData.Success("修改成功");
        else
            return ResultData.Error("修改失败");
    }

    @Override
    public ResultData<String> deleteGoodsTransport(Long id) {
        if(id != null){
            boolean b = this.removeById(id);
            if(b)
                return ResultData.Success("删除成功");
            else
                return ResultData.Error("删除失败");
        }else{
            return ResultData.Error("id为空，删除异常");
        }
    }
}
