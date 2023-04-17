CREATE TABLE `tweet`
(
    `id`           int(32)  NOT NULL AUTO_INCREMENT,
    `user_id`      int(32)  NOT NULL,
    `title`        varchar(100)      DEFAULT NULL,
    `content`      text     NOT NULL,
    `images`       varchar(320)      DEFAULT NULL,
    `time`         datetime NOT NULL,
    `views`        int(32)  NOT NULL DEFAULT 0,
    `views_weekly` int(32)  NOT NULL DEFAULT 0,
    `likes`        int(32)  NOT NULL DEFAULT 0,
    `stars`        int(32)  NOT NULL DEFAULT 0,
    `comments`     int(32)  NOT NULL DEFAULT 0,
    `is_help`      boolean  NOT NULL,
    `solved`       boolean  NOT NULL DEFAULT FALSE,
    `censored`     int(32)  NOT NULL DEFAULT 0,
    `published`    boolean  NOT NULL DEFAULT FALSE,
    `deleted`      boolean  NOT NULL DEFAULT FALSE,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
);
