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

CREATE TABLE `userinfo`
(
    `id`       int(32)     NOT NULL,
    `username` varchar(32) NOT NULL UNIQUE,
    `email`    varchar(64) NOT NULL UNIQUE,
    `phone`    varchar(12) NOT NULL DEFAULT "Empty",
    `bio`      varchar(64) NOT NULL DEFAULT "这个人是个OP，还没有个性签名",
    `avatar`   varchar(128) DEFAULT "/static/images/user/default.png",
    `blacked`  boolean     NOT NULL DEFAULT false,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`id`) REFERENCES `sys_user` (`id`)
);

INSERT INTO `userinfo`
VALUES (2, 'admin', '20000000@buaa.edu.cn', "15000000000", "This is a bio", "path", false); ##password:123456
INSERT INTO `userinfo`
VALUES (3, 'user', '20000001@buaa.edu.cn', "15000000001", "This is a bio", "path", false); ##password:123456

CREATE TABLE `animal`
(
    `id`        int(32)         NOT NULL AUTO_INCREMENT,
    `name`      varchar(32)     NOT NULL UNIQUE,
    `intro`     varchar(256)    NOT NULL DEFAULT "管理员是个OP，还没设置动物介绍",
    `adopted`   boolean         NOT NULL DEFAULT false,
    `avatar`    varchar(128)     DEFAULT "/static/images/user/default.png",
    PRIMARY KEY (`id`)
);

INSERT INTO animal (`id`,`name`,`intro`,`adopted`,`avatar`) values (1, "馆长", "大家好，我是馆长",false,NULL);
INSERT INTO animal (`id`,`name`,`intro`,`adopted`,`avatar`) values (20, "提米", "嗨，我素提米",true,NULL);

CREATE TABLE `tweet`
(
    `id`           int(32)  NOT NULL AUTO_INCREMENT,
    `user_id`      int(32)  NOT NULL,
    `title`        varchar(100)      DEFAULT NULL,
    `content`      varchar(1024)     NOT NULL DEFAULT "",
    `images`       varchar(640)      DEFAULT NULL,
    `time`         datetime NOT NULL DEFAULT now(),
    `views`        int(32)  NOT NULL DEFAULT 0,
    `views_weekly` int(32)  NOT NULL DEFAULT 0,
    `likes`        int(32)  NOT NULL DEFAULT 0,
    `stars`        int(32)  NOT NULL DEFAULT 0,
    `comments`     int(32)  NOT NULL DEFAULT 0,
    `is_help`      boolean  NOT NULL DEFAULT FALSE,
    `solved`       boolean  NOT NULL DEFAULT FALSE,
    `censored`     int(32)  NOT NULL DEFAULT 0,
    `published`    boolean  NOT NULL DEFAULT FALSE,
    `deleted`      boolean  NOT NULL DEFAULT FALSE,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
);

insert into tweet (user_id,title,`time`,content,is_help,published,censored) values (3, "hhhh", now(),"这是一个帖子",false,true,1);
insert into tweet (user_id,title,`time`,content,is_help,published,censored) values (3, "hhhh2", now(),"这是另一个帖子",false,true,2);
insert into tweet (user_id,title,`time`,content,is_help,published,censored) values (3, "help", now(),"这是一个求助帖",true,true,1);
insert into tweet (user_id,title,`time`,content,is_help,published,censored) values (3, "help2", now(),"这是另一个求助帖",true,true,0);
insert into tweet (user_id,title,`time`,content,is_help,published,censored) values (3, "从现在开始，本贴升级为原神贴", now(),"为贯彻原神为本群核心游戏的思想，进一步深化原神群改革，争取把我群升级为原神群，现对各群员提出以下要求:
1、本群其他游戏玩家跟原神玩家说话前要添加敬语。
2、本群原神玩家对话途中其他游戏玩家不允许插嘴。
3、其他游戏玩家每天早晚都要向本群原神玩家问好。
4、非原神玩家发言控制在14字以内，必须打标点符号，不允许出现反原言论，不允许使用QQ第一排以下的表情。
5、非原神玩家不允许发表情包。
6、非原神玩家发图或语音必须征得原神玩家同意。
7、非原神玩家一天只能发10句话，超过必禁。
8、22: 00后对非原神玩家实行宵禁。",false,true,1);

CREATE TABLE `comment`
(
    `id`       int(32)  NOT NULL AUTO_INCREMENT,
    `user_id`  int(32)  NOT NULL,
    `tweet_id` int(32)  NOT NULL,
    `content`  varchar(1024)     NOT NULL,
    `time`     datetime NOT NULL DEFAULT now(),
    `likes`    int(32)  NOT NULL DEFAULT 0,
    `is_help`  boolean  NOT NULL DEFAULT FALSE,
    `censored` int(32)  NOT NULL DEFAULT 0,
    `deleted`  boolean  NOT NULL DEFAULT FALSE,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`),
    FOREIGN KEY (`tweet_id`) REFERENCES `tweet` (`id`)
);

insert into comment (user_id,tweet_id,content) values (3,1,"你在h什么？");
insert into comment (user_id,tweet_id,content) values (3,2,"太长了，不看");
insert into comment (user_id,tweet_id,content,is_help) values (3,3,"你说得对，但是原神是一款开放世界第一人称射击游戏",true);
insert into comment (user_id,tweet_id,content,is_help) values (3,3,"现在，我要点名一个游戏",true);
insert into `comment` (user_id, tweet_id, content, time, censored) values (3, 1, "这个帖子不错", now(), 1);
insert into `comment` (user_id, tweet_id, content, time, censored) values (2, 1, "我是管理员", now(), 1);
insert into `comment` (user_id, tweet_id, content, time, censored) values (3, 1, "你再看？", now(), 1);

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
    `time`       datetime NOT NULL DEFAULT now(),
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

insert into tag (content) values ("馆长");
insert into tag (content) values ("提米");
insert into tag (content) values ("狗");
insert into tag (content) values ("猫");
insert into tag (content) values ("白色");
insert into tag (content) values ("原神");

CREATE TABLE `tweettag`
(
    `tweet_id` int(32) NOT NULL,
    `tag_id`   int(32) NOT NULL,
    PRIMARY KEY (`tweet_id`, `tag_id`),
    FOREIGN KEY (tweet_id) REFERENCES tweet (id),
    FOREIGN KEY (tag_id) REFERENCES tag (id)
);

insert into tweettag(tweet_id,tag_id) values (1,1);
insert into tweettag(tweet_id,tag_id) values (1,2);
insert into tweettag(tweet_id,tag_id) values (2,1);
insert into tweettag(tweet_id,tag_id) values (3,4);
insert into tweettag(tweet_id,tag_id) values (4,2);
insert into tweettag(tweet_id,tag_id) values (4,4);
insert into tweettag(tweet_id,tag_id) values (4,5);
insert into tweettag(tweet_id,tag_id) values (5,6);

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
    `content` varchar(1024)    NOT NULL,
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

CREATE TABLE `adoption`
(
    `id`        int(32)         NOT NULL AUTO_INCREMENT,
    `user_id`   int(32)         NOT NULL,
    `animal_id` int(32)         NOT NULL,
    `reason`    varchar(1024)   NOT NULL,
    `censored`  int(32)         NOT NULL DEFAULT 0,
    `time`      DATETIME        NOT NULL DEFAULT now(),
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `sys_user`(`id`),
    FOREIGN KEY (`animal_id`) REFERENCES `animal`(`id`)
);

insert into adoption (user_id,animal_id,reason) values (3,1,"只能说这就是原神给我的骄傲的资本");

DROP EVENT IF EXISTS clean_verification_event;
CREATE EVENT clean_verification_event
    ON SCHEDULE EVERY 20 MINUTE STARTS NOW()
    DO delete
       from verification
       where TIMESTAMPDIFF(MINUTE, start_time, NOW()) > 10;
