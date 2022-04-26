package com.noone.skins.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

/**
 * @author xad
 * @create 2022-03-22 23:13
 */
@TableName("share_data")//java类型跟数据库表明一一对应
@Data//不需要再写get，set方法
public class Share {
    @TableId(value = "id",type = IdType.AUTO)//设置id自动生成
    private Integer id;
    @TableField(value = "data_type",jdbcType = JdbcType.VARCHAR)
    private String dataType;
    @TableField(value = "data_name",jdbcType = JdbcType.VARCHAR)
    private String dataName;
    @TableField(value = "data_url",jdbcType = JdbcType.VARCHAR)
    private String dataUrl;
    @TableField(value = "data_text",jdbcType = JdbcType.LONGVARCHAR)
    private String dataText;
    @TableField(value = "img_url",jdbcType = JdbcType.LONGVARCHAR)
    private String imgUrl;
    private Integer likes;
}
