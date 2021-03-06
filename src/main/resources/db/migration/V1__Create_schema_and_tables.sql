-- MySQL Script generated by MySQL Workbench
-- 09/17/15 11:55:46
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema NextBook
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema NextBook
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `NextBook` DEFAULT CHARACTER SET utf8 ;
USE `NextBook` ;

-- -----------------------------------------------------
-- Table `NextBook`.`ROLE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `NextBook`.`ROLE` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `NextBook`.`USER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `NextBook`.`USER` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NULL,
  `active` TINYINT(1) NULL DEFAULT true,
  `role_id` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  INDEX `user_to_role_idx` (`role_id` ASC),
  CONSTRAINT `user_to_role`
  FOREIGN KEY (`role_id`)
  REFERENCES `NextBook`.`ROLE` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `NextBook`.`PERMISSIONS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `NextBook`.`PERMISSIONS` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `NextBook`.`ROLE_PERMISSIONS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `NextBook`.`ROLE_PERMISSIONS` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `role_id` INT NOT NULL,
  `permission_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `role_to_permission_idx` (`role_id` ASC),
  INDEX `permission_to_role_idx` (`permission_id` ASC),
  CONSTRAINT `role_to_permission`
  FOREIGN KEY (`role_id`)
  REFERENCES `NextBook`.`ROLE` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `permission_to_role`
  FOREIGN KEY (`permission_id`)
  REFERENCES `NextBook`.`PERMISSIONS` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `NextBook`.`CATEGORY`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `NextBook`.`CATEGORY` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name_ua` VARCHAR(150) NOT NULL,
  `order` INT UNSIGNED NULL,
  `link` VARCHAR(45) NULL,
  `name_ru` VARCHAR(150) NULL,
  `name_en` VARCHAR(150) NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `NextBook`.`SUBCATEGORY`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `NextBook`.`SUBCATEGORY` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name_ua` VARCHAR(150) NULL,
  `order` INT NULL,
  `link` VARCHAR(45) NULL,
  `category_id` INT NULL,
  `name_ru` VARCHAR(150) NULL,
  `name_en` VARCHAR(150) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_subcategory_to_category_idx` (`category_id` ASC),
  CONSTRAINT `fk_subcategory_to_category`
  FOREIGN KEY (`category_id`)
  REFERENCES `NextBook`.`CATEGORY` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `NextBook`.`PUBLISHER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `NextBook`.`PUBLISHER` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name_ua` VARCHAR(250) NOT NULL,
  `description` TEXT NULL,
  `name_ru` VARCHAR(250) NULL,
  `name_en` VARCHAR(250) NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `NextBook`.`BOOK`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `NextBook`.`BOOK` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `ISBN` VARCHAR(50) NOT NULL,
  `name_ua` TEXT NOT NULL,
  `name_en` TEXT NULL,
  `name_ru` TEXT NULL,
  `subcategory_id` INT NOT NULL,
  `eighteen_plus` TINYINT(1) NULL DEFAULT false,
  `year_of_publication` INT NOT NULL,
  `publisher_id` INT NOT NULL,
  `language` VARCHAR(10) NOT NULL,
  `type_of_book` VARCHAR(50) NOT NULL,
  `pages_number` INT NULL,
  `description_ua` TEXT NULL,
  `description_en` TEXT NULL,
  `description_ru` TEXT NULL,
  `link` VARCHAR(45) NULL,
  `link_to_storage` VARCHAR(150) NULL,
  `number_of_img_for_cover` INT NULL,
  `number_of_img_in_galery` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_book_to_subcategory_idx` (`subcategory_id` ASC),
  INDEX `fk_book_to_publisher_idx` (`publisher_id` ASC),
  CONSTRAINT `fk_book_to_subcategory`
  FOREIGN KEY (`subcategory_id`)
  REFERENCES `NextBook`.`SUBCATEGORY` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_book_to_publisher`
  FOREIGN KEY (`publisher_id`)
  REFERENCES `NextBook`.`PUBLISHER` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `NextBook`.`AUTHOR`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `NextBook`.`AUTHOR` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name_ua` VARCHAR(60) NOT NULL,
  `last_name_ua` VARCHAR(60) NULL,
  `first_name_ru` VARCHAR(60) NULL,
  `last_name_ru` VARCHAR(60) NULL,
  `first_name_en` VARCHAR(60) NULL,
  `last_name_en` VARCHAR(60) NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `NextBook`.`USERS_TO_PUBLISHER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `NextBook`.`USERS_TO_PUBLISHER` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `publisher_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_user_to_publisher_idx` (`user_id` ASC),
  INDEX `fk_publisher_to_user_idx` (`publisher_id` ASC),
  CONSTRAINT `fk_user_to_publisher`
  FOREIGN KEY (`user_id`)
  REFERENCES `NextBook`.`USER` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_publisher_to_user`
  FOREIGN KEY (`publisher_id`)
  REFERENCES `NextBook`.`PUBLISHER` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `NextBook`.`KEYWORDS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `NextBook`.`KEYWORDS` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `keyword` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `keyword_UNIQUE` (`keyword` ASC))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `NextBook`.`KEYWORDS_TO_BOOK`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `NextBook`.`KEYWORDS_TO_BOOK` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `book_id` INT NOT NULL,
  `keyword_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_book_to_keyword_idx` (`book_id` ASC),
  INDEX `fk_keyword_to_book_idx` (`keyword_id` ASC),
  CONSTRAINT `fk_book_to_keyword`
  FOREIGN KEY (`book_id`)
  REFERENCES `NextBook`.`BOOK` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_keyword_to_book`
  FOREIGN KEY (`keyword_id`)
  REFERENCES `NextBook`.`KEYWORDS` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `NextBook`.`AUTHORS_TO_BOOK`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `NextBook`.`AUTHORS_TO_BOOK` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `book_id` INT NOT NULL,
  `author_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_book_to_author_idx` (`book_id` ASC),
  INDEX `fk_author_to_book_idx` (`author_id` ASC),
  CONSTRAINT `fk_book_to_author`
  FOREIGN KEY (`book_id`)
  REFERENCES `NextBook`.`BOOK` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_author_to_book`
  FOREIGN KEY (`author_id`)
  REFERENCES `NextBook`.`AUTHOR` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
