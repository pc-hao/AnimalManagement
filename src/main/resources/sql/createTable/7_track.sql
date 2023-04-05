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