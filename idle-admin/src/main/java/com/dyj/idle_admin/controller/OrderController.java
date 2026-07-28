package com.dyj.idle_admin.controller;


import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dyj.idle_admin.common.ResultData;
import com.dyj.idle_admin.domain.query.OrderPageQuery;
import com.dyj.idle_admin.domain.vo.OrderCountVO;
import com.dyj.idle_admin.service.IOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dyj
 * @since 2025-05-20
 */
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Tag(name = "订单管理")
public class OrderController {
    private final IOrderService orderService;

    @Operation(summary = "得到订单各状态数量")
    @GetMapping("/getOrderCount")
    public ResultData getOrderCount(){
        return orderService.getOrderCount();
    }

    @Operation(summary = "分页查询订单")
    @PostMapping("/list")
    public ResultData page(@RequestBody OrderPageQuery query) {
        return orderService.getOrderPage(query);
    }

    @Operation(summary = "更新订单状态")
    @PostMapping("/updateStatus")
    public ResultData updateOrderStatus(@RequestParam("orderId") Long orderId, @RequestParam("status") Integer status) {
        return orderService.updateOrderStatus(orderId, status);
    }

}
