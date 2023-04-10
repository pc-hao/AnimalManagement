CREATE TABLE `userInfo`
(
    `id`       int(32)     NOT NULL AUTO_INCREMENT,
    `username` varchar(32) NOT NULL UNIQUE,
    `email`    varchar(64) NOT NULL,
    `phone`    varchar(12),
    `bio`      varchar(64),
    `avatar`   varchar(64),
    PRIMARY KEY (`id`)
);