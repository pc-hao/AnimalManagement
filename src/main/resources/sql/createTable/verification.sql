DROP TABLE IF EXISTS `verification`;

CREATE TABLE `verification`
(
    `email`      varchar(50) NOT NULL,
    `veri_code`  varchar(50) NOT NULL,
    `start_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`email`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4;