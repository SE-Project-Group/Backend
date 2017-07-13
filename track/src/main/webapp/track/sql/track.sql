DROP TABLE IF EXISTS `client`;
DROP TABLE IF EXISTS `administrator`;

-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 2017-07-12 11:23:12
-- 鏈嶅姟鍣ㄧ増鏈細 5.6.17
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
-- 琛ㄧ殑缁撴瀯 `administrator`
--

CREATE TABLE IF NOT EXISTS `administrator` (
  `adminId` int(11) NOT NULL AUTO_INCREMENT,
  `adminName` char(20) DEFAULT NULL,
  `password` char(20) DEFAULT NULL,
  PRIMARY KEY (`adminId`),
  UNIQUE KEY `admin_name` (`adminName`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- 杞瓨琛ㄤ腑鐨勬暟鎹� `administrator`
--

INSERT INTO `administrator` (`adminId`, `adminName`, `password`) VALUES
(1, '123', '123');

-- --------------------------------------------------------

--
-- 琛ㄧ殑缁撴瀯 `client`
--

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
-- 杞瓨琛ㄤ腑鐨勬暟鎹� `client`
--

INSERT INTO `client` (`userId`, `phone`, `gender`, `birthday`, `userName`, `password`, `email`) VALUES
(2, '777', 'sss', '2013-01-15', '123', '123', '1656'),
(4, '888', '888', '1997-03-13', '888', '888', '888'),
(9999, '9999', '26', '2017-07-04', '9999', '9999', '9999');

-- --------------------------------------------------------

--
-- 琛ㄧ殑缁撴瀯 `friend`
--
DROP TABLE IF EXISTS `follow`;
CREATE TABLE IF NOT EXISTS `follow` (
  `userId` int(11) NOT NULL,
  `followId` int(11) DEFAULT NULL,
  `isFriend` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`userId`,`followId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
