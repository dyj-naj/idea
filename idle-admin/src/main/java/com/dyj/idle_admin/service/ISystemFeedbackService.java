package com.dyj.idle_admin.service;

import com.dyj.idle_admin.common.PageDTO;
import com.dyj.idle_admin.common.ResultData;
import com.dyj.idle_admin.domain.po.SystemFeedback;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dyj.idle_admin.domain.query.FeedBackPageQuery;
import com.dyj.idle_admin.domain.vo.FeedBackVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dyj
 * @since 2025-05-20
 */
public interface ISystemFeedbackService extends IService<SystemFeedback> {

    ResultData<PageDTO<FeedBackVO>> getFeedBackList(FeedBackPageQuery query);

    ResultData<String> responseFeedBack(Long feedbackId, String responseContent);
}
