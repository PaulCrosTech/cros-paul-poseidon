USE `integration-tests-db`;

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