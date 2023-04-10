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