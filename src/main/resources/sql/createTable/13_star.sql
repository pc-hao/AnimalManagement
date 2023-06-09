CREATE TABLE `star`
(
    `user_id`   int(32)         NOT NULL,
    `tweet_id`  int(32)         NOT NULL,
    PRIMARY KEY (`user_id`, `tweet_id`),
    FOREIGN KEY (`user_id`) REFERENCES `sys_user`(`id`),
    FOREIGN KEY (`tweet_id`) REFERENCES `tweet`(`id`)
);