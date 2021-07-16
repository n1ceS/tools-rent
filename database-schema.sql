SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema toolsrent
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `toolsrent` ;

-- -----------------------------------------------------
-- Schema toolsrent
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `toolsrent` DEFAULT CHARACTER SET utf8 ;
USE `toolsrent` ;

-- -----------------------------------------------------
-- Table `toolsrent`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `toolsrent`.`users` ;

CREATE TABLE IF NOT EXISTS `toolsrent`.`users` (
  `username` VARCHAR(25) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`username`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `toolsrent`.`userroles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `toolsrent`.`userroles` ;

CREATE TABLE IF NOT EXISTS `toolsrent`.`userroles` (
  `ID` BIGINT NOT NULL,
  `type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `toolsrent`.`users_to_roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `toolsrent`.`users_to_roles` ;

CREATE TABLE IF NOT EXISTS `toolsrent`.`users_to_roles` (
  `userroles_ID` BIGINT NOT NULL,
  `users_username` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`userroles_ID`, `users_username`),
  INDEX `fk_users_to_roles_userroles_idx` (`userroles_ID` ASC) VISIBLE,
  INDEX `fk_users_to_roles_users1_idx` (`users_username` ASC) VISIBLE,
  CONSTRAINT `fk_users_to_roles_userroles`
    FOREIGN KEY (`userroles_ID`)
    REFERENCES `toolsrent`.`userroles` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_to_roles_users1`
    FOREIGN KEY (`users_username`)
    REFERENCES `toolsrent`.`users` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `toolsrent`.`items`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `toolsrent`.`items` ;

CREATE TABLE IF NOT EXISTS `toolsrent`.`items` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(255) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `toolsrent`.`pricelist`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `toolsrent`.`pricelist` ;

CREATE TABLE IF NOT EXISTS `toolsrent`.`pricelist` (
  `item_id` BIGINT NOT NULL,
  `price` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`item_id`),
  INDEX `fk_pricelist_items1_idx` (`item_id` ASC) VISIBLE,
  CONSTRAINT `fk_pricelist_items1`
    FOREIGN KEY (`item_id`)
    REFERENCES `toolsrent`.`items` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `toolsrent`.`orders`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `toolsrent`.`orders` ;

CREATE TABLE IF NOT EXISTS `toolsrent`.`orders` (
  `ID` BIGINT NOT NULL AUTO_INCREMENT,
  `user_username` VARCHAR(25) NOT NULL,
  `item_id` BIGINT NOT NULL,
  `start_date` TIMESTAMP NOT NULL,
  `end_date` TIMESTAMP NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_order_users1_idx` (`user_username` ASC) VISIBLE,
  INDEX `fk_order_items1_idx` (`item_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_users1`
    FOREIGN KEY (`user_username`)
    REFERENCES `toolsrent`.`users` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_items1`
    FOREIGN KEY (`item_id`)
    REFERENCES `toolsrent`.`items` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `toolsrent`.`order_cost`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `toolsrent`.`order_cost` ;

CREATE TABLE IF NOT EXISTS `toolsrent`.`order_cost` (
  `order_id` BIGINT NOT NULL,
  `total_cost` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`order_id`),
  INDEX `fk_order_cost_order1_idx` (`order_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_cost_order1`
    FOREIGN KEY (`order_id`)
    REFERENCES `toolsrent`.`orders` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `toolsrent`.`userroles'
-- -----------------------------------------------------

INSERT INTO `toolsrent`.`userroles` (`ID`, `type`) VALUES ('1', 'ADMIN');
INSERT INTO `toolsrent`.`userroles` (`ID`, `type`) VALUES ('2', 'USER');
