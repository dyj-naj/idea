package com.dyj.idle;

import com.dyj.idle.entity.*;
import com.dyj.idle.mapper.CategoryMapper;
import com.dyj.idle.mapper.ChatMapper;
import com.dyj.idle.mapper.GoodsMapper;
import com.dyj.idle.mapper.UserMapper;
import com.dyj.idle.service.*;
import com.dyj.idle.utils.IdWorker;
import com.dyj.idle.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class mySqlTest {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ChatMapper chatMapper;
    @Autowired
    private ChatService chatService;

    @Autowired
    private OrderService orderService;
    @Test
    void test(){
        String input = "住房出租\n" +
                "二手房源\n" +
                "新房\n" +
                "转租\n" +
                "合租\n" +
                "整租\n" +
                "车位\n" +
                "办公\n" +
                "仓库\n" +
                "商铺";
        // 使用正则表达式分割字符串，匹配回车和空格
        String[] items = input.split("\\s*\\n\\s*");

        List<String> list=new ArrayList<>();
        IdWorker idWorker=new IdWorker(0,0);
        // 打印结果
        for (String item : items) {
            System.out.println(item);

            categoryMapper.test(idWorker.nextId(),583815969898496000l,item);
        }

//
//
//

//        for(String name:list){
//
//        }
//        userService.changeCoin(580707747033513984l,-100);
//        System.out.println("100人抢购活动开始了");
//        int sum=0;
//        for(int i=1;i<=120;i++){
//            Integer integer = goodsMapper.subStock(584209613025443840l);
//            if(integer==1){
//                sum++;
//                System.out.println("第"+i+"个用户抢到了");
//            }else {
//                System.out.println("第"+i+"个用户没抢到");
//            }
//        }
//        System.out.println("最终有"+sum+"个抢到了");

//        orderService.getOrderDetail(590986343274774528l)
//        orderService.insertOrder(584209613025443840l,578358967332244000l,12,1.22,"kk","胜地","sdjia");
//        GoodsDetail goodsById = goodsMapper.getGoodsById(588822084927356928l);
//        System.out.println(goodsById);

//        goodsMapper.insertGoodsHot(588822084927356928l);
//        IdWorker idWorker=new IdWorker(0,0);
//        chatMapper.insertChatWindow(chatWindow,586253002721984512l,580707747033513984l,580707747033513984l);
//        Long chatWindow = chatService.getChatWindow(586253002721984512l, 580707747033513984l, 580707747033513984l);
//        System.out.println(chatWindow);

//        List<Message> allMessage = chatMapper.getAllMessage(588118434600976384l);
//        allMessage.forEach(e-> System.out.println(e));
//        User test = chatMapper.test();
//        List<ChatWindow> allChatWindow = chatMapper.getAllChatWindow(578358967332244000l);
//        allChatWindow.forEach(e-> System.out.println(e));

//        userMapper.insCollect(578358967332244000l,584209613025443840l);
//        boolean collect = userService.isCollect(578358967332244000l, 58420961302544384l);
//        System.out.println(collect);

//        List<Long> test = goodsMapper.test();
//        test.forEach(e->{
//            goodsMapper.test1(e);
//        });

//        List<MyGoods> myGoodsByTag = goodsService.getMyGoodsByTag(578358967332244000l, 2);
//        myGoodsByTag.forEach(e-> System.out.println(e));


//        goodsService.insert(391298301l,578358967332244000l,"321",1.20,"ss","ll");
//        System.out.println(categoryService.getAllCategory());


//        List<PriCategory> allCategory = categoryService.getAllCategory();
//        System.out.println(allCategory);
//        List<Category> allPriCategory = categoryMapper.getAllPriCategory();//找到所有一级分类
//        List<PriCategory> categoryInfo=new ArrayList<>();//初始化容器
//        for(Category category:allPriCategory){
//            PriCategory priCategory=new PriCategory();
//            priCategory.setPriName(category.getName());//一级标签
//            List<SubCategory> SubCategoryList=new ArrayList<>();;
//
//            List<Category> allSecCategory = categoryMapper.getAllSecCategory(category.getId());//查询对应的子类
//            for(Category category1:allSecCategory){
//                SubCategory subCategory=new SubCategory();
//                subCategory.setSecName(category1.getName());//二级标签
//                List<String> ThiCategoryList=new ArrayList<>();;
//
//                List<Category> allThiCategory=categoryMapper.getAllThiCategory(category1.getId());
//
//                for(Category category2:allThiCategory){
//                    ThiCategoryList.add(category2.getName());
//                }
//                subCategory.setListThiCategory(ThiCategoryList);
//                SubCategoryList.add(subCategory);
//            }
//            priCategory.setListSecCategory(SubCategoryList);
//            categoryInfo.add(priCategory);
//        }
//
//        System.out.println(categoryInfo);

    }

}
