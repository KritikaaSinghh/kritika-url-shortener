INSERT INTO users (email, password, name, role)
VALUES ('admin@gmail.com', 'admin', 'Administrator', 'ROLE_ADMIN'),
       ('singhkritika8449@gmail.com', 'secret', 'Kritika Singh', 'ROLE_USER');

INSERT INTO short_urls (short_key, original_url, created_by, created_at, expires_at, is_private, click_count)
VALUES
('java01', 'https://spring.io/projects/spring-boot', 1, TIMESTAMP '2026-07-07', NULL, FALSE, 0),
('java02', 'https://www.oracle.com/java/', 1, TIMESTAMP '2026-07-08', NULL, FALSE, 0),
('java03', 'https://docs.spring.io/spring-framework/reference/', 1, TIMESTAMP '2026-07-09', NULL, FALSE, 0),
('java04', 'https://github.com/spring-projects/spring-boot', 1, TIMESTAMP '2026-07-10', NULL, TRUE, 0),
('java05', 'https://start.spring.io/', 1, TIMESTAMP '2026-07-11', NULL, FALSE, 0);