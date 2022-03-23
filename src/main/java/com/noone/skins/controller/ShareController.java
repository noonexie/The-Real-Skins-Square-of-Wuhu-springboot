package com.noone.skins.controller;

import com.noone.skins.common.Result;
import com.noone.skins.entity.Share;
import com.noone.skins.mapper.ShareMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping//定义一个post接口,此函数的参数是前台传过来的json数据，通过@RequestBody注解将参数转化为java对象，所以要定义一个java对象做参数
    public Result<?/*泛型？：表示任何一种类型*/> saveShare(@RequestBody Share share) {
        shareMapper.insert(share);
        return Result.success();
    }
}
