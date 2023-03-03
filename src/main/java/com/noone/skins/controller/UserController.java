//package com.noone.skins.controller;
//
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import com.noone.skins.common.Result;
//import com.noone.skins.entity.User;
//import com.noone.skins.mapper.UserMapper;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//
//@RestController
//@RequestMapping("/user")
//public class UserController {
//
//    @Resource
//    private UserMapper userMapper;
//
//    //登录接口
//    @PostMapping("/login")
//    public Result<?> login(@RequestBody User user) {
//        User one = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()).eq(User::getPassword, user.getPassword()));
//        if (one == null) {
//            return Result.error("-1", "用户名或密码错误");
//        }
//        Integer id = one.getId();
//        return Result.success(id);
//    }
//
//    //注册接口
//    @PostMapping("/register")
//    public Result<?> register(@RequestBody User user) {
//        User one = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()));
//        if (one != null) {
//            return Result.error("-1", "用户名已存在");
//        }
//        userMapper.insert(user);
//        return Result.success();
//    }
//
//    @GetMapping("/{id}")
//    public Result<?> getById(@PathVariable Integer id) {
//        User user = userMapper.selectById(id);
//        return Result.success(user);
//    }
//
//    @PutMapping
//    public Result<?> changeInfo(@RequestBody User user) {
//        userMapper.updateById(user);
//        return Result.success();
//    }
//}
