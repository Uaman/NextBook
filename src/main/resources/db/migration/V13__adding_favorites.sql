DROP TABLE IF exists `nextbook`.`FAVORITES`;

-- -----------------------------------------------------
-- Table `NextBook`.`FAVORITES`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nextbook`.`FAVORITES` (
  `book_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`book_id`, `user_id`),
  INDEX `fk_user_to_books` (`user_id` ASC),
  INDEX `fk_book_to_users` (`book_id` ASC),
  CONSTRAINT `book_id`
    FOREIGN KEY (`book_id`)
    REFERENCES `nextbook`.`BOOK` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `nextbook`.`USER` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;