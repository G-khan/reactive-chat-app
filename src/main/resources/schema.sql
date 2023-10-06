CREATE TABLE IF NOT EXISTS message
(
    id       bigint AUTO_INCREMENT PRIMARY KEY,
    username varchar(255) NOT NULL,
    content  varchar(MAX) NOT NULL
);