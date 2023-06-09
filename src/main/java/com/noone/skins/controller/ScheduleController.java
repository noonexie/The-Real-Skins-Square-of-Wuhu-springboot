package com.noone.skins.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.noone.skins.common.Result;
import com.noone.skins.entity.Schedule;
import com.noone.skins.mapper.ScheduleMapper;
import com.noone.skins.mapper.ShareMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Date;
import java.sql.Timestamp;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Resource//引入sharemapper
    private ScheduleMapper scheduleMapper;

    @PostMapping("/putSchedule")
    //定义一个post接口,此函数的参数是前台传过来的json数据，通过@RequestBody注解将参数转化为java对象，所以要定义一个java对象做参数///*泛型？：表示任何一种类型*/
    public Result<?> saveSchedule(@RequestBody Schedule schedule) {
        scheduleMapper.insert(schedule);
        return Result.success();
    }

    @GetMapping("/listSchedule")
    public Result<?> listPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        //pageNum对应前端传入的当前页数，pageSize对应前端传入的每页多少条,search前端传入的关键字：按关键字查询  并均设置默认值
        LambdaQueryWrapper<Schedule> wrapper = Wrappers.<Schedule>lambdaQuery();
        wrapper.ge(Schedule::getEnd, search);
//        wrapper.like(Schedule::getEnd, search);      //再查找模糊匹配名称为关键字的
//        .eq(Share::getDataType, type)  //查找匹配数据库中data_type="video"的
        Page<Schedule> videoPage = scheduleMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(videoPage);
    }
}
