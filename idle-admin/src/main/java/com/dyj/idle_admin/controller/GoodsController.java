package com.dyj.idle_admin.controller;


import com.dyj.idle_admin.common.ResultData;
import com.dyj.idle_admin.domain.dto.GoodsCategoryAddDTO;
import com.dyj.idle_admin.domain.dto.GoodsQualityAddDTO;
import com.dyj.idle_admin.domain.dto.GoodsTransportAddDTO;
import com.dyj.idle_admin.domain.query.GoodsPageQuery;
import com.dyj.idle_admin.service.IGoodsQualityService;
import com.dyj.idle_admin.service.IGoodsService;
import com.dyj.idle_admin.service.IGoodsTransportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dyj
 * @since 2025-05-18
 */
@RestController
@RequestMapping("/goods")
@Tag(name = "商品管理")
@RequiredArgsConstructor
public class GoodsController {

    private final IGoodsService goodsService;

    private final IGoodsQualityService goodsQualityService;

    private final IGoodsTransportService goodsTransportService;



    /**
     * 分页查询
     * @param query
     * @return
     */
    @Operation(summary = "分页查询商品")
    @PostMapping("/list")
    public ResultData list(@RequestBody GoodsPageQuery query) {
        return goodsService.getPages(query);
    }

    /**
     * 修改商品的状态
     * @param goodsId
     * @param status
     * @return
     */
    @Operation(summary = "修改商品状态")
    @GetMapping("/updateStatus")
    public ResultData updateGoodsStatus(@RequestParam("goodsId") Long goodsId, @RequestParam("status") Integer status){
        return goodsService.updateStatus(goodsId, status);
    }

    @Operation(summary = "获取商品分类")
    @GetMapping("/getGoodsCategory")
    public ResultData getGoodsCategory() throws JsonProcessingException {
        return goodsService.getGoodsCategory();
    }

    @Operation(summary = "添加商品分类")
    @PostMapping("/addGoodsCategory")
    public ResultData addGoodsCategory(@RequestBody GoodsCategoryAddDTO goodsCategoryAddDTO){
        return goodsService.addGoodsCategory(goodsCategoryAddDTO);
    }
    @Operation(summary = "删除商品分类")
    @GetMapping("/deleteGoodsCategory")
    public ResultData deleteGoodsCategory(@RequestParam("id") Long id,@RequestParam("type") Integer type){
        return goodsService.deleteGoodsCategory(id,type);
    }


    @Operation(summary = "更新商品分类")
    @PostMapping("/updateGoodsCategory")
    public ResultData updateGoodsCategory(@RequestParam("id") Long id,
                                          @RequestParam("name")String name,
                                          @RequestParam("type") Integer type){
        return goodsService.updateGoodsCategory(id,name,type);
    }

    @Operation(summary = "查询商品成色")
    @GetMapping("/getGoodsQualityList")
    public ResultData getGoodsQuality(@RequestParam("pageNum") Integer pageNum,@RequestParam("pageSize") Integer pageSize){
        return goodsQualityService.getGoodsQualityList(pageNum,pageSize);
    }


    @Operation(summary = "添加商品成色")
    @PostMapping("/addGoodsQuality")
    public ResultData addGoodsQuality(@RequestBody GoodsQualityAddDTO goodsQualityAddDTO){
        return goodsQualityService.addGoodsQuality(goodsQualityAddDTO);
    }

    @Operation(summary = "修改商品成色")
    @PostMapping("/updateGoodsQuality")
    public ResultData updateGoodsQuality(@RequestBody GoodsQualityAddDTO dto){
        return goodsQualityService.updateGoodsQuality(dto);
    }

    @Operation(summary = "删除商品成色")
    @PostMapping("/deleteGoodsQuality")
    public ResultData deleteGoodsQuality(@RequestParam("id") Long id){
        return goodsQualityService.deleteGoodsQuality(id);
    }


    @Operation(summary = "获取商品运输")
    @GetMapping("/getGoodsTransport")
    public ResultData getGoodsTransport(@RequestParam("pageNum") Integer pageNum,
                                        @RequestParam("pageSize") Integer pageSize){
        return goodsTransportService.getGoodsTransport(pageNum,pageSize);
    }

    @Operation(summary = "添加商品运输")
    @PostMapping("/addGoodsTransport")
    public ResultData addGoodsTransport(@RequestBody GoodsTransportAddDTO dto){
        return goodsTransportService.addGoodsTransport(dto);
    }

    @Operation(summary = "修改商品运输")
    @PostMapping("/updateGoodsTransport")
    public ResultData updateGoodsTransport(@RequestBody GoodsTransportAddDTO dto){
        return goodsTransportService.updateGoodsTransport(dto);
    }

    @Operation(summary = "删除商品运输")
    @GetMapping("/deleteGoodsTransport")
    public ResultData deleteGoodsTransport(Long id){
        return goodsTransportService.deleteGoodsTransport(id);
    }

}
