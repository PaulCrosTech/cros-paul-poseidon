DROP SCHEMA IF EXISTS `integration-tests-db`;
CREATE SCHEMA IF NOT EXISTS `integration-tests-db` DEFAULT CHARACTER SET utf8;
USE `integration-tests-db`;

CREATE TABLE bid_list
(
    bid_id         INT         NOT NULL AUTO_INCREMENT,
    account        VARCHAR(30) NOT NULL,
    type           VARCHAR(30) NOT NULL,
    bid_quantity   DOUBLE,
    ask_quantity   DOUBLE,
    bid            DOUBLE,
    ask            DOUBLE,
    benchmark      VARCHAR(125),
    bid_list_date  TIMESTAMP,
    commentary     VARCHAR(125),
    security       VARCHAR(125),
    status         VARCHAR(10),
    trader         VARCHAR(125),
    book           VARCHAR(125),
    creation_name  VARCHAR(125),
    creation_date  TIMESTAMP,
    revision_name  VARCHAR(125),
    revision_date  TIMESTAMP,
    deal_name      VARCHAR(125),
    deal_type      VARCHAR(125),
    source_list_id VARCHAR(125),
    side           VARCHAR(125),

    PRIMARY KEY (bid_id)
);

CREATE TABLE trade
(
    trade_id       INT         NOT NULL AUTO_INCREMENT,
    account        VARCHAR(30) NOT NULL,
    type           VARCHAR(30) NOT NULL,
    buy_quantity   DOUBLE,
    sell_quantity  DOUBLE,
    buy_price      DOUBLE,
    sell_price     DOUBLE,
    trade_date     TIMESTAMP,
    security       VARCHAR(125),
    status         VARCHAR(10),
    trader         VARCHAR(125),
    benchmark      VARCHAR(125),
    book           VARCHAR(125),
    creation_name  VARCHAR(125),
    creation_date  TIMESTAMP,
    revision_name  VARCHAR(125),
    revision_date  TIMESTAMP,
    deal_name      VARCHAR(125),
    deal_type      VARCHAR(125),
    source_list_id VARCHAR(125),
    side           VARCHAR(125),

    PRIMARY KEY (trade_id)
);

CREATE TABLE curve_point
(
    curve_point_id INT NOT NULL AUTO_INCREMENT,
    curve_id       tinyint,
    as_of_date     TIMESTAMP,
    term           DOUBLE,
    value          DOUBLE,
    creation_date  TIMESTAMP,

    PRIMARY KEY (curve_point_id)
);

CREATE TABLE rating
(
    rating_id     INT NOT NULL AUTO_INCREMENT,
    moodys_rating VARCHAR(125),
    sand_p_rating VARCHAR(125),
    fitch_rating  VARCHAR(125),
    order_number  tinyint,

    PRIMARY KEY (rating_id)
);

CREATE TABLE rule_name
(
    rule_name_id INT NOT NULL AUTO_INCREMENT,
    name         VARCHAR(125),
    description  VARCHAR(125),
    json         VARCHAR(125),
    template     VARCHAR(512),
    sql_str      VARCHAR(125),
    sql_part     VARCHAR(125),

    PRIMARY KEY (rule_name_id)
);

CREATE TABLE user
(
    user_id  INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(125),
    password VARCHAR(125),
    fullname VARCHAR(125),
    role     VARCHAR(125),

    PRIMARY KEY (user_id),
    UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE
);

INSERT INTO `user` (`fullname`, `password`, `role`, `username`)
VALUES ('User Fullname', '$2a$10$sUlcQ2JEzBvNmEhHA/2gJeBuRYKDyETYT2fzYB6csZBaR7K8I7UXS', 'USER', 'user.user');
INSERT INTO `user` (`fullname`, `password`, `role`, `username`)
VALUES ('Admin  Fullname', '$2a$10$sUlcQ2JEzBvNmEhHA/2gJeBuRYKDyETYT2fzYB6csZBaR7K8I7UXS', 'ADMIN', 'admin.admin');


INSERT INTO `bid_list` (`bid_id`, `account`, `ask`, `ask_quantity`, `benchmark`, `bid`, `bid_list_date`, `bid_quantity`,
                        `book`, `commentary`, `creation_date`, `creation_name`, `deal_name`, `deal_type`,
                        `revision_date`, `revision_name`, `security`, `side`, `source_list_id`, `status`, `trader`,
                        `type`)
VALUES (19, 'Account 1', NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NOW(), NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, 'Type 1');
INSERT INTO `bid_list` (`bid_id`, `account`, `ask`, `ask_quantity`, `benchmark`, `bid`, `bid_list_date`, `bid_quantity`,
                        `book`, `commentary`, `creation_date`, `creation_name`, `deal_name`, `deal_type`,
                        `revision_date`, `revision_name`, `security`, `side`, `source_list_id`, `status`, `trader`,
                        `type`)
VALUES (20, 'Account 2', NULL, NULL, NULL, NULL, NULL, 2, NULL, NULL, NOW(), NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, 'Type 2');
INSERT INTO `bid_list` (`bid_id`, `account`, `ask`, `ask_quantity`, `benchmark`, `bid`, `bid_list_date`, `bid_quantity`,
                        `book`, `commentary`, `creation_date`, `creation_name`, `deal_name`, `deal_type`,
                        `revision_date`, `revision_name`, `security`, `side`, `source_list_id`, `status`, `trader`,
                        `type`)
VALUES (21, 'Account 3', NULL, NULL, NULL, NULL, NULL, 3, NULL, NULL, NOW(), NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, 'Type 3');


INSERT INTO `curve_point` (`curve_point_id`, `as_of_date`, `creation_date`, `curve_id`, `term`, `value`)
VALUES (1, NULL, NOW(), 10, 11, 12);
INSERT INTO `curve_point` (`curve_point_id`, `as_of_date`, `creation_date`, `curve_id`, `term`, `value`)
VALUES (2, NULL, NOW(), 20, 21, 22);
INSERT INTO `curve_point` (`curve_point_id`, `as_of_date`, `creation_date`, `curve_id`, `term`, `value`)
VALUES (6, NULL, NOW(), 30, 31, 32);


INSERT INTO `rating` (`rating_id`, `fitch_rating`, `moodys_rating`, `order_number`, `sand_p_rating`)
VALUES (1, 'Fitch 1', 'Moody 1', 1, 'SandP 1');
INSERT INTO `rating` (`rating_id`, `fitch_rating`, `moodys_rating`, `order_number`, `sand_p_rating`)
VALUES (3, 'Fitch 2', 'Moody 2', 2, 'SandP 2');
INSERT INTO `rating` (`rating_id`, `fitch_rating`, `moodys_rating`, `order_number`, `sand_p_rating`)
VALUES (4, 'Fitch 3', 'Moody 3', 3, 'SandP 3');

INSERT INTO `rule_name` (`rule_name_id`, `description`, `json`, `name`, `sql_part`, `sql_str`, `template`)
VALUES (1, 'Desc 1', 'Json 1', 'Name 1', 'SQL P 1', 'SQL Str 1', 'T 1');
INSERT INTO `rule_name` (`rule_name_id`, `description`, `json`, `name`, `sql_part`, `sql_str`, `template`)
VALUES (3, 'Desc 2', 'Json 2', 'Name 2', 'SQL Part 2', 'SQL STR 2', 'T 2');
INSERT INTO `rule_name` (`rule_name_id`, `description`, `json`, `name`, `sql_part`, `sql_str`, `template`)
VALUES (4, 'Desc 3', 'Json 3', 'Name 3', 'SQL PART 3', 'SQL STR 3', 'T 3');

INSERT INTO `trade` (`trade_id`, `account`, `benchmark`, `book`, `buy_price`, `buy_quantity`, `creation_date`,
                     `creation_name`, `deal_name`, `deal_type`, `revision_date`, `revision_name`, `security`,
                     `sell_price`, `sell_quantity`, `side`, `source_list_id`, `status`, `trade_date`, `trader`, `type`)
VALUES (1, 'T Account 1', NULL, NULL, NULL, 1, NOW(), NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, 'T Type 1');
INSERT INTO `trade` (`trade_id`, `account`, `benchmark`, `book`, `buy_price`, `buy_quantity`, `creation_date`,
                     `creation_name`, `deal_name`, `deal_type`, `revision_date`, `revision_name`, `security`,
                     `sell_price`, `sell_quantity`, `side`, `source_list_id`, `status`, `trade_date`, `trader`, `type`)
VALUES (3, 'T Account 2', NULL, NULL, NULL, 2, NOW(), NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, 'T Type 2');
INSERT INTO `trade` (`trade_id`, `account`, `benchmark`, `book`, `buy_price`, `buy_quantity`, `creation_date`,
                     `creation_name`, `deal_name`, `deal_type`, `revision_date`, `revision_name`, `security`,
                     `sell_price`, `sell_quantity`, `side`, `source_list_id`, `status`, `trade_date`, `trader`, `type`)
VALUES (4, 'T Account 3', NULL, NULL, NULL, 3, NOW(), NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, 'T Type 3');
