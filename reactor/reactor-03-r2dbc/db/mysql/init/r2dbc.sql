# 特性	        utf8mb4_0900_ai_ci	                 utf8mb4_unicode_ci
# 引入版本	    MySQL 8.0 (Unicode 9.0 标准)          MySQL 5.7 (Unicode 8.0 标准)
# 排序准确性	    更精确，符合最新Unicode标准	         较精确
# 性能	        更快 (优化过的算法)  更快（优化）	     较慢
# 大小写敏感	    不敏感 (ai = accent insensitive)	     不敏感
# 重音敏感	    不敏感 (ai)	                         不敏感
# 表情符号处理	更好  更棒	                         较好
# 语言特定规则	更完善	                             较完善
# ================================================================================
# ROW_FORMAT 的可用取值  行
# MySQL 支持以下几种行格式：
#
# 行格式	        说明	                            适用场景
# DYNAMIC	    动态行格式，优化可变长度字段存储	    包含可变长度列的表（推荐InnoDB默认）
# COMPACT	    紧凑行格式，比REDUNDANT更节省空间	旧版本兼容
# REDUNDANT	    冗余行格式，兼容性最好但空间效率低	极少使用
# COMPRESSED	压缩行格式，使用zlib压缩表数据	    需要节省空间的只读或低频更新表
# FIXED	        固定行格式（仅MyISAM支持）	        MyISAM引擎的固定长度列表

-- 创建数据库
CREATE DATABASE IF NOT EXISTS r2dbc DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
-- 使用数据库
use `r2dbc`;

# -- 创建数据表
-- 用户表
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`
(
    `id`          bigint(20)                                                    NOT NULL AUTO_INCREMENT,
    `username`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
    `password`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
    `email`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '邮箱',
    `phone`       char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci     NOT NULL COMMENT '电话',
    `create_time` datetime(0)                                                   NOT NULL COMMENT '创建时间',
    `update_time` datetime(0)                                                   NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- 角色表
DROP TABLE IF EXISTS `t_roles`;
CREATE TABLE `t_roles`
(
    `id`          bigint(20)                                                    NOT NULL AUTO_INCREMENT,
    `name`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名',
    `value`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色的英文名',
    `create_time` datetime(0)                                                   NOT NULL,
    `update_time` datetime(0)                                                   NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- 权限表（资源表）
DROP TABLE IF EXISTS `t_perm`;
CREATE TABLE `t_perm`
(
    `id`          bigint(20)                                                    NOT NULL AUTO_INCREMENT,
    `value`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限字段',
    `uri`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '资源路径',
    `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '资源描述',
    `create_time` datetime(0)                                                   NOT NULL,
    `update_time` datetime(0)                                                   NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- 用户角色关系表
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`
(
    `id`          bigint(20)  NOT NULL AUTO_INCREMENT,
    `user_id`     bigint(20)  NOT NULL,
    `role_id`     bigint(20)  NOT NULL,
    `create_time` datetime(0) NOT NULL,
    `update_time` datetime(0) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;


-- 角色权限关系表
DROP TABLE IF EXISTS `t_role_perm`;
CREATE TABLE `t_role_perm`
(
    `id`          bigint(20)  NOT NULL AUTO_INCREMENT,
    `role_id`     bigint(20)  NOT NULL,
    `perm_id`     bigint(20)  NOT NULL,
    `create_time` datetime(0) NOT NULL,
    `update_time` datetime(0) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- 图书&作者表
DROP TABLE IF EXISTS `t_book`;
CREATE TABLE `t_book`
(
    `id`           bigint(20)   NOT NULL AUTO_INCREMENT,
    `title`        varchar(255) NOT NULL,
    `author_id`    bigint(20)   NOT NULL,
    `publish_time` datetime(0)  NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `t_author`;
CREATE TABLE `t_author`
(
    `id`   bigint(20)   NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;
;
# -- 初始化数据：
INSERT INTO r2dbc.t_author (name)
VALUES ('张三'),
       ('李四')
;