SET SQL_SAFE_UPDATES = 0;

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `GestaoTurnos` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE IF NOT EXISTS `GestaoTurnos`.`Docentes` (
  `Username` VARCHAR(6) NOT NULL,
  `Nome` VARCHAR(45) NOT NULL,
  `Password` VARCHAR(45) NOT NULL,
  `Email` VARCHAR(60) NOT NULL,
  PRIMARY KEY (`Username`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `GestaoTurnos`.`Alunos` (
  `Username` VARCHAR(6) NOT NULL,
  `Nome` VARCHAR(45) NOT NULL,
  `Password` VARCHAR(45) NOT NULL,
  `Estatuto` VARCHAR(45) NOT NULL,
  `Ano` SMALLINT(6) NOT NULL,
  `Email` VARCHAR(60) NOT NULL,
  PRIMARY KEY (`Username`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `GestaoTurnos`.`Turnos` (
  `Codigo` VARCHAR(20) NOT NULL,
  `Capacidade` SMALLINT(6) NOT NULL,
  `Tipo` VARCHAR(45) NOT NULL,
  `UC_Sigla` VARCHAR(10) NOT NULL,
  `Docente_Username` VARCHAR(6) NOT NULL,
  `DiaSemana` VARCHAR(45) NOT NULL,
  `Hora` VARCHAR(45) NOT NULL,
  `NTrocas` SMALLINT(6) NOT NULL,
  PRIMARY KEY (`Codigo`),
  INDEX `fk_Turno_UC1_idx` (`UC_Sigla` ASC),
  INDEX `fk_Turno_Docente1_idx` (`Docente_Username` ASC),
  CONSTRAINT `fk_Turno_UC1`
    FOREIGN KEY (`UC_Sigla`)
    REFERENCES `GestaoTurnos`.`UCs` (`Sigla`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Turno_Docente1`
    FOREIGN KEY (`Docente_Username`)
    REFERENCES `GestaoTurnos`.`Docentes` (`Username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `GestaoTurnos`.`UCs` (
  `Sigla` VARCHAR(10) NOT NULL,
  `Ano` TINYINT(4) NOT NULL,
  `Semestre` TINYINT(4) NOT NULL,
  `Nome` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`Sigla`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `GestaoTurnos`.`Trocas` (
  `Data` DATE NOT NULL,
  `Turno_Codigo` VARCHAR(20) NOT NULL,
  `Aluno_Username` VARCHAR(6) NOT NULL,
  `TurnosAtual` VARCHAR(20) NOT NULL,
  INDEX `fk_Troca_Turno1_idx` (`Turno_Codigo` ASC),
  INDEX `fk_Troca_Aluno1_idx` (`Aluno_Username` ASC),
  PRIMARY KEY (`Aluno_Username`, `Turno_Codigo`, `TurnosAtual`),
  INDEX `fk_Trocas_Turnos1_idx` (`TurnosAtual` ASC),
  CONSTRAINT `fk_Troca_Turno1`
    FOREIGN KEY (`Turno_Codigo`)
    REFERENCES `GestaoTurnos`.`Turnos` (`Codigo`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Troca_Aluno1`
    FOREIGN KEY (`Aluno_Username`)
    REFERENCES `GestaoTurnos`.`Alunos` (`Username`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Trocas_Turnos1`
    FOREIGN KEY (`TurnosAtual`)
    REFERENCES `GestaoTurnos`.`Turnos` (`Codigo`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `GestaoTurnos`.`AlunosTemTurnos` (
  `Aluno_Username` VARCHAR(6) NOT NULL,
  `Turno_Codigo` VARCHAR(20) NOT NULL,
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  INDEX `fk_Aluno_has_Turno_Turno1_idx` (`Turno_Codigo` ASC),
  PRIMARY KEY (`id`),
  INDEX `fk_Aluno_has_Turno_Aluno1_idx` (`Aluno_Username` ASC),
  CONSTRAINT `fk_Aluno_has_Turno_Aluno1`
    FOREIGN KEY (`Aluno_Username`)
    REFERENCES `GestaoTurnos`.`Alunos` (`Username`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Aluno_has_Turno_Turno1`
    FOREIGN KEY (`Turno_Codigo`)
    REFERENCES `GestaoTurnos`.`Turnos` (`Codigo`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `GestaoTurnos`.`Faltas` (
  `Turno_Codigo` VARCHAR(20) NOT NULL,
  `Aluno_Username` VARCHAR(6) NOT NULL,
  `Dia` DATE NOT NULL,
  INDEX `fk_Faltas_Aluno1_idx` (`Aluno_Username` ASC),
  PRIMARY KEY (`Turno_Codigo`, `Aluno_Username`, `Dia`),
  INDEX `fk_Faltas_Turno1_idx` (`Turno_Codigo` ASC),
  CONSTRAINT `fk_Faltas_Turno1`
    FOREIGN KEY (`Turno_Codigo`)
    REFERENCES `GestaoTurnos`.`Turnos` (`Codigo`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Faltas_Aluno1`
    FOREIGN KEY (`Aluno_Username`)
    REFERENCES `GestaoTurnos`.`Alunos` (`Username`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `GestaoTurnos`.`DocentesTemUCs` (
  `UC_Sigla` VARCHAR(10) NOT NULL,
  `Docente_Username` VARCHAR(6) NOT NULL,
  PRIMARY KEY (`UC_Sigla`, `Docente_Username`),
  INDEX `fk_UC_has_Docente_Docente1_idx` (`Docente_Username` ASC),
  INDEX `fk_UC_has_Docente_UC1_idx` (`UC_Sigla` ASC),
  CONSTRAINT `fk_UC_has_Docente_UC1`
    FOREIGN KEY (`UC_Sigla`)
    REFERENCES `GestaoTurnos`.`UCs` (`Sigla`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_UC_has_Docente_Docente1`
    FOREIGN KEY (`Docente_Username`)
    REFERENCES `GestaoTurnos`.`Docentes` (`Username`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `GestaoTurnos`.`AlunosTemUCs` (
  `Aluno_Username` VARCHAR(6) NOT NULL,
  `UC_Sigla` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`Aluno_Username`, `UC_Sigla`),
  INDEX `fk_Aluno_has_UC_UC1_idx` (`UC_Sigla` ASC),
  INDEX `fk_Aluno_has_UC_Aluno1_idx` (`Aluno_Username` ASC),
  CONSTRAINT `fk_Aluno_has_UC_Aluno1`
    FOREIGN KEY (`Aluno_Username`)
    REFERENCES `GestaoTurnos`.`Alunos` (`Username`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Aluno_has_UC_UC1`
    FOREIGN KEY (`UC_Sigla`)
    REFERENCES `GestaoTurnos`.`UCs` (`Sigla`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `GestaoTurnos`.`Notificacoes` (
  `Texto` VARCHAR(150) NOT NULL,
  `Alunos_Username` VARCHAR(6) NOT NULL,
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  INDEX `fk_Notificacoes_Alunos1_idx` (`Alunos_Username` ASC),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_Notificacoes_Alunos1`
    FOREIGN KEY (`Alunos_Username`)
    REFERENCES `GestaoTurnos`.`Alunos` (`Username`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
