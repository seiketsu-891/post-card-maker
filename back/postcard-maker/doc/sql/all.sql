DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
    `username`   varchar(15)  DEFAULT NULL COMMENT '用户名',
    `password`   varchar(50)  DEFAULT NULL COMMENT '密码',
    `email`      varchar(255) DEFAULT NULL COMMENT '电子邮箱',
    `createTime` datetime     DEFAULT NULL COMMENT '创建时间',
    `updateTime` datetime     DEFAULT NULL COMMENT '创建时间',
    `salt`       varchar(255) DEFAULT NULL COMMENT '密码md5加密颜值',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 8
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='用户表';



