package com.noone.skins.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.sql.Date;
import java.sql.Timestamp;

@TableName("schedule")
@Data
public class Schedule {
    @TableId(value = "id",type = IdType.AUTO)//设置id自动生成
    private Integer id;
    private String user;
    private Timestamp start;
    private Timestamp end;
    private String things;
}
