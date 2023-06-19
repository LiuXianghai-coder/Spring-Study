CREATE TABLE IF NOT EXISTS user_role(
    user_id SERIAL PRIMARY KEY ,
    user_name VARCHAR(30) NOT NULL ,
    user_password VARCHAR(128) NOT NULL ,
    user_role VARCHAR(10) NOT NULL ,
    is_enabled BOOLEAN NOT NULL DEFAULT TRUE
);