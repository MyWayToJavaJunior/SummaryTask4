SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `university_admission` ;
CREATE SCHEMA IF NOT EXISTS `university_admission` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `university_admission` ;

-- -----------------------------------------------------
-- Table `university_admission`.`User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `university_admission`.`User` ;

CREATE TABLE IF NOT EXISTS `university_admission`.`User` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `role` ENUM('client', 'admin') NOT NULL,
  `lang` ENUM('ru','en') NOT NULL DEFAULT 'ru',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC));


-- -----------------------------------------------------
-- Table `university_admission`.`Entrant`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `university_admission`.`Entrant` ;

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
DROP TABLE IF EXISTS `university_admission`.`Faculty` ;

CREATE TABLE IF NOT EXISTS `university_admission`.`Faculty` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name_ru` VARCHAR(100) NOT NULL,
  `name_eng` VARCHAR(100) NOT NULL,
  `total_seats` TINYINT UNSIGNED NOT NULL,
  `budget_seats` TINYINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name_ru` ASC),
  UNIQUE INDEX `name_eng_UNIQUE` (`name_eng` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `university_admission`.`Subject`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `university_admission`.`Subject` ;

CREATE TABLE IF NOT EXISTS `university_admission`.`Subject` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name_ru` VARCHAR(45) NOT NULL,
  `name_eng` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name_ru` ASC),
  UNIQUE INDEX `name_eng_UNIQUE` (`name_eng` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `university_admission`.`Faculty_Entrants`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `university_admission`.`Faculty_Entrants` ;

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
DROP TABLE IF EXISTS `university_admission`.`Faculty_Subjects` ;

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
DROP TABLE IF EXISTS `university_admission`.`Mark` ;

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

USE `university_admission` ;

-- -----------------------------------------------------
-- Placeholder table for view `university_admission`.`faculties_report_sheet`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `university_admission`.`faculties_report_sheet` (`facultyId` INT, `first_name` INT, `last_name` INT, `email` INT, `isBlocked` INT, `preliminary_sum` INT, `diploma_sum` INT, `total_sum` INT);

-- -----------------------------------------------------
-- Placeholder table for view `university_admission`.`entrant_marks_sum`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `university_admission`.`entrant_marks_sum` (`facultyId` INT, `entrantId` INT, `preliminary_sum` INT, `diploma_sum` INT);

-- -----------------------------------------------------
-- View `university_admission`.`faculties_report_sheet`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `university_admission`.`faculties_report_sheet` ;
DROP TABLE IF EXISTS `university_admission`.`faculties_report_sheet`;
USE `university_admission`;
CREATE  OR REPLACE VIEW university_admission.`faculties_report_sheet` AS
    SELECT
        `facultyId`,
        university_admission.user.first_name,
        university_admission.user.last_name,
        university_admission.user.email,
        university_admission.entrant.isBlocked,
        `preliminary_sum`,
        `diploma_sum`,
        `preliminary_sum` + `diploma_sum` AS `total_sum`
    FROM
        university_admission.`entrant_marks_sum`
            INNER JOIN
        university_admission.faculty ON `entrant_marks_sum`.`entrantId` = university_admission.faculty.id
            INNER JOIN
        university_admission.entrant ON `entrantId` = university_admission.entrant.id
            INNER JOIN
        university_admission.user ON university_admission.entrant.User_idUser = university_admission.user.id
    ORDER BY isBlocked ASC , `total_sum` DESC;



-- -----------------------------------------------------
-- View `university_admission`.`entrant_marks_sum`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `university_admission`.`entrant_marks_sum` ;
DROP TABLE IF EXISTS `university_admission`.`entrant_marks_sum`;
USE `university_admission`;
CREATE  OR REPLACE VIEW `entrant_marks_sum` AS
    SELECT
        university_admission.faculty_entrants.Faculty_idFaculty AS `facultyId`,
        university_admission.mark.Entrant_idEntrant AS `entrantId`,
        SUM(CASE `exam_type`
            WHEN 'preliminary' THEN university_admission.mark.value
            ELSE 0
        END) AS `preliminary_sum`,
        SUM(CASE `exam_type`
            WHEN 'diploma' THEN university_admission.mark.value
            ELSE 0
        END) AS `diploma_sum`
    FROM
        university_admission.faculty_entrants
            INNER JOIN
        university_admission.mark ON university_admission.faculty_entrants.Entrant_idEntrant = university_admission.mark.Entrant_idEntrant
    GROUP BY faculty_entrants.Faculty_idFaculty,entrantId;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
USE `university_admission`;

DELIMITER $$

USE `university_admission`$$
DROP TRIGGER IF EXISTS `university_admission`.`Entrant_BINS` $$
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
DROP TRIGGER IF EXISTS `university_admission`.`Faculty_BINS` $$
USE `university_admission`$$
CREATE TRIGGER `Faculty_BINS` BEFORE INSERT ON `Faculty` FOR EACH ROW
BEGIN
    -- throw exception when condition not satisfied
    IF (NEW.budget_seats > NEW.total_seats) THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Budget seats amount must be lower then total.', MYSQL_ERRNO = 1001;
    END IF;
END;$$


DELIMITER ;

USE `university_admission`$$
DROP TRIGGER IF EXISTS `university_admission`.`Mark_BEFORE_INSERT` $$
USE `university_admission`$$
CREATE DEFINER = CURRENT_USER TRIGGER `university_admission`.`Mark_BEFORE_INSERT` BEFORE INSERT ON `Mark` FOR EACH ROW
    BEGIN
    -- throw exception when condition not satisfied
    IF (SELECT mark.`Entrant_idEntrant`,mark.`Faculty_idFaculty`,mark.`exam_type` FROM mark WHERE mark.`Entrant_idEntrant`= NEW.`Entrant_idEntrant` AND mark.`Faculty_idFaculty`=NEW.`Faculty_idFaculty` AND mark.`exam_type`= NEW.`exam_type` AND NEW.`exam_type`='diploma') IS NOT NULL THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Record with', MYSQL_ERRNO = 1001;
    END IF;
END;$$


DELIMITER ;
