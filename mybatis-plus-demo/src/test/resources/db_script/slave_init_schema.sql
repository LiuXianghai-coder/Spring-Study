-- auto-generated definition
create table course_info
(
    course_id   int         not null
        primary key,
    course_name varchar(32) null,
    course_type int         null,
    update_time datetime    null
);