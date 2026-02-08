create table rate_info
(
    id           bigint         not null
        primary key,
    rate_name    varchar(128)   null,
    rate_val     decimal(32, 8) null,
    backup_id    varchar(32)    null,
    created_user varchar(32)    null,
    created_time datetime       null,
    updated_user varchar(32)    null,
    updated_time datetime       null
);