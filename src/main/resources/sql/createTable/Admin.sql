# 创建表的相关SQL操作
CREATE TABLE `admin`
(
    `id`       int(11)      NOT NULL AUTO_INCREMENT,
    `username` varchar(255) NOT NULL,
    `password` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
);