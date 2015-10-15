ALTER TABLE `nextbook`.`BOOK`
  ADD COLUMN `rating` FLOAT AFTER `number_of_img_in_galery`;

ALTER TABLE `nextbook`.`BOOK`
  ADD COLUMN `voted` INTEGER AFTER `rating`;

UPDATE `nextbook`.`BOOK` SET `rating`=0;
UPDATE `nextbook`.`BOOK` SET `voted`=0;

CREATE TABLE IF NOT EXISTS `nextbook`.`USER_VOTE_FOR_BOOK`
(
  `id` INT NOT NULL AUTO_INCREMENT,
  `book_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `mark` FLOAT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE (`book_id`, `user_id`),
  INDEX `fk_book_to_rank_idx` (`book_id` ASC),
  INDEX `fk_user_to_rank_idx` (`user_id` ASC),
  CONSTRAINT `fk_book_to_rank_idx`
  FOREIGN KEY (`book_id`)
  REFERENCES `NextBook`.`BOOK` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_to_rank_idx`
  FOREIGN KEY (`user_id`)
  REFERENCES `NextBook`.`USER` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
 ENGINE = InnoDB;