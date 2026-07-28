package com.dyj.idle.controller;

import com.dyj.idle.common.R;
import com.dyj.idle.entity.FeedbackDTO;
import com.dyj.idle.entity.MyGoods;
import com.dyj.idle.entity.OrderInfo;
import com.dyj.idle.entity.User;
import com.dyj.idle.service.GoodsService;
import com.dyj.idle.service.OrderService;
import com.dyj.idle.service.OssService;
import com.dyj.idle.service.UserService;
import com.dyj.idle.utils.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Tag(name = "用户管理")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private OssService ossService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    //登录接口
    @PostMapping("/login")
    public R<User> login(@RequestParam String account, @RequestParam String password) {
        //验证账号密码是否正确
        User userInfo = userService.getUserInfoByLogin(account, password);
        if(userInfo == null){
            return R.error("用户名或密码错误");
        }
        if(userInfo.getIsForbidden()==1) {
            return R.error("用户被封禁");
        }
        if (userInfo != null) {
            System.out.println("欢迎用户："+userInfo.getAccount()+"登录");
            System.out.println(userInfo);
            String accessToken=JwtUtil.generateToken(userInfo.getId(),1000*10*100);//10s
            String refreshToken=JwtUtil.generateToken(userInfo.getId(),1000*60*60*100);//20s
            return R.success(userInfo).add("accessToken",accessToken).add("refreshToken",refreshToken);
        }


        return R.error("账号或密码错误");
    }

    @GetMapping("/newToken")
    public R<String> getNewToken(HttpServletRequest request,Long id){
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return R.error("refreshToken失效");
        }
        System.out.println(token.substring(7)+"dyjdyj");
        try {
            if(JwtUtil.isTokenValid(token.substring(7),id)){
                System.out.println(new Date()+":refreshToken失效");
                return R.error("refreshToken失效");
            }
        }catch (Exception e){
            return R.error("refreshToken失效");
        }

        String accessToken=JwtUtil.generateToken(id,1000*10);//10s
        String refreshToken=JwtUtil.generateToken(id,1000*60*60);//20s

        return R.success("成功重新刷新token").add("accessToken",accessToken).add("refreshToken",refreshToken);
    }

    //得到新的用户数据
    @GetMapping("/getUserInfo")
    public R<User> getUserInfoById(HttpServletRequest request,@RequestParam("id") Long id){
        String token = request.getHeader("Authorization");

        Long userId=JwtUtil.extractId(token.substring(7));

        if(!userId.equals(id)){
            return R.error("refreshToken失效");
        }
        User userInfoByToken = userService.getUserInfoById(id);
        return R.success(userInfoByToken);
    }
    //验证邮箱
    @GetMapping("/verifyEmail")
    public R<String> verifyEmail(@RequestParam String Email, @RequestParam("tag") Integer tag) {
        System.out.println("收到了邮箱验证的请求");
        User user=userService.verifyEmailIsExist(Email);
        if(tag==0){//找邮箱是否注册
            if(user!=null){
                return R.error("邮箱已经注册");
            }else{
                //发送验证码，并且保存验证码的有效时间
                //使用线程去处理
                new Thread(()->{
                    String verifyCode = null;//发送邮件
                    try {
                        verifyCode = MailSend.sendEmail(Email);
                        //使用redis储存验证码，设置有效时间
                        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();//设置键值对
                        operations.set(Email+"的验证码：",verifyCode,300, TimeUnit.SECONDS);//五分钟后过期
                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
                return R.success("验证码发送成功");
            }
        }else {//找邮箱是否没注册
            if(user==null){
                return R.error("邮箱没有注册");
            }else{
                return R.success("验证码发送成功");
            }
        }
    }

    //用户注册
    @PostMapping("/register")
    public R<String> register(@RequestParam String mailbox,@RequestParam String verifyCode,@RequestParam String password){
        System.out.println("收到注册请求");

        //先看看验证码有没有过期
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();//设置键值对
        String s = operations.get(mailbox + "的验证码：");
        if(!s.equals(verifyCode)){
            return R.error("验证码错误或者已经过期!");
        }
//        //用户名是否重复
//        User user = userService.getUserInfoByAccount(account);
//        if(user!=null){
//            return R.error("用户名重复，请换一个！");
//        }
        IdWorker idWorker=new IdWorker(0,0);
        try {
            userService.insert(idWorker.nextId(),mailbox, UniqueUserNameGenerator.generate(),password);//插入用户数据
        }catch (Exception e){
            R.error("后端用户数据插入错误");
        }
        return R.success("注册成功");
    }
    //保存用户信息
    @PostMapping("/save")
    public R<String> save(HttpServletRequest request,@RequestParam("sex") Integer sex,@DateTimeFormat(pattern = "yyyy-MM-dd") Date birthday,
                          @RequestParam("introduction") String introduction,@RequestParam("nickname") String nickname,
                          @RequestParam("phone") String phone){
        String token = request.getHeader("Authorization");
        System.out.println(birthday);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(birthday);
        System.out.println(formattedDate);
        Long userId=JwtUtil.extractId(token.substring(7));
        userService.saveUserInfo(sex,formattedDate,introduction,userId,nickname,phone);
        return R.success("成功收到");
    }
    //删除oos服务器里的图片
    @GetMapping("deleteImage")
    public R<String> deleteImage(@RequestParam("imageUrl") String imageUrl){
        System.out.println("收到删除图片的请求");
        try {
            ossService.deleteImage(UploadTag.extractFileName(imageUrl));
        }catch (Exception e){
            return R.error("上传错误");
        }
        return R.success("删除成功");
    }

    @GetMapping("changeImage")
    public R<String> changeImage(@RequestParam("newImageUrl") String imageUrl,@RequestParam("userId") Long userId){
        System.out.println("收到更换用户头像的请求");
        try{
            userService.changeImage(imageUrl,userId);
        }catch (Exception e){
            return R.error("新头像保存失败");
        }
        return R.success("新头像保存成功");
    }

    @GetMapping("getMyGoods")
    public R<List<MyGoods>> getMyGoods(@RequestParam("userId") Long userId,@RequestParam("tag") int tag){
         try{
             List<MyGoods> myGoodsByTag = goodsService.getMyGoodsByTag(userId, tag);
             return R.success(myGoodsByTag);
         }catch (Exception e){
             return R.error("获取错误");
         }
    }

    @GetMapping("getOtherUser")
    public R<User> getOtherUser(@RequestParam("userId") Long userId){
        try{
            User userInfoById = userService.getUserInfoById(userId);
            return R.success(userInfoById);
        }catch (Exception e){
            return R.error("获取错误");
        }
    }

    @GetMapping("changeAttention")
    public R<String> changeAttention(@RequestParam("userId") Long userId,
                                     @RequestParam("fansId") Long fansId,
                                     @RequestParam("tag") int tag){
        try{
            userService.changeAttention(userId,fansId,tag);
            return R.success("成功");
        }catch (Exception e){
            return R.error("获取错误");
        }
    }

    @GetMapping("checkAttention")
    public R<Boolean> checkAttention(@RequestParam("userId") Long userId,
                                     @RequestParam("fansId") Long fansId){
        try{
            boolean b = userService.checkAttention(userId, fansId);
            return R.success(b);
        }catch (Exception e){
            return R.error("获取错误");
        }
    }

    @GetMapping("getCount")
    public R<String> getCount(@RequestParam("userId") Long userId){
        try{
            Integer attentionNum = userService.getAttentionNum(userId);
            Integer fansNum = userService.getFansNum(userId);
            return R.success("成功得到").add("attention",attentionNum).add("fans",fansNum);
        }catch (Exception e){
            return R.error("获取错误");
        }
    }

    @GetMapping("getAllOrder")
    public R<List<OrderInfo>> getAllOrder(@RequestParam("userId") Long userId){
        try{
            List<OrderInfo> allOrder = orderService.getAllOrder(userId);
            return R.success(allOrder);
        }catch (Exception e){
            return R.error("获取订单信息错误");
        }
    }

    @GetMapping("getMyCollect")
    public R<List<MyGoods>> getMyCollect(@RequestParam("userId") Long userId){
        try{
            List<MyGoods> myCollect = goodsService.getMyCollect(userId);
            return R.success(myCollect);
        }catch (Exception e){
            return R.error("获取订单信息错误");
        }
    }

    @Operation(summary = "提交用户反馈")
    @PostMapping("/feedback")
    public R<String> feedback(@RequestBody FeedbackDTO dto) {
        try {
            userService.saveFeedback(dto);
            return R.success("反馈成功");
        } catch (Exception e) {
            return R.error("反馈失败");
        }
    }


}
