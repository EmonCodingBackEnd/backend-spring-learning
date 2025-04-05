-- 创建数据库
CREATE DATABASE IF NOT EXISTS boot3db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
-- 使用数据库
use boot3db;

# -- 创建数据表
DROP TABLE IF EXISTS t_user;
CREATE TABLE t_user
(
    `id`   BIGINT(20) NOT Null AUTO_INCREMENT COMMENT '编号',
    `login_name` VARCHAR(200) COMMENT '用户名称',
    `nick_name` VARCHAR(200) COMMENT '用户昵称',
    `passwd` VARCHAR(200) COMMENT '用户密码',
    PRIMARY KEY (id)
) COMMENT = '';
# -- 初始化数据：建议配置为500条
INSERT INTO boot3db.t_user (login_name,nick_name,passwd) VALUES
                                                ('zhangsan','张三','123456')
                                                ;
