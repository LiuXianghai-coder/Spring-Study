create table asset_info
(
    id         bigint auto_increment
        primary key,
    group_id   bigint      not null,
    asset_name varchar(64) not null
);

create table course_info
(
    course_id   int         not null
        primary key,
    course_name varchar(32) null
);

create table dictionary
(
    id         bigint auto_increment
        primary key,
    group_code varchar(10) null,
    vc_code    varchar(10) null,
    vc_value   varchar(64) null
);

create table group_info
(
    id         bigint auto_increment
        primary key,
    group_id   bigint       not null,
    group_type varchar(10)  not null,
    source     varchar(255) null
);

create table key_word
(
    id       bigint auto_increment
        primary key,
    group_id bigint      not null,
    key_word varchar(32) not null
);

create table user_course_rel
(
    id        int not null
        primary key,
    user_id   int null,
    course_id int null
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