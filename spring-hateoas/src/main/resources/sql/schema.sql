CREATE TABLE IF NOT EXISTS user_info
(
    user_id     BIGINT PRIMARY KEY,
    user_name   VARCHAR(20) NOT NULL,
    user_gender VARCHAR(4)  NOT NULL
);