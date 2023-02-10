

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema digitalbooking
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `digitalbooking` ;

-- -----------------------------------------------------
-- Schema digitalbooking
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `digitalbooking` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `digitalbooking` ;

-- -----------------------------------------------------
-- Table `digitalbooking`.`categorias`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `digitalbooking`.`categorias` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `titulo` VARCHAR(50) NOT NULL,
  `descricao` VARCHAR(150) NOT NULL,
  `url_imagem` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `digitalbooking`.`caracteristicas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `digitalbooking`.`caracteristicas` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50) NOT NULL,
  `icone` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `digitalbooking`.`imagens`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `digitalbooking`.`imagens` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `titulo` VARCHAR(50) NOT NULL,
  `url` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `digitalbooking`.`funcoes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `digitalbooking`.`funcoes` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `digitalbooking`.`usuarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `digitalbooking`.`usuarios` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50) NOT NULL,
  `sobrenome` VARCHAR(50) NOT NULL,
  `email` VARCHAR(50) NOT NULL,
  `senha` VARCHAR(45) NOT NULL,
  `funcoes_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_usuarios_funcoes`
    FOREIGN KEY (`funcoes_id`)
    REFERENCES `digitalbooking`.`funcoes` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_usuarios_funcoes_idx` ON `digitalbooking`.`usuarios` (`funcoes_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `digitalbooking`.`reservas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `digitalbooking`.`reservas` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `hora_reserva` DATETIME NOT NULL,
  `data_inicio` DATE NOT NULL,
  `data_final` DATE NOT NULL,
  `usuarios_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_reservas_usuarios1`
    FOREIGN KEY (`usuarios_id`)
    REFERENCES `digitalbooking`.`usuarios` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_reservas_usuarios1_idx` ON `digitalbooking`.`reservas` (`usuarios_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `digitalbooking`.`produtos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `digitalbooking`.`produtos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `descricao` VARCHAR(150) NOT NULL,
  `reservas_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_produtos_reservas1`
    FOREIGN KEY (`reservas_id`)
    REFERENCES `digitalbooking`.`reservas` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_produtos_reservas1_idx` ON `digitalbooking`.`produtos` (`reservas_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `digitalbooking`.`cidades`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `digitalbooking`.`cidades` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50) NOT NULL,
  `pais` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
