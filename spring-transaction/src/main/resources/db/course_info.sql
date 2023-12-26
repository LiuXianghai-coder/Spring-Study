CREATE TABLE course_info
(
    course_id   INT         NOT NULL
        PRIMARY KEY,
    course_name VARCHAR(32) NULL,
    course_type INT         NULL,
    update_time DATETIME NULL
);

INSERT INTO course_info (course_id, course_name, course_type, update_time) VALUES (1, '语文', 1, '2023-12-25 20:04:26');
INSERT INTO course_info (course_id, course_name, course_type, update_time) VALUES (2, '数学', 1, '2023-08-10 21:09:29');
INSERT INTO course_info (course_id, course_name, course_type, update_time) VALUES (3, '英语', 1, '2023-08-10 21:09:28');
INSERT INTO course_info (course_id, course_name, course_type, update_time) VALUES (4, '政治', 2, '2023-08-10 21:09:27');
INSERT INTO course_info (course_id, course_name, course_type, update_time) VALUES (5, '历史', 2, '2023-08-10 21:09:29');
INSERT INTO course_info (course_id, course_name, course_type, update_time) VALUES (6, '地理', 2, '2023-08-10 21:09:29');
INSERT INTO course_info (course_id, course_name, course_type, update_time) VALUES (7, '化学', 3, '2023-08-10 21:09:29');