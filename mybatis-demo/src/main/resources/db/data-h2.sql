INSERT INTO asset_info (id, group_id, asset_name) VALUES (1, 1, '社科院');
INSERT INTO asset_info (id, group_id, asset_name) VALUES (2, 1, '北大医学院');
INSERT INTO asset_info (id, group_id, asset_name) VALUES (3, 1, '离心机');

INSERT INTO course_info (course_id, course_name, course_type, update_time) VALUES (1, '语文', 1, '2024-01-08 21:12:25');
INSERT INTO course_info (course_id, course_name, course_type, update_time) VALUES (2, '数学', 2, '2024-01-08 21:12:25');
INSERT INTO course_info (course_id, course_name, course_type, update_time) VALUES (3, '大学英语', 2, '2024-01-08 21:12:25');
INSERT INTO course_info (course_id, course_name, course_type, update_time) VALUES (4, '政治', 2, '2023-08-10 21:09:27');
INSERT INTO course_info (course_id, course_name, course_type, update_time) VALUES (5, '历史', 2, '2023-08-10 21:09:29');
INSERT INTO course_info (course_id, course_name, course_type, update_time) VALUES (6, '地理', 2, '2023-08-10 21:09:29');
INSERT INTO course_info (course_id, course_name, course_type, update_time) VALUES (7, '化学', 3, '2023-08-10 21:09:29');

INSERT INTO dictionary (id, group_code, vc_code, vc_value) VALUES (1, 'union_kind', '1', '子公司');
INSERT INTO dictionary (id, group_code, vc_code, vc_value) VALUES (2, 'union_kind', '2', '合营企业');
INSERT INTO dictionary (id, group_code, vc_code, vc_value) VALUES (3, 'union_kind', '3', '联营企业');

INSERT INTO group_info (id, group_id, group_type, source) VALUES (1, 1, '1', '北京社会科学研究部');
INSERT INTO group_info (id, group_id, group_type, source) VALUES (2, 1, '1', '北京原子能物理研究所');
INSERT INTO group_info (id, group_id, group_type, source) VALUES (3, 1, '1', '北京生物研究中心');

INSERT INTO key_word (id, group_id, key_word) VALUES (2, 1, '医学');
INSERT INTO key_word (id, group_id, key_word) VALUES (3, 1, '离心');

INSERT INTO user_info (user_id, user_name, user_gender, simple_id, backup_id) VALUES (2, 'xhliu2', 'male', '0x3f3f', '135794');