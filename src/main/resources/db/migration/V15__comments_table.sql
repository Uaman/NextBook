DROP TABLE IF exists `nextbook`.`COMMENTS`;

-- -----------------------------------------------------
-- Table `NextBook`.`COMMENTS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nextbook`.`COMMENTS` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `book_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `comment` TEXT NOT NULL,
  `created_time` BIGINT NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_user_to_comments` (`user_id` ASC),
  INDEX `fk_book_to_comments` (`book_id` ASC),
  CONSTRAINT `comment_to_book_id`
    FOREIGN KEY (`book_id`)
    REFERENCES `nextbook`.`BOOK` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `comment_to_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `nextbook`.`USER` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;