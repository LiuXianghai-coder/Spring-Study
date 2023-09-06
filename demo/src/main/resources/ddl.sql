-- auto-generated definition
create table sale_info
(
    id      int         not null
        primary key,
    sale_id varchar(32) not null,
    amount  int         not null,
    year    date        not null
);

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