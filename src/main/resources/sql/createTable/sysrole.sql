DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role`
(
    `id`        int(32) NOT NULL AUTO_INCREMENT,
    `role_name` varchar(100) DEFAULT '',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4;

insert into `sys_role`(`id`, `role_name`)
values (1, 'ADMIN'),
       (2, 'USER');