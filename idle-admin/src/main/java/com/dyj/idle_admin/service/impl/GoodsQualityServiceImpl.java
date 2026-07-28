package com.dyj.idle_admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dyj.idle_admin.common.PageDTO;
import com.dyj.idle_admin.common.ResultData;
import com.dyj.idle_admin.domain.dto.GoodsQualityAddDTO;
import com.dyj.idle_admin.domain.po.GoodsQuality;
import com.dyj.idle_admin.mapper.GoodsQualityMapper;
import com.dyj.idle_admin.service.IGoodsQualityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyj.idle_admin.utils.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dyj
 * @since 2025-05-19
 */
@Service
public class GoodsQualityServiceImpl extends ServiceImpl<GoodsQualityMapper, GoodsQuality> implements IGoodsQualityService {

    @Override
    public ResultData<String> addGoodsQuality(GoodsQualityAddDTO goodsQualityAddDTO) {
        GoodsQuality goodsQuality = BeanUtils.copyBean(goodsQualityAddDTO, GoodsQuality.class);
        goodsQuality.setCreateTime(new Date());
        this.save(goodsQuality);
        return ResultData.Success("添加成功");
    }

    @Override
    public ResultData<String> updateGoodsQuality(GoodsQualityAddDTO dto) {
        GoodsQuality goodsQuality = BeanUtils.copyBean(dto, GoodsQuality.class);
        this.updateById(goodsQuality);
        return ResultData.Success("修改成功");
    }

    @Override
    public ResultData<String> deleteGoodsQuality(Long id) {
        boolean delete = this.removeById(id);
        if(delete){
            return ResultData.Success("删除成功");
        }else{
            return ResultData.Error("删除失败");
        }
    }

    @Override
    public ResultData<PageDTO<GoodsQuality>> getGoodsQualityList(Integer pageNum, Integer pageSize) {
        Page<GoodsQuality> page = new Page<>(pageNum,pageSize);
        Page<GoodsQuality> goodsQualityPage = this.page(page);
        if(goodsQualityPage.getRecords()==null){
            return ResultData.Success(PageDTO.empty(goodsQualityPage));
        }
        return ResultData.Success(PageDTO.of(goodsQualityPage));
    }

}
