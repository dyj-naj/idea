import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.dyj.idle_admin.domain.po.SystemInfo;
import com.dyj.idle_admin.domain.po.User;
import com.dyj.idle_admin.idleAdminApplication;
import com.dyj.idle_admin.listener.UserReadListener;
import com.dyj.idle_admin.service.ISystemInfoService;
import com.dyj.idle_admin.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * @Author: 杜英杰
 * @CreateTime: 2025-05-14
 * @Description:
 * @Version: 1.0
 */

@SpringBootTest(classes = idleAdminApplication.class)
public class EasyExcelTest {

    @Autowired
    private IUserService userService;

    @Autowired
    ISystemInfoService systemInfoService;

    @Test
    public void testRead() throws Exception {
        File file=new File("D:/cache/edge/闲小猪用户列表.xlsx");

        InputStream is=new FileInputStream(file);

        UserReadListener listener=new UserReadListener(userService);

        EasyExcel.read(is, User.class,listener).sheet(0).headRowNumber(1).doRead();

    }

    @Test
    public void testWrite(){

        List<User> list = userService.lambdaQuery().list();

        EasyExcel.write("E:/test.xlsx",User.class).sheet("用户信息").doWrite(list);
//        List<SystemInfo> s = systemInfoService.list();
//        System.out.println(s);



    }
}
