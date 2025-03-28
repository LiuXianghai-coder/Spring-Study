-- auto-generated definition
create table user_info
(
    user_id     int         not null
        primary key,
    user_name   varchar(30) null,
    user_gender varchar(6)  null,
    simple_id   varchar(32) null,
    backup_id   varchar(32) null
);

create table oa_statistic_2025_a
(
    id                bigint      not null comment '主键'
        primary key,
    statistic_content text        null comment '统计内容',
    created_id        varchar(32) null comment '该条数据的创建人',
    created_time      datetime    null comment '数据创建时间'
);

create table oa_statistic_2025_b
(
    id                bigint      not null comment '主键'
        primary key,
    statistic_content text        null comment '统计内容',
    created_id        varchar(32) null comment '该条数据的创建人',
    created_time      datetime    null comment '数据创建时间'
);

create table oa_statistic_2025_c
(
    id                bigint      not null comment '主键'
        primary key,
    statistic_content text        null comment '统计内容',
    created_id        varchar(32) null comment '该条数据的创建人',
    created_time      datetime    null comment '数据创建时间'
);