CREATE TABLE `tweet`
(
    `id`            int(32)         NOT NULL AUTO_INCREMENT,
    `user_id`       int(32)         NOT NULL,
    `content`       text            NOT NULL,
    `images`        varchar(320)    DEFAULT NULL,
    `views`         int(32)         NOT NULL DEFAULT 0,
    `views_weekly`  int(32)         NOT NULL DEFAULT 0,
    `likes`         int(32)         NOT NULL DEFAULT 0,
    `stars`         int(32)         NOT NULL DEFAULT 0,
    `is_help`       boolean         NOT NULL,
    `solved`        boolean         NOT NULL,
    `censored`      boolean         NOT NULL,
    `published`     boolean         NOT NULL,
    `deleted`       boolean         NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`)
);
