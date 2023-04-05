CREATE TABLE `tweettag`
(
    `tweet_id`  int(32)         NOT NULL,
    `tag_id`    int(32)         NOT NULL,
    PRIMARY KEY (`tweet_id`, `tag_id`),
    FOREIGN KEY (tweet_id) REFERENCES tweet(id),
    FOREIGN KEY (tag_id) REFERENCES tag(id)
);