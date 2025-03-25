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
VALUES ('Administrator', '$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa', 'ADMIN', 'admin');
INSERT INTO `user` (`fullname`, `password`, `role`, `username`)
VALUES ('User', '$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa', 'USER', 'user');
INSERT INTO `user` (`fullname`, `password`, `role`, `username`)
VALUES ('User Fullname', '$2a$10$sUlcQ2JEzBvNmEhHA/2gJeBuRYKDyETYT2fzYB6csZBaR7K8I7UXS', 'USER', 'user.user');
INSERT INTO `user` (`fullname`, `password`, `role`, `username`)
VALUES ('Admin  Fullname', '$2a$10$sUlcQ2JEzBvNmEhHA/2gJeBuRYKDyETYT2fzYB6csZBaR7K8I7UXS', 'ADMIN', 'admin.admin');
