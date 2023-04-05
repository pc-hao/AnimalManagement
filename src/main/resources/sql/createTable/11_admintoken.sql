CREATE TABLE `admintoken`
(
    `token`         int(32)         NOT NULL,
    `admin_id`      int(32)         NOT NULL,
    PRIMARY KEY (`token`),
    FOREIGN KEY (`admin_id`) REFERENCES `admin`(`id`)
);