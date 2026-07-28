package com.dyj.idle_admin.domain.query;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: 杜英杰
 * @CreateTime: 2025-05-18
 * @Description:
 * @Version: 1.0
 */

@Data
public class GoodsPageQuery {

    private Integer pageNum;
    private Integer pageSize;
    private Integer offset;
    private GoodsCondition condition;
}
