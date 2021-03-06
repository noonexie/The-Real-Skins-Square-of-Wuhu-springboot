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
-- 数据库字段影响Mybatis PLus数据存取
-- ----------------------------
ALTER TABLE `share_data`
    CHANGE COLUMN `data-type` `dataType`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '分享数据的类型' AFTER `id`,
    CHANGE COLUMN `data-name` `dataName`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '分享数据的名称' AFTER `dataType`,
    CHANGE COLUMN `data-url` `dataUrl`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '分享数据的网址' AFTER `dataName`,
    CHANGE COLUMN `data-text` `dataText`  text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '分享数据的上榜理由' AFTER `dataUrl`;

