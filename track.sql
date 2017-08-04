-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 2017-07-20 10:01:13
-- 服务器版本： 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `track`
--

-- --------------------------------------------------------

--
-- 表的结构 `administrator`
--

DROP TABLE IF EXISTS `administrator`;
CREATE TABLE IF NOT EXISTS `administrator` (
  `adminId` int(11) NOT NULL AUTO_INCREMENT,
  `adminName` char(20) DEFAULT NULL,
  `password` char(20) DEFAULT NULL,
  PRIMARY KEY (`adminId`),
  UNIQUE KEY `admin_name` (`adminName`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- 转存表中的数据 `administrator`
--

INSERT INTO `administrator` (`adminId`, `adminName`, `password`) VALUES
(1, '123', '123');

-- --------------------------------------------------------

--
-- 表的结构 `bestfeed`
--

DROP TABLE IF EXISTS `bestfeed`;
CREATE TABLE IF NOT EXISTS `bestfeed` (
  `feedId` varchar(30) NOT NULL DEFAULT '',
  `date` date NOT NULL DEFAULT '0000-00-00',
  PRIMARY KEY (`feedId`,`date`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- 转存表中的数据 `bestfeed`
--

INSERT INTO `bestfeed` (`feedId`, `date`) VALUES
('5966e9fde9266510d4d7ecc3', '2017-07-14'),
('59670e5ec0628b34901e17e3', '2017-07-14');

-- --------------------------------------------------------

--
-- 表的结构 `client`
--
DROP TABLE IF EXISTS `client`;
CREATE TABLE IF NOT EXISTS `client` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `phone` char(20) DEFAULT NULL,
  `gender` char(20) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `userName` char(20) DEFAULT NULL,
  `password` char(20) DEFAULT NULL,
  `email` char(20) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10000 ;

--
-- 转存表中的数据 `client`
--

INSERT INTO `client` (`userId`, `phone`, `gender`, `birthday`, `userName`, `password`, `email`) VALUES
(1001, '15821913316', 'male', '2013-01-15', 'irving', '123456', 'thor.wang@sjtu.edu.cn');

-- --------------------------------------------------------

--
-- 表的结构 `follow`
--
DROP TABLE IF EXISTS `follow`;
CREATE TABLE IF NOT EXISTS `follow` (
  `userId` int(11) NOT NULL,
  `followId` int(11) NOT NULL DEFAULT '0',
  `isFriend` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`userId`,`followId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- 转存表中的数据 `follow`
--


/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
DROP TABLE IF EXISTS `clientextrainfo`;
CREATE TABLE IF NOT EXISTS `clientextrainfo` (
  `userId` int(11) NOT NULL,
   `career` char(20) DEFAULT NULL,
   `education` char(20) DEFAULT NULL,
   `hobbyone` char(20) DEFAULT NULL,
   `hobbytwo` char(20) DEFAULT NULL,
   `hobbythree` char(20) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
