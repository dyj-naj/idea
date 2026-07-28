package com.dyj.idle.controller;


import cn.hutool.log.Log;
import com.dyj.idle.common.R;
import com.dyj.idle.entity.User;
import com.dyj.idle.mapper.GoodsMapper;
import com.dyj.idle.service.GoodsService;
import com.dyj.idle.service.OssService;
import com.dyj.idle.service.UserService;
import com.dyj.idle.utils.IdWorker;
import com.dyj.idle.utils.UploadTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private OssService ossService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private UserService userService;

    @PostMapping("/headImage")
    public R<String> uploadHeadImage(@RequestParam("fileName") String fileName,
                                     @RequestParam("userId") Long userId,
                                     @RequestParam("file") MultipartFile imageFile)  {
        System.out.println("收到用户图片上传的请求上传请求");
        if(imageFile.isEmpty()){
            return R.error("图片文件为空");
        }
        String res=null;
        try {
            String resFileName= UploadTag.getUserHeadImageMD5(userId,fileName);
            res=ossService.uploadFile(imageFile,resFileName);
            System.out.println("保存到oos的文件地址:"+res);
            //将这个路径替换用户原来的头像路径
            userService.changeImage(res,userId);
        }catch (Exception e){
            return R.error("文件上传错误");
        }

        return  R.success(res);
    }

    //发布商品
    @PostMapping("/publish")
    public R<String> publishGoods(@RequestParam("goods_seller") Long UserId,
                                  @RequestParam("goods_desc") String goods_desc,
                                  @RequestParam("goods_price") double goods_price,
                                  @RequestParam("goods_category") Long[] goods_category,
                                  @RequestParam("goods_use") String goods_use,
                                  @RequestParam("goods_send") String goods_send,
                                  @RequestParam("goods_stock") Integer goods_stock,
                                  @RequestParam("files") MultipartFile[] imageFile) throws IOException {

            IdWorker idWorker=new IdWorker(0,0);
            Long goods_id=idWorker.nextId();
            String firstPicture=null;
            //保存商品的图片
            for(int i=0;i<imageFile.length;i++){
                String fileName=UploadTag.getUserGoodsMD5(UserId);
                String s = ossService.uploadFile(imageFile[i], fileName);
                goodsService.saveGoodsImage(goods_id,s);//保存商品图片
                if(i==0) firstPicture=s;
            }




            //保存用户分类
            Long p=null,s=null,t=null;
            String goodsCategory=null;
            for(int i=0;i<goods_category.length;i++){
                if(i==0) {
                    p=goods_category[i];
                    goodsCategory=goodsMapper.getPriCategory(goods_category[i]);
                }
                if(i==1) {
                    s=goods_category[i];
                    goodsCategory=goodsMapper.getSecCategory(goods_category[i]);
                }
                if(i==2) {
                    t=goods_category[i];
                    goodsCategory=goodsMapper.getThiCategory(goods_category[i]);
                }
            }
            goodsService.insert(goods_id,UserId,goods_desc,goods_price,goods_use,goods_send,goods_stock,firstPicture,goodsCategory);
            goodsService.saveGoodsCategory(goods_id,p,s,t);
//            添加商品浏览和想要
            goodsService.insertGoodsHot(goods_id);

        return R.success("ok");
    }
}
