CREATE TABLE `tb_user`
(
    `id`   bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `name` varchar(255) NOT NULL DEFAULT '' COMMENT '姓名',
    `age`  int(11)      NOT NULL DEFAULT '-1' COMMENT '年龄',
    PRIMARY KEY (`id`),
    INDEX `index_name` (`name`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='用户信息表';

CREATE TABLE `tb_user_contact`
(
    `id`            bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `user_id`       bigint(20)   NOT NULL DEFAULT '-1' COMMENT '用户id',
    `contact_type`  tinyint(4)   NOT NULL DEFAULT '0' COMMENT '联系方式类型 0:未知 1:手机 2:邮箱 3:微信 4:QQ',
    `contact_value` varchar(255) NOT NULL DEFAULT '' COMMENT '联系方式信息',
    `create_time`   DATETIME     NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
    `update_time`   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `index_user_id` (`user_id`) USING BTREE,
    INDEX `index_contact_value` (`contact_value`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='联系方式信息表';


CREATE TABLE `tb_message`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `msg_id`      varchar(255) NOT NULL DEFAULT '' COMMENT '消息ID',
    `status`      int(11)      NOT NULL DEFAULT '-1' COMMENT '消息状态，-1-待发送，0-发送中，1-发送失败 2-已发送',
    `content`     varchar(255) NOT NULL DEFAULT '' COMMENT '消息内容',
    `deleted`     tinyint(4)   NOT NULL DEFAULT '0' COMMENT '是否删除 0-未删除  1-已删除',
    `create_time` DATETIME     NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
    `update_time` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `index_msg_id` (`msg_id`) USING BTREE,
    INDEX `index_create_time` (`create_time`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='消息表';

CREATE TABLE `tb_message_detail`
(
    `id`             bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `msg_id`         varchar(255) NOT NULL DEFAULT '' COMMENT '消息ID',
    `detail_content` varchar(255) NOT NULL DEFAULT '' COMMENT '详细消息内容',
    `create_time`    DATETIME     NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
    `update_time`    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `index_msg_id` (`msg_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='消息表详情表';


-- auto-generated definition
create table store_snapshot
(
    id           varchar(32) not null comment '主键',
    warehouse_id varchar(32) null comment '仓库主键',
    snap_date    datetime    null comment '快照日期',
    create_id    varchar(32) null comment '创建人id',
    created_time datetime    null comment '创建日期',
    modify_id    varchar(32) null comment '更新人 id',
    modify_time  datetime    null comment '更新时间'
)
    comment '仓库快照';