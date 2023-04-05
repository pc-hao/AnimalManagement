CREATE TABLE `user`
(
    `id`        int(32)         NOT NULL AUTO_INCREMENT,
    `username`  varchar(32)     NOT NULL UNIQUE,
    `password`  varchar(256)    NOT NULL,
    `email`     varchar(64)     NOT NULL,
    `phone`     varchar(12)     NOT NULL,
    `bio`       varchar(64)     NOT NULL,
    `avatar`    varchar(64)     NOT NULL,
    `blacked`   varchar(64)     NOT NULL,
    PRIMARY KEY (`id`)
);