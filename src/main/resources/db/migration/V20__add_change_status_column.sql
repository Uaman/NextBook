ALTER TABLE `nextbook`.`COMMENTS`
  ADD COLUMN `changed_by` CHAR(20) NOT NULL AFTER `status`;


UPDATE `nextbook`.`COMMENTS` SET `changed_by`='NONE';