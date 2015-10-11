ALTER TABLE `nextbook`.`FAVORITES` 
  ADD `id` INT NOT NULL AUTO_INCREMENT,
  DROP PRIMARY KEY,
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE INDEX `unique_couple` (`user_id` ASC, `book_id` ASC);