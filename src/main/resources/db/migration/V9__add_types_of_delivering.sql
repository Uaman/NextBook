DROP TABLE IF exists `NextBook`.`ORDERS_WITH_BOOK`;
DROP TABLE IF exists `NextBook`.`ORDERS`;
DROP TABLE IF exists `NextBook`.`ORDER_STATES`;
DROP TABLE IF exists `NextBook`.`TYPES_OF_PAYMENT`;
DROP TABLE IF exists `NextBook`.`TYPES_OF_DELIVERING`;
DROP TABLE IF exists `NextBook`.`USERS_ADRESSES`;
DROP TABLE IF exists `NextBook`.`ADRESSES`;


-- -----------------------------------------------------
-- Table `NextBook`.`ORDER_STATES`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `NextBook`.`ORDER_STATES` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `state_of_the_order` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `NextBook`.`TYPES_OF_PAYMENT`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `NextBook`.`TYPES_OF_PAYMENT` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name_of_type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `NextBook`.`TYPES_OF_DELIVERING`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `NextBook`.`TYPES_OF_DELIVERING` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `type_name` VARCHAR(45) NOT NULL,
  `description` TEXT NOT NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `NextBook`.`ORDERS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `NextBook`.`ORDERS` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `state_id` INT NOT NULL,
  `is_paid` BINARY NOT NULL DEFAULT false,
  `user_id` INT(11) NOT NULL,
  `date_of_order` DATETIME NOT NULL,
  `type_of_payment_id` INT NOT NULL,
  `description` TEXT NULL,
  `delivering_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `user_id_for_orders_idx` (`user_id` ASC),
  INDEX `state_of_the_orders_id_idx` (`state_id` ASC),
  INDEX `type_of_payment_for_orders_idx` (`type_of_payment_id` ASC),
  INDEX `type_of_delivering_for_orders_idx` (`delivering_id` ASC),
  CONSTRAINT `user_id_for_orders`
  FOREIGN KEY (`user_id`)
  REFERENCES `NextBook`.`USER` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `state_of_the_orders_id`
  FOREIGN KEY (`state_id`)
  REFERENCES `NextBook`.`ORDER_STATES` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `type_of_payment_for_orders`
  FOREIGN KEY (`type_of_payment_id`)
  REFERENCES `NextBook`.`TYPES_OF_PAYMENT` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `type_of_delivering_for_orders`
  FOREIGN KEY (`delivering_id`)
  REFERENCES `NextBook`.`TYPES_OF_DELIVERING` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `NextBook`.`ORDERS_WITH_BOOK`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `NextBook`.`ORDERS_WITH_BOOK` (
  `ORDERS_id` INT NOT NULL,
  `BOOK_id` INT NOT NULL,
  `number_of_books` INT NOT NULL,
  `id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  INDEX `fk_ORDERS_has_BOOK_BOOK1_idx` (`BOOK_id` ASC),
  INDEX `fk_ORDERS_has_BOOK_ORDERS1_idx` (`ORDERS_id` ASC),
  CONSTRAINT `fk_ORDERS_has_BOOK_ORDERS1`
  FOREIGN KEY (`ORDERS_id`)
  REFERENCES `NextBook`.`ORDERS` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ORDERS_has_BOOK_BOOK1`
  FOREIGN KEY (`BOOK_id`)
  REFERENCES `NextBook`.`BOOK` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `NextBook`.`ADRESSES`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `NextBook`.`ADRESSES` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `city` VARCHAR(50) NULL,
  `branch_number` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `NextBook`.`USERS_ADRESSES`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `NextBook`.`USERS_ADRESSES` (
  `USER_id` INT NOT NULL,
  `Address_id` INT NOT NULL,
  PRIMARY KEY (`USER_id`, `Address_id`),
  INDEX `fk_USER_has_Streets_Streets1_idx` (`Address_id` ASC),
  INDEX `fk_USER_has_Streets_USER1_idx` (`USER_id` ASC),
  CONSTRAINT `fk_USER_has_Streets_USER1`
  FOREIGN KEY (`USER_id`)
  REFERENCES `NextBook`.`USER` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_USER_has_Streets_Streets1`
  FOREIGN KEY (`Address_id`)
  REFERENCES `NextBook`.`ADRESSES` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;
