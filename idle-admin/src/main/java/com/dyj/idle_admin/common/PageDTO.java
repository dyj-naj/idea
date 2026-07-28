package com.dyj.idle_admin.common;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dyj.idle_admin.utils.BeanUtils;
import com.dyj.idle_admin.utils.CollUtils;
import com.dyj.idle_admin.utils.Convert;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO<T> {
    // 总数
    protected Long total = 0L;
    // 当前页
    protected Long pages = 0L;
    // 数据
    protected List<T> list = null;

    // 根据参数 生成空list数据
    public static <T> PageDTO<T> empty(Long total, Long pages) {
        return new PageDTO<>(total, pages, CollUtils.emptyList());
    }
    // 根据page 生成空list数据
    public static <T> PageDTO<T> empty(Page<?> page) {
        return new PageDTO<>(page.getTotal(), page.getPages(), CollUtils.emptyList());
    }

    // 将page转换为PageDTO
    public static <T> PageDTO<T> of(Page<T> page) {
        if(page == null){
            return new PageDTO<>();
        }
        if (CollUtils.isEmpty(page.getRecords())) {
            return empty(page);
        }
        return new PageDTO<>(page.getTotal(), page.getPages(), page.getRecords());
    }

    // 将page转换为PageDTO 根据mapper转换
    public static <T,R> PageDTO<T> of(Page<R> page, Function<R, T> mapper) {
        if(page == null){
            return new PageDTO<>();
        }
        if (CollUtils.isEmpty(page.getRecords())) {
            return empty(page);
        }
        return new PageDTO<>(page.getTotal(), page.getPages(),
                page.getRecords().stream().map(mapper).collect(Collectors.toList()));
    }

    // 将page转换为PageDTO 数据有由list提供
    public static <T> PageDTO<T> of(Page<?> page, List<T> list) {
        return new PageDTO<>(page.getTotal(), page.getPages(), list);
    }

    // 将page转换为PageDTO  根据page.getRecords() 转换为对应clazz的list
    public static <T, R> PageDTO<T> of(Page<R> page, Class<T> clazz) {
        return new PageDTO<>(page.getTotal(), page.getPages(), BeanUtils.copyList(page.getRecords(), clazz));
    }

    // 将page转换为PageDTO  根据page.getRecords() 由convert方法 转换为对应clazz的list
    public static <T, R> PageDTO<T> of(Page<R> page, Class<T> clazz, Convert<R, T> convert) {
        return new PageDTO<>(page.getTotal(), page.getPages(), BeanUtils.copyList(page.getRecords(), clazz, convert));
    }
}
