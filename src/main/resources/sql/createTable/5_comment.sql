CREATE TABLE `comment`
(
    `id`       int(32)  NOT NULL AUTO_INCREMENT,
    `user_id`  int(32)  NOT NULL,
    `tweet_id` int(32)  NOT NULL,
    `content`  text     NOT NULL,
    `time`     datetime NOT NULL,
    `likes`    int(32)  NOT NULL DEFAULT 0,
    `is_help`  boolean  NOT NULL,
    `censored` boolean  NOT NULL DEFAULT FALSE, 
    `deleted`  boolean  NOT NULL DEFAULT FALSE,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`),
    FOREIGN KEY (`tweet_id`) REFERENCES `tweet` (`id`)
);