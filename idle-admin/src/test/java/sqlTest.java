import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dyj.idle_admin.common.PageDTO;
import com.dyj.idle_admin.common.ResultData;
import com.dyj.idle_admin.domain.po.Goods;
import com.dyj.idle_admin.domain.query.GoodsCondition;
import com.dyj.idle_admin.domain.query.GoodsPageQuery;
import com.dyj.idle_admin.domain.vo.GoodsVO;
import com.dyj.idle_admin.enums.SortStatus;
import com.dyj.idle_admin.idleAdminApplication;
import com.dyj.idle_admin.mapper.GoodsMapper;
import com.dyj.idle_admin.service.IGoodsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: 杜英杰
 * @CreateTime: 2025-05-18
 * @Description:
 * @Version: 1.0
 */
@SpringBootTest(classes = idleAdminApplication.class)
public class sqlTest {

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private GoodsMapper goodsMapper;

    @Test
    public void test() {
        GoodsPageQuery query = new GoodsPageQuery();
        query.setPageSize(10);
        query.setPageNum(1);
        query.setOffset((query.getPageNum() - 1) * query.getPageSize());

        ResultData<PageDTO<GoodsVO>> pages = goodsService.getPages(query);
        System.out.println(pages);


    }

}
