/*
SQLyog v10.2 
MySQL - 5.1.73-community : Database - library
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`library` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `library`;

/*Table structure for table `admin` */

DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
  `auser` varchar(20) DEFAULT NULL,
  `apwd` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `admin` */

insert  into `admin`(`auser`,`apwd`) values ('1','1');

/*Table structure for table `book` */

DROP TABLE IF EXISTS `book`;

CREATE TABLE `book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bname` char(20) DEFAULT NULL,
  `author` char(20) DEFAULT NULL,
  `press` char(30) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `entertime` char(20) DEFAULT NULL,
  `sum` int(11) DEFAULT NULL,
  `nowsum` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=354 DEFAULT CHARSET=utf8;

/*Data for the table `book` */

insert  into `book`(`id`,`bname`,`author`,`press`,`price`,`entertime`,`sum`,`nowsum`) values (349,'5','5','5',5,'5',5,5),(350,'6','6','6',6,'6',6,6),(351,'7','7','7',7,'7',7,7),(352,'8','8','8',8,'8',8,8),(353,'9','9','9',9,'9',9,9);

/*Table structure for table `book1` */

DROP TABLE IF EXISTS `book1`;

CREATE TABLE `book1` (
  `bname` varchar(10) DEFAULT NULL,
  `press` varchar(10) DEFAULT NULL,
  `author` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `book1` */

/*Table structure for table `brinfo` */

DROP TABLE IF EXISTS `brinfo`;

CREATE TABLE `brinfo` (
  `bn` int(11) DEFAULT NULL,
  `bname` char(20) DEFAULT NULL,
  `username` char(20) DEFAULT NULL,
  `borrowtime` char(20) DEFAULT NULL,
  `phonenum` char(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `brinfo` */

insert  into `brinfo`(`bn`,`bname`,`username`,`borrowtime`,`phonenum`) values (350,'6','persist1','2015-08-29','15855054300');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `upwd` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `phonenum` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`upwd`,`name`,`sex`,`phonenum`) values (1,'persist1','123456','张红','女','15855054300'),(2,'persist2','123456','王悦','女','15855034378'),(3,'persist3','123456','周岳','男','15823054350'),(4,'persist4','123456','李龙','男','15390054300');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
