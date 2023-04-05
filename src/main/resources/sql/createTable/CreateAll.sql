CREATE TABLE `admin`
(
    `id`        int(32)         NOT NULL AUTO_INCREMENT,
    `username`  varchar(32)     NOT NULL UNIQUE,
    `password`  varchar(256)    NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `user`
(
    `id`        int(32)         NOT NULL AUTO_INCREMENT,
    `username`  varchar(32)     NOT NULL UNIQUE,
    `password`  varchar(256)    NOT NULL,
    `email`     varchar(64)     NOT NULL,
    `phone`     varchar(12)     NOT NULL,
    `bio`       varchar(64)     NOT NULL,
    `avatar`    varchar(64)     NOT NULL,
    `blacked`   varchar(64)     NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `animal`
(
    `id`        int(32)         NOT NULL AUTO_INCREMENT,
    `name`      varchar(32)     NOT NULL UNIQUE,
    `intro`     varchar(256)    NOT NULL,
    `adopted`   boolean         NOT NULL,
    PRIMARY KEY (`id`)
);

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

CREATE TABLE `application`
(
    `id`        int(32)         NOT NULL AUTO_INCREMENT,
    `user_id`   int(32)         NOT NULL,
    `content`   varchar(256)    NOT NULL,
    `censored`  boolean         NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`)
);

CREATE TABLE `track`
(
    `id`            int(32)     NOT NULL AUTO_INCREMENT,
    `animal_id`     int(32)     NOT NULL,
    `time`          datetime    NOT NULL,
    `location_x`    int(32)     NOT NULL,
    `location_y`    int(32)     NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`animal_id`) REFERENCES `animal`(`id`)
);

CREATE TABLE `tag`
(
    `id`        int(32)         NOT NULL AUTO_INCREMENT,
    `content`   varchar(32)     NOT NULL UNIQUE,
    PRIMARY KEY (`id`)
);

CREATE TABLE `tweettag`
(
    `tweet_id`  int(32)         NOT NULL,
    `tag_id`    int(32)         NOT NULL,
    PRIMARY KEY (`tweet_id`, `tag_id`),
    FOREIGN KEY (tweet_id) REFERENCES tweet(id),
    FOREIGN KEY (tag_id) REFERENCES tag(id)
);

CREATE TABLE `token`
(
    `token`     int(32)         NOT NULL,
    `user_id`   int(32)         NOT NULL,
    PRIMARY KEY (`token`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`)
);

CREATE TABLE `admintoken`
(
    `token`         int(32)         NOT NULL,
    `admin_id`      int(32)         NOT NULL,
    PRIMARY KEY (`token`),
    FOREIGN KEY (`admin_id`) REFERENCES `admin`(`id`)
);

CREATE TABLE `message`
(
    `id`        int(32)         NOT NULL AUTO_INCREMENT,
    `user_id`   int(32)         NOT NULL,
    `content`   text            NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`)
);

CREATE TABLE `star`
(
    `user_id`   int(32)         NOT NULL,
    `tweet_id`  int(32)         NOT NULL,
    PRIMARY KEY (`user_id`, `tweet_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
    FOREIGN KEY (`tweet_id`) REFERENCES `tweet`(`id`)
);

CREATE TABLE `tweetlike`
(
    `user_id`   int(32)         NOT NULL,
    `tweet_id`  int(32)         NOT NULL,
    PRIMARY KEY (`user_id`, `tweet_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
    FOREIGN KEY (`tweet_id`) REFERENCES `tweet`(`id`)
);

CREATE TABLE `commentlike`
(
    `user_id`       int(32)         NOT NULL,
    `comment_id`    int(32)         NOT NULL,
    PRIMARY KEY (`user_id`, `comment_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
    FOREIGN KEY (`comment_id`) REFERENCES `comment`(`id`)
);
