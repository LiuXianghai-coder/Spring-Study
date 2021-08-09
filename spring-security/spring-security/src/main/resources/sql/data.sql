-- 原始密码都为 “123456”
INSERT INTO user_role
VALUES (1, 'Tom', '$2a$10$o/SQpYXDmLUrGf0IpB/.kOm1y9HSWzNDCxQMXrUTAfDxegNIxPapK', 'ROLE_ADMIN', TRUE),
       (2, 'Jerry', '$2a$10$o/SQpYXDmLUrGf0IpB/.kOm1y9HSWzNDCxQMXrUTAfDxegNIxPapK', 'ROLE_ADMIN', TRUE),
       (3, 'Spark', '$2a$10$eV6oHClRWrzwCQGlFOgoseR3u2Rtc8XemnIo5eWtQYlsPzPKHr.A6', 'ROLE_ADMIN', TRUE),
       (4, 'Tomas', '$2a$10$eV6oHClRWrzwCQGlFOgoseR3u2Rtc8XemnIo5eWtQYlsPzPKHr.A6', 'ROLE_ADMIN', TRUE);