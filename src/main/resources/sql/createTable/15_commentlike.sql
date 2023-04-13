CREATE TABLE `commentlike`
(
    `user_id`       int(32)         NOT NULL,
    `comment_id`    int(32)         NOT NULL,
    PRIMARY KEY (`user_id`, `comment_id`),
    FOREIGN KEY (`user_id`) REFERENCES `sys_user`(`id`),
    FOREIGN KEY (`comment_id`) REFERENCES `comment`(`id`)
);