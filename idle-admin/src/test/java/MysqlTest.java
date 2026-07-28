import com.dyj.idle_admin.utils.UniqueUserNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.activation.DataSource;

/**
 * @Author: 杜英杰
 * @CreateTime: 2025-05-08
 * @Description:
 * @Version: 1.0
 */


public class MysqlTest {

    @Test
    public void initTest(){

        for(int i=0;i<5;i++)
        System.out.println(UniqueUserNameGenerator.generate());
        System.out.println("initTest");
    }

    
}
