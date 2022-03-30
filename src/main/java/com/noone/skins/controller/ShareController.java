package com.noone.skins.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.noone.skins.common.Result;
import com.noone.skins.entity.Share;
import com.noone.skins.mapper.ShareMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author xad
 * @create 2022-03-22 23:05
 * 写HTTP接口
 */
@RestController
@RequestMapping("/share")//定义路由
public class ShareController {

    @Resource//引入sharemapper
    ShareMapper shareMapper;

    //        保存用户上传的分享数据到数据库
    @PostMapping//定义一个post接口,此函数的参数是前台传过来的json数据，通过@RequestBody注解将参数转化为java对象，所以要定义一个java对象做参数///*泛型？：表示任何一种类型*/
    public Result<?> saveShare(@RequestBody Share share) {
        shareMapper.insert(share);
        return Result.success();
    }

    @GetMapping("/listById/{id}")
    public Result<?> listById(@PathVariable Integer id) {
        Share share = shareMapper.selectById(id);
        return Result.success(share);
    }

    //    按关键字分页查询，返回可分页的结果
    @GetMapping("/listAll")
    public Result<?> listPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String type,
                              @RequestParam(defaultValue = "") String search) {
//        pageNum对应前端传入的当前页数，pageSize对应前端传入的每页多少条,search前端传入的关键字：按关键字查询  并均设置默认值
        LambdaQueryWrapper<Share> wrapper = Wrappers.<Share>lambdaQuery();
        wrapper.eq(Share::getDataType, type)  //查找匹配数据库中data_type="video"的
                .like(Share::getDataName, search);      //再查找模糊匹配名称为关键字的
        Page<Share> videoPage = shareMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(videoPage);
    }

    //    点赞&点踩更新,put更新按钮
    @PutMapping
    public synchronized Result<?> changeLikes(@RequestBody Share share) {
        shareMapper.updateById(share);//必传参数为ID，其他参数不传不变，传了的变
        return Result.success();
    }
}
