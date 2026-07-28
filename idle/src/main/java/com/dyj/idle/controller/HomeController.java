package com.dyj.idle.controller;

import cn.hutool.json.JSONObject;
import com.dyj.idle.common.R;
import com.dyj.idle.entity.*;
import com.dyj.idle.service.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private BannerService bannerService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private SearchService searchService;

    private Map<Long, Boolean> judge=new HashMap<>();

    private static final String CACHE_KEY = "物品分类";
    private static final long EXPIRATION_TIME = 30; // 缓存过期时间（分钟）
    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON处理器


    //获得轮播图数据
    @GetMapping("/banner")
    public R<List<Banner>> getBanner(){
        try{

            List<Banner> bannerList=bannerService.getBanner();
            return R.success(bannerList);
        }catch (Exception e){
            return R.error("轮播图数据获取错误");
        }
    }
    @GetMapping("category")
    public R<List<PriCategory>> getCategory() {
        try {
            // 1. 尝试从Redis获取缓存
            String cachedJson = stringRedisTemplate.opsForValue().get(CACHE_KEY);

            if (cachedJson != null && !cachedJson.isEmpty()) {
                // 2. 如果缓存存在则直接返回
                List<PriCategory> cachedList = objectMapper.readValue(cachedJson, new TypeReference<List<PriCategory>>() {});
                return R.success(cachedList);
            }

            // 3. 缓存不存在则查询数据库
            List<PriCategory> allCategory = categoryService.getAllCategory();

            // 4. 将查询结果序列化为JSON并存入Redis
            String jsonData = objectMapper.writeValueAsString(allCategory);
            stringRedisTemplate.opsForValue().set(
                    CACHE_KEY,
                    jsonData,
                    EXPIRATION_TIME,
                    TimeUnit.MINUTES
            );

            return R.success(allCategory);
        } catch (Exception e) {
            // 异常处理（建议添加日志记录）
            e.printStackTrace();
            return R.error("分类数据获取错误");
        }
    }

    //得到商品信息，分页查询
    @GetMapping("goods")
    public R<List<GoodsInfo>> getGoodsList(int page,int pageSize,Long categoryId){
        System.out.println("正在查找商品");
        try{
            List<GoodsInfo> goodsInfoList = goodsService.getGoodsInfoList(categoryId,(page-1)*pageSize,pageSize);//分页查询
            return R.success(goodsInfoList);
        }catch (Exception e){
            return R.error("无法查找到商品信息");
        }
    }

    //得到秒杀的信息
    @GetMapping("seckill")
    public R<SecKillGoods> getSecKillGoods(Long id){
        try{
            //添加商品预热
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();//设置键值对
            SecKillGoods secKillGoods = goodsService.getSecKillGoods(id);
            String s = operations.get("秒杀活动" + id + "的库存：");
            if(s==null){
                operations.set("秒杀活动"+id+"的库存：",secKillGoods.getSecKillNum().toString());
            }
            return R.success(secKillGoods);
        }catch (Exception e){
            return R.error("得到秒杀信息错误");
        }
    }

    //抢购
    @GetMapping("/secKillBuy")
    public R<String> secKillBuy(@RequestParam("id") Long id,@RequestParam("userId") Long userId,@RequestParam("value") Integer value){
        try{
            String key = "秒杀活动" + id + "的库存："; // 你的库存键
            int amount = 1; // 需要减去的库存数量
// Lua 脚本内容
            String luaScript =
                    "local key = KEYS[1] " +
                            "local amount = tonumber(ARGV[1]) " +
                            "local stock = tonumber(redis.call('get', key)) " +
                            "if stock and stock > 0 then " +
                            "    redis.call('set', key, stock - amount) " +
                            "    return 1 " +
                            "else " +
                            "    return 0 " +
                            "end";
// 将 Lua 脚本编译为 RedisScript 对象
            RedisScript<Boolean> redisScript = new DefaultRedisScript<>(luaScript, Boolean.class);

// 执行 Lua 脚本
            Boolean isSuccess = stringRedisTemplate.execute(redisScript,
                    Collections.singletonList(key), String.valueOf(amount));
// 根据 isSuccess 的值返回响应
            if (isSuccess) {
                JSONObject jsonObject=new JSONObject();
                jsonObject.set("userId",userId);
                jsonObject.set("value",value);
                jsonObject.set("id",id);
                //发送消息队列通知数据库，商品库存减一
                rabbitTemplate.convertAndSend("seckill.queue",jsonObject.toString());
                return R.success("成功");
            } else {
                return R.success("失败");
            }
        }catch (Exception e){
            return R.error("抢购错误");
        }
    }

    //得到秒杀的信息
    @GetMapping("/getSearchHotWord")
    public R<List<SearchWord>> getSearchHotWord(String text) {
        try {
            List<SearchWord> searchWord = searchService.getSearchWord(text);
            return R.success(searchWord);

        } catch (Exception e) {
            return R.error("得到搜索信息错误");
        }
    }

    @GetMapping("/getSearchGoods")
    public R<List<GoodsInfo>> getSearchGoods(String text){
        try{
            return R.success(searchService.getSearchGoods(text));
        }catch (Exception e){
            return R.error("得到搜索信息错误");
        }
    }

    /**
     * 得到商品品质
     */
    @GetMapping("/getGoodsQuality")
    public R<List<GoodsQualityVO>> getGoodsQuality(){
        try{
            List<GoodsQualityVO> goodsQualityVOList=goodsService.getGoodsQuality();
            return R.success(goodsQualityVOList);
        }catch (Exception e){
            return R.error("无法获取商品品质信息");
        }
    }
    /**
     * 得到商品运输方式
     */
    @GetMapping("/getGoodsTransport")
    public R<List<GoodsTransportVO>> getGoodsSend(){
        try{
            List<GoodsTransportVO> sendList=goodsService.getGoodsTransport();
            return R.success(sendList);
        }catch (Exception e){
            return R.error("无法获取商品运输方式");
        }
    }

}
