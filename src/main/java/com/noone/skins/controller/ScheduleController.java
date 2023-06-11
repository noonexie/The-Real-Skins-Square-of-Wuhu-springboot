package com.noone.skins.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.noone.skins.common.MailService;
import com.noone.skins.common.Result;
import com.noone.skins.entity.Schedule;
import com.noone.skins.entity.User;
import com.noone.skins.mapper.ScheduleMapper;
import com.noone.skins.mapper.ShareMapper;
import com.noone.skins.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Resource//引入sharemapper
    private ScheduleMapper scheduleMapper;
    @Resource
    private MailService mailService;
    @Resource
    private UserMapper userMapper;

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

    @GetMapping("/smtc")
    public Result<?> sendMailToCreator(@RequestParam Integer fromId,
                                       @RequestParam Integer thingId) {
//        根据fromid查来源者用户名
        User userFrom = userMapper.selectById(fromId);
        Schedule schedule = scheduleMapper.selectById(thingId);
        User userTo = userMapper.selectById(schedule.getUser());
        String text = "用户： " + userFrom.getNickname() +
                " 对您在 " + schedule.getStart() +
                " 至 " + schedule.getEnd() +
                " 时间段内的计划非常感兴趣，请去真皮广场与TA约一下吧！";
        mailService.sendMail("真皮广场通知！", text, userTo.getEmail());
        return Result.success();
    }

    @GetMapping("/smto")
    public Result<?> sendMailToOther(@RequestParam Integer thingId) {
        Schedule schedule = scheduleMapper.selectById(thingId);
        int creatorId = schedule.getUser();
        User creator = userMapper.selectById(creatorId);
        String text = "用户： " + creator.getNickname() +
                " 创建了 " + schedule.getStart() +
                " 至 " + schedule.getEnd() +
                " 时间段内的日程，请前往真皮广场与其相约吧！";
        List<User> userList = userMapper.selectList(null);
        for (User user : userList) {
            if (user.getId() != creatorId) {
                mailService.sendMail("真皮广场通知！", text, user.getEmail());
            }
        }
        return Result.success();
    }
}
