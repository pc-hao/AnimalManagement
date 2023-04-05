CREATE TABLE `comment`
(
    `id`        int(32)         NOT NULL AUTO_INCREMENT,
    `user_id`   int(32)         NOT NULL,
    `tweet_id`  int(32)         NOT NULL,
    `content`   text            NOT NULL,
    `likes`     int(32)         NOT NULL DEFAULT 0,
    `is_help`   boolean         NOT NULL,
    `deleted`   boolean         NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
    FOREIGN KEY (`tweet_id`) REFERENCES `tweet`(`id`)
);