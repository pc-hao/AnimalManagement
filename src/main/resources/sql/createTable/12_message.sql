CREATE TABLE `message`
(
    `id`        int(32)         NOT NULL AUTO_INCREMENT,
    `user_id`   int(32)         NOT NULL,
    `content`   text            NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`)
);