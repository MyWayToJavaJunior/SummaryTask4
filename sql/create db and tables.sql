SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `university_admission` ;
CREATE SCHEMA IF NOT EXISTS `university_admission` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `university_admission` ;

-- -----------------------------------------------------
-- Table `university_admission`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `university_admission`.`User` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `role` ENUM('client', 'admin') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC));


-- -----------------------------------------------------
-- Table `university_admission`.`Entrant`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `university_admission`.`Entrant` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `city` VARCHAR(45) NOT NULL,
  `district` VARCHAR(45) NOT NULL,
  `school` VARCHAR(45) NOT NULL,
  `User_idUser` INT NOT NULL,
  `isBlocked` TINYINT(1) NOT NULL DEFAULT false,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_Entrant_User1_idx` (`User_idUser` ASC),
  CONSTRAINT `fk_Entrant_User`
    FOREIGN KEY (`User_idUser`)
    REFERENCES `university_admission`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `university_admission`.`Faculty`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `university_admission`.`Faculty` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `total_seats` TINYINT UNSIGNED NOT NULL,
  `budget_seats` TINYINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `university_admission`.`Subject`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `university_admission`.`Subject` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `university_admission`.`Faculty_Entrants`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `university_admission`.`Faculty_Entrants` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Entrant_idEntrant` INT NOT NULL,
  `Faculty_idFaculty` INT NOT NULL,
  PRIMARY KEY (`id`, `Entrant_idEntrant`, `Faculty_idFaculty`),
  INDEX `fk_Entrant_has_Faculty_Faculty1_idx` (`Faculty_idFaculty` ASC),
  INDEX `fk_Entrant_has_Faculty_Entrant1_idx` (`Entrant_idEntrant` ASC),
  UNIQUE INDEX `idFacultyEntrants_UNIQUE` (`id` ASC),
  CONSTRAINT `fk_Entrant_has_Faculty_Entrant1`
    FOREIGN KEY (`Entrant_idEntrant`)
    REFERENCES `university_admission`.`Entrant` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Entrant_has_Faculty_Faculty1`
    FOREIGN KEY (`Faculty_idFaculty`)
    REFERENCES `university_admission`.`Faculty` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `university_admission`.`Faculty_Subjects`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `university_admission`.`Faculty_Subjects` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Faculty_idFaculty` INT NOT NULL,
  `Subject_idSubject` INT NOT NULL,
  PRIMARY KEY (`id`, `Faculty_idFaculty`, `Subject_idSubject`),
  INDEX `fk_Faculty_has_Subject_Subject1_idx` (`Subject_idSubject` ASC),
  INDEX `fk_Faculty_has_Subject_Faculty1_idx` (`Faculty_idFaculty` ASC),
  UNIQUE INDEX `idFacultySubjects_UNIQUE` (`id` ASC),
  CONSTRAINT `fk_Faculty_has_Subject_Faculty1`
    FOREIGN KEY (`Faculty_idFaculty`)
    REFERENCES `university_admission`.`Faculty` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Faculty_has_Subject_Subject1`
    FOREIGN KEY (`Subject_idSubject`)
    REFERENCES `university_admission`.`Subject` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `university_admission`.`Mark`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `university_admission`.`Mark` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Entrant_idEntrant` INT NOT NULL,
  `Subject_idSubject` INT NOT NULL,
  `value` TINYINT UNSIGNED NOT NULL,
  `exam_type` ENUM('diploma','preliminary') NOT NULL,
  PRIMARY KEY (`id`, `Entrant_idEntrant`, `Subject_idSubject`),
  INDEX `fk_Entrant_has_Subject_Subject1_idx` (`Subject_idSubject` ASC),
  INDEX `fk_Entrant_has_Subject_Entrant1_idx` (`Entrant_idEntrant` ASC),
  UNIQUE INDEX `idMark_UNIQUE` (`id` ASC),
  CONSTRAINT `fk_Entrant_has_Subject_Entrant1`
    FOREIGN KEY (`Entrant_idEntrant`)
    REFERENCES `university_admission`.`Entrant` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Entrant_has_Subject_Subject1`
    FOREIGN KEY (`Subject_idSubject`)
    REFERENCES `university_admission`.`Subject` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `university_admission`.`ReportSheet`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `university_admission`.`ReportSheet` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Faculty_idFaculty` INT NOT NULL,
  `Mark_idMark` INT UNSIGNED NOT NULL,
  `Mark_Entrant_idEntrant` INT NOT NULL,
  `Mark_Subject_idSubject` INT NOT NULL,
  PRIMARY KEY (`id`, `Mark_idMark`, `Mark_Entrant_idEntrant`, `Mark_Subject_idSubject`),
  INDEX `fk_ReportSheet_Faculty1_idx` (`Faculty_idFaculty` ASC),
  INDEX `fk_ReportSheet_Mark1_idx` (`Mark_idMark` ASC, `Mark_Entrant_idEntrant` ASC, `Mark_Subject_idSubject` ASC),
  CONSTRAINT `fk_ReportSheet_Faculty1`
    FOREIGN KEY (`Faculty_idFaculty`)
    REFERENCES `university_admission`.`Faculty` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ReportSheet_Mark1`
    FOREIGN KEY (`Mark_idMark` , `Mark_Entrant_idEntrant` , `Mark_Subject_idSubject`)
    REFERENCES `university_admission`.`Mark` (`id` , `Entrant_idEntrant` , `Subject_idSubject`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `university_admission`.`Role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `university_admission`.`Role` (
  `idRole` INT NOT NULL,
  `role_type` VARCHAR(45) NULL,
  PRIMARY KEY (`idRole`),
  UNIQUE INDEX `role_type_UNIQUE` (`role_type` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `university_admission`.`Exam_Type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `university_admission`.`Exam_Type` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `mark_type_UNIQUE` (`name` ASC))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
USE `university_admission`;

DELIMITER $$
USE `university_admission`$$
CREATE TRIGGER `Entrant_BINS` BEFORE INSERT ON `Entrant` FOR EACH ROW
BEGIN
    -- throw exception when condition not satisfied
    IF (SELECT user.role FROM user WHERE NEW.User_idUser=User.id)='admin' THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Admins are not allowed to have Entrant record', MYSQL_ERRNO = 1001;
    END IF;
END;$$

USE `university_admission`$$
CREATE TRIGGER `Faculty_Entrants_BINS` BEFORE INSERT ON `Faculty_Entrants` FOR EACH ROW
BEGIN
    -- throw exception when condition not satisfied
    IF (NEW.Entrant_idEntrant=Faculty_Entrants.Entrant_idEntrant AND NEW.Faculty_idFaculty=Faculty_Entrants.Faculty_idFaculty) THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Entrant is already applied for this faculty', MYSQL_ERRNO = 1001;
    END IF;
END;
$$


DELIMITER ;
