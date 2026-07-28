package com.dyj.idle_admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dyj.idle_admin.common.ResultData;
import com.dyj.idle_admin.domain.po.SystemFeedback;
import com.dyj.idle_admin.domain.query.FeedBackPageQuery;
import com.dyj.idle_admin.service.ISystemFeedbackService;
import com.dyj.idle_admin.service.ISystemInfoService;
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
@RequestMapping("/feedback")
@Tag(name = "反馈管理")
@RequiredArgsConstructor
public class SystemFeedbackController {

    private final ISystemFeedbackService  systemFeedbackService;

    @Operation(summary = "分页获取反馈")
    @PostMapping("/list")
    public ResultData getFeedBackList(@RequestBody FeedBackPageQuery query) {
        return systemFeedbackService.getFeedBackList(query);
    }

    @Operation(summary = "管理员回复反馈")
    @PostMapping("/response")
    public ResultData responseFeedBack(@RequestParam("feedbackId") Long feedbackId,
                                       @RequestParam("responseContent") String responseContent) {
        return systemFeedbackService.responseFeedBack(feedbackId,responseContent);
    }



}
