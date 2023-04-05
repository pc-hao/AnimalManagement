CREATE TABLE `animal`
(
    `id`        int(32)         NOT NULL AUTO_INCREMENT,
    `name`      varchar(32)     NOT NULL UNIQUE,
    `intro`     varchar(256)    NOT NULL,
    `adopted`   boolean         NOT NULL,
    PRIMARY KEY (`id`)
);
