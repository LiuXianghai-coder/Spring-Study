-- auto-generated definition
create table order_info
(
    id          int      not null comment '记录所属主键'
        primary key,
    user_id     int      null comment '创建该订单的用户 id',
    goods_id    int      null comment '商品所属id',
    goods_cnt   int      null comment '商品交易数量',
    created_tme datetime null comment '订单创建时间'
);

create table user_info
(
    user_id     int         not null
        primary key,
    user_name   varchar(30) null,
    user_gender varchar(6)  null,
    simple_id   varchar(32) null,
    backup_id   varchar(32) null
);