/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Host           : localhost:3306
Source Database       : the_real_skins
*/

-- ----------------------------
-- Table structure for share_data
-- ----------------------------
CREATE TABLE `share_data` (
                            `id`  int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '分享数据的ID' ,
                            `data_type`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '分享数据的类型' ,
                            `data_name`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '分享数据的名称' ,
                            `data_url`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '分享数据的网址' ,
                            `data_text`  text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '分享数据的上榜理由' ,
                            PRIMARY KEY (`id`)
)
    COMMENT='用户上传分享的数据库'
;

-- ----------------------------
-- add likes
-- ----------------------------
ALTER TABLE `share_data`
    ADD COLUMN `likes`  int(11) NULL DEFAULT 0 COMMENT '分享数据的点赞数' AFTER `data_text`;

-- ----------------------------
-- 数据库字段影响Mybatis PLus数据存取 不是 要加注解
-- ----------------------------
ALTER TABLE `share_data`
    CHANGE COLUMN `data-type` `dataType`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '分享数据的类型' AFTER `id`,
    CHANGE COLUMN `data-name` `dataName`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '分享数据的名称' AFTER `dataType`,
    CHANGE COLUMN `data-url` `dataUrl`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '分享数据的网址' AFTER `dataName`,
    CHANGE COLUMN `data-text` `dataText`  text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '分享数据的上榜理由' AFTER `dataUrl`;

-- ----------------------------
-- 删除字段为NULL的脏数据
-- ----------------------------
DELETE FROM share_data WHERE data_type IS NULL;

-- ----------------------------
-- 匹配查询
-- ----------------------------
SELECT * FROM share_data WHERE data_url REGEXP 'http';

-- ----------------------------
-- 筛选不匹配的
-- ----------------------------
SELECT * FROM share_data WHERE data_url NOT REGEXP 'http';

-- ----------------------------
-- 删除不匹配的
-- ----------------------------
DELETE FROM share_data WHERE data_url NOT REGEXP 'http';

-- ----------------------------
-- 筛选id大于45的
-- ----------------------------
SELECT * from share_data WHERE id > 45;

-- ----------------------------
-- 删除id大于45的
-- ----------------------------
DELETE from share_data WHERE id > 45;

-- ----------------------------
-- 添加用户上传图片的地址存储字段
-- ----------------------------
ALTER TABLE `share_data`
    ADD COLUMN `img_url`  text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '上传图片的地址' AFTER `data_text`;

-- ----------------------------
-- 新建用户信息表
-- ----------------------------
CREATE TABLE `user` (
                        `id`  int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户ID' ,
                        `username`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '用户名' ,
                        `password`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '用户密码' ,
                        `nickname`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '用户昵称' ,
                        `avatar`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '用户头像URL' ,
                        PRIMARY KEY (`id`)
)
    DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='用户信息表'
;


SELECT * FROM share_data WHERE img_url IS NULL;

CREATE TABLE user (
`id` INT  UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
`username` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '姓名',
`password` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '密码',
`start_time` datetime  DEFAULT NULL COMMENT '记录创建时间',
`validity_time` datetime  DEFAULT NULL COMMENT '有效时间',
`status` TINYINT NOT NULL DEFAULT '1' COMMENT '1代表记录有效，0代表记录无效',
PRIMARY KEY (`id`)
) ENGINE = INNODB charset = utf8mb4 COMMENT '用户信息表';