CREATE TABLE `application`
(
    `id`        int(32)         NOT NULL AUTO_INCREMENT,
    `user_id`   int(32)         NOT NULL,
    `content`   varchar(256)    NOT NULL,
    `censored`  boolean         NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `sys_user`(`id`)
);