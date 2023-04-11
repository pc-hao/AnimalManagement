drop table if exists  userInfo;
CREATE TABLE `userInfo`
(
    `id`       int(32)     NOT NULL,
    `username` varchar(32) NOT NULL,
    `email`    varchar(64) NOT NULL UNIQUE,
    `phone`    varchar(12),
    `bio`      varchar(64),
    `avatar`   varchar(64),
    PRIMARY KEY (`id`),
    FOREIGN KEY (`id`) REFERENCES `sys_user` (`id`)
);