package com.dyj.idle_admin.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.dyj.idle_admin.domain.po.User;
import com.dyj.idle_admin.service.IUserService;
import org.springframework.dao.DuplicateKeyException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: 杜英杰
 * @CreateTime: 2025-05-14
 * @Description:
 * @Version: 1.0
 */

public class UserReadListener implements ReadListener<User> {

    List<User> list=new ArrayList<>();

    private Integer SuccessCount = 0;

    private Integer FailedCount = 0;

    private IUserService service;
    public UserReadListener(IUserService service){
        this.service = service;
    }
    public UserReadListener(){

    }

    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        System.out.println("Excel 标题行: " + headMap.values());
    }

    @Override
    public void invoke(User user, AnalysisContext context) {
        if (user == null) {
            System.err.println("警告：接收到 null 对象");
            return;
        }
        System.out.println("成功读取: " + user);
        list.add(user);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("读取完成，共 " + list.size() + " 条数据");
        for (User user : list) {
            try {
                service.save(user);
                SuccessCount++;
            } catch (DuplicateKeyException e) {
                System.out.println("主键或唯一约束冲突，跳过记录: "+user.getAccount());
                FailedCount++;
            }
        }
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) {
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException ex = (ExcelDataConvertException) exception;
            System.err.printf("转换失败：第%d行第%d列，数据=%s%n",
                    ex.getRowIndex() + 1, ex.getColumnIndex() + 1, ex.getCellData());
            FailedCount++;
        }
    }

    public List<User> getList() {
        return list;
    }

    public Integer getSuccessCount() {
        return SuccessCount;
    }

    public Integer getFailedCount() {
        return FailedCount;
    }
}
