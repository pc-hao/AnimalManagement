DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user`
(
    `id`       int(32)      NOT NULL AUTO_INCREMENT,
    `username` varchar(100) NOT NULL UNIQUE,
    `password` varchar(100) NOT NULL,
    `status`   varchar(100) comment 'NORMAL正常  PROHIBIT禁用',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4;

INSERT INTO `sys_user`
VALUES (2, 'admin', '$2a$10$5T851lZ7bc2U87zjt/9S6OkwmLW62tLeGLB2aCmq3XRZHA7OI7Dqa', 'NORMAL'); ##password:123456
INSERT INTO `sys_user`
VALUES (3, 'user', '$2a$10$szHoqQ64g66PymVJkip98.Fap21Csy8w.RD8v5Dhq08BMEZ9KaSmS', 'NORMAL'); ##password:123456
