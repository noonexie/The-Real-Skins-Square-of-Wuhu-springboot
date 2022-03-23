package com.noone.skins.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author xad
 * @create 2022-03-22 23:13
 */
@TableName("share_data")//java类型跟数据库表明一一对应
@Data//不需要再写get，set方法
public class Share {
    @TableId(value = "id",type = IdType.AUTO)//设置id自动生成
    private Integer id;
    private String type;
    private String name;
    private String url;
    private String text;
    private Integer likes;
}
