ALTER TABLE `nextbook`.`BOOK`
  ADD COLUMN `status` CHAR(20) AFTER `voted`;


UPDATE `nextbook`.`BOOK` SET `status`='NOT_ACTIVE';