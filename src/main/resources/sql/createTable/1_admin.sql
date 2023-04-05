CREATE TABLE `admin`
(
    `id`        int(32)         NOT NULL AUTO_INCREMENT,
    `username`  varchar(32)     NOT NULL UNIQUE,
    `password`  varchar(256)    NOT NULL,
    PRIMARY KEY (`id`)
);