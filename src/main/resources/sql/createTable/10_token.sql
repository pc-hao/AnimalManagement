CREATE TABLE `token`
(
    `token`     int(32)         NOT NULL,
    `user_id`   int(32)         NOT NULL,
    PRIMARY KEY (`token`),
    FOREIGN KEY (`user_id`) REFERENCES `sys_user`(`id`)
);