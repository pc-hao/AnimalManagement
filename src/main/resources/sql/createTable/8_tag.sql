CREATE TABLE `tag`
(
    `id`        int(32)         NOT NULL AUTO_INCREMENT,
    `content`   varchar(32)     NOT NULL UNIQUE,
    PRIMARY KEY (`id`)
);