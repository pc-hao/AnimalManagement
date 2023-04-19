drop table if exists  userinfo;
CREATE TABLE `userinfo`
(
    `id`       int(32)     NOT NULL,
    `username` varchar(32) NOT NULL UNIQUE,
    `email`    varchar(64) NOT NULL UNIQUE,
    `phone`    varchar(12),
    `bio`      varchar(64),
    `avatar`   varchar(64),
    `blacked`  boolean NOT NULL DEFAULT false,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`id`) REFERENCES `sys_user` (`id`)
);