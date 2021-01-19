CREATE DATABASE IF NOT EXISTS `timetable` ;
CREATE TABLE `timetable`.`user` (
  `username` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `full_name` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `role` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`username`));
CREATE TABLE `timetable`.`course` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `csoport` VARCHAR(45) NULL,
  `eloado` VARCHAR(45) NULL,
  `felev` INT NULL,
  `fo` INT NULL,
  `hossz` INT NULL,
  `kar` VARCHAR(45) NULL,
  `kezdes` INT NULL,
  `nap` VARCHAR(45) NULL,
  `szki` VARCHAR(45) NULL,
  `tanszek` VARCHAR(45) NULL,
  `tantargy` VARCHAR(45) NULL,
  `terem` VARCHAR(45) NULL,
  `ti` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));
INSERT INTO `timetable`.`user` (`username`, `email`, `full_name`, `password`, `role`) VALUES ('admin', 'admin@timetable.com', 'Admin Admin', 'adminpw', 'admin');
INSERT INTO `timetable`.`user` (`username`, `email`, `full_name`, `password`, `role`) VALUES ('teacher', 'teacher@timetable.com', 'Test Teacher', 'teacherpw', 'teacher');
INSERT INTO `timetable`.`user` (`username`, `email`, `full_name`, `password`, `role`) VALUES ('student', 'student@timetable.com', 'Test Student', 'studentpw', 'student');
