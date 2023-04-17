DROP DATABASE animalmanagement;
CREATE DATABASE animalmanagement;
USE animalmanagement;


DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user`
(
    `id`       int(32)      NOT NULL AUTO_INCREMENT,
    `username` varchar(32) NOT NULL UNIQUE,
    `password` varchar(256) NOT NULL,
    `status`   varchar(10) NOT NULL comment 'NORMAL正常  PROHIBIT禁用',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4;

INSERT INTO `sys_user`
VALUES (2, 'admin', '$2a$10$5T851lZ7bc2U87zjt/9S6OkwmLW62tLeGLB2aCmq3XRZHA7OI7Dqa', 'NORMAL'); ##password:123456
INSERT INTO `sys_user`
VALUES (3, 'user', '$2a$10$szHoqQ64g66PymVJkip98.Fap21Csy8w.RD8v5Dhq08BMEZ9KaSmS', 'NORMAL'); ##password:123456

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role`
(
    `id`        int(32) NOT NULL AUTO_INCREMENT,
    `role_name` varchar(10) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4;

insert into `sys_role`(`id`, `role_name`)
values (1, 'ADMIN'),
       (2, 'USER');

DROP TABLE IF EXISTS `sys_role_user`;

CREATE TABLE `sys_role_user`
(
    `id`      int(32) NOT NULL AUTO_INCREMENT,
    `user_id` int(32) DEFAULT NULL,
    `role_id` int(32) DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`),
    FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4;

insert into `sys_role_user`(`id`, `user_id`, `role_id`)
values (1, 2, 1),
       (2, 3, 2);

drop table if exists  userInfo;
CREATE TABLE `userInfo`
(
    `id`       int(32)     NOT NULL,
    `username` varchar(32) NOT NULL UNIQUE,
    `email`    varchar(64) NOT NULL UNIQUE,
    `phone`    varchar(12),
    `bio`      varchar(64),
    `avatar`   varchar(64),
    `blacked`  boolean NOT NULL DEFAULT false,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`id`) REFERENCES `sys_user` (`id`)
);

INSERT INTO `userInfo`
VALUES (2, 'admin', '20000000@buaa.edu.cn', "15000000000", "This is a bio", "path", false); ##password:123456
INSERT INTO `userInfo`
VALUES (3, 'user', '20000001@buaa.edu.cn', "15000000001", "This is a bio", "path", false); ##password:123456

CREATE TABLE `animal`
(
    `id`        int(32)         NOT NULL AUTO_INCREMENT,
    `name`      varchar(32)     NOT NULL UNIQUE,
    `intro`     varchar(256)    NOT NULL,
    `adopted`   boolean         NOT NULL,
    `avatar`    varchar(64),
    PRIMARY KEY (`id`)
);

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

insert into tweet (user_id,title,`time`,content,is_help) values (3, "hhhh", now(),"这是一个帖子",false);

CREATE TABLE `comment`
(
    `id`       int(32)  NOT NULL AUTO_INCREMENT,
    `user_id`  int(32)  NOT NULL,
    `tweet_id` int(32)  NOT NULL,
    `content`  text     NOT NULL,
    `time`     datetime NOT NULL,
    `likes`    int(32)  NOT NULL DEFAULT 0,
    `is_help`  boolean  NOT NULL,
    `censored` int(32)  NOT NULL DEFAULT 0,
    `deleted`  boolean  NOT NULL DEFAULT FALSE,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`),
    FOREIGN KEY (`tweet_id`) REFERENCES `tweet` (`id`)
);

CREATE TABLE `application`
(
    `id`       int(32)      NOT NULL AUTO_INCREMENT,
    `user_id`  int(32)      NOT NULL,
    `content`  varchar(256) NOT NULL,
    `censored` boolean      NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
);

CREATE TABLE `track`
(
    `id`         int(32)  NOT NULL AUTO_INCREMENT,
    `animal_id`  int(32)  NOT NULL,
    `time`       datetime NOT NULL,
    `location_x` int(32)  NOT NULL,
    `location_y` int(32)  NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`animal_id`) REFERENCES `animal` (`id`)
);

CREATE TABLE `tag`
(
    `id`      int(32)     NOT NULL AUTO_INCREMENT,
    `content` varchar(32) NOT NULL UNIQUE,
    PRIMARY KEY (`id`)
);

CREATE TABLE `tweettag`
(
    `tweet_id` int(32) NOT NULL,
    `tag_id`   int(32) NOT NULL,
    PRIMARY KEY (`tweet_id`, `tag_id`),
    FOREIGN KEY (tweet_id) REFERENCES tweet (id),
    FOREIGN KEY (tag_id) REFERENCES tag (id)
);

CREATE TABLE `token`
(
    `token`   int(32) NOT NULL,
    `user_id` int(32) NOT NULL,
    PRIMARY KEY (`token`),
    FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
);

CREATE TABLE `message`
(
    `id`      int(32) NOT NULL AUTO_INCREMENT,
    `user_id` int(32) NOT NULL,
    `content` text    NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
);

CREATE TABLE `star`
(
    `user_id`  int(32) NOT NULL,
    `tweet_id` int(32) NOT NULL,
    PRIMARY KEY (`user_id`, `tweet_id`),
    FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`),
    FOREIGN KEY (`tweet_id`) REFERENCES `tweet` (`id`)
);

CREATE TABLE `tweetlike`
(
    `user_id`  int(32) NOT NULL,
    `tweet_id` int(32) NOT NULL,
    PRIMARY KEY (`user_id`, `tweet_id`),
    FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`),
    FOREIGN KEY (`tweet_id`) REFERENCES `tweet` (`id`)
);

CREATE TABLE `commentlike`
(
    `user_id`    int(32) NOT NULL,
    `comment_id` int(32) NOT NULL,
    PRIMARY KEY (`user_id`, `comment_id`),
    FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`),
    FOREIGN KEY (`comment_id`) REFERENCES `comment` (`id`)
);

CREATE TABLE `tweetstar`
(
    `user_id`   int(32)         NOT NULL,
    `tweet_id`  int(32)         NOT NULL,
    PRIMARY KEY (`user_id`, `tweet_id`),
    FOREIGN KEY (`user_id`) REFERENCES `sys_user`(`id`),
    FOREIGN KEY (`tweet_id`) REFERENCES `tweet`(`id`)
);

DROP TABLE IF EXISTS `verification`;

CREATE TABLE `verification`
(
    `email`      varchar(50) NOT NULL,
    `veri_code`  varchar(50) NOT NULL,
    `start_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`email`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4;


DROP EVENT IF EXISTS clean_verification_event;
CREATE EVENT clean_verification_event
    ON SCHEDULE EVERY 20 MINUTE STARTS NOW()
    DO delete
       from verification
       where TIMESTAMPDIFF(MINUTE, start_time, NOW()) > 10;