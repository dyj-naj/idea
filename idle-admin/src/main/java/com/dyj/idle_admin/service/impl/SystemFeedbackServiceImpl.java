package com.dyj.idle_admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dyj.idle_admin.common.PageDTO;
import com.dyj.idle_admin.common.ResultData;
import com.dyj.idle_admin.domain.po.SystemFeedback;
import com.dyj.idle_admin.domain.query.FeedBackCondition;
import com.dyj.idle_admin.domain.query.FeedBackPageQuery;
import com.dyj.idle_admin.domain.vo.FeedBackVO;
import com.dyj.idle_admin.mapper.SystemFeedbackMapper;
import com.dyj.idle_admin.service.ISystemFeedbackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyj.idle_admin.service.IUserService;
import com.dyj.idle_admin.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dyj
 * @since 2025-05-20
 */
@Service
@RequiredArgsConstructor
public class SystemFeedbackServiceImpl extends ServiceImpl<SystemFeedbackMapper, SystemFeedback> implements ISystemFeedbackService {

    private final IUserService userService;
    @Override
    public ResultData<PageDTO<FeedBackVO>> getFeedBackList(FeedBackPageQuery query) {
        Page<SystemFeedback> page = new Page<>(query.getPageNum(), query.getPageSize());
        QueryWrapper queryWrapper=new QueryWrapper();
        FeedBackCondition condition = query.getCondition();
        if(condition!=null){
            if(condition.getUserAccount()!=null){
                queryWrapper.like("publisher_account", condition.getUserAccount());
            }
            if(condition.getStarNum()!=null){
                queryWrapper.eq("star", condition.getStarNum());
            }
            if(condition.getStatus()!=null){
                queryWrapper.eq("status", condition.getStatus());
            }
            if(condition.getStartTime()!=null&&condition.getEndTime()!=null){
                queryWrapper.between("submit_time", condition.getStartTime(), condition.getEndTime());
            }
        }
        Page<SystemFeedback> feedbackPage = this.page(page, queryWrapper);

        if(feedbackPage.getRecords()==null){
            return ResultData.Success(PageDTO.empty(feedbackPage));
        }
        List<SystemFeedback> feedbackList = feedbackPage.getRecords();
        List<FeedBackVO> feedbackVOList = BeanUtils.copyList(feedbackList, FeedBackVO.class);
        for(FeedBackVO feedbackVO:feedbackVOList){
            feedbackVO.setPublisherAvatar(userService.getById(feedbackVO.getPublisherId()).getHeadUrl());
        }
        return ResultData.Success(PageDTO.of(feedbackPage, feedbackVOList));

    }

    @Override
    public ResultData<String> responseFeedBack(Long feedbackId, String responseContent) {
        boolean b = this.lambdaUpdate()
                .set(SystemFeedback::getResponseContent, responseContent)
                .set(SystemFeedback::getResponseTime, new Date())
                .set(SystemFeedback::getStatus,1)
                .eq(SystemFeedback::getId, feedbackId)
                .update();
        if(b){
            return ResultData.Success("修改成功");
        }
        return ResultData.Error("修改失败");
    }
}
