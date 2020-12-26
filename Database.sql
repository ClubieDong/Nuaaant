CREATE DATABASE  IF NOT EXISTS `NuaaAnt` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `NuaaAnt`;
-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: 47.101.201.160    Database: NuaaAnt
-- ------------------------------------------------------
-- Server version	8.0.22-0ubuntu0.20.04.3

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Agrees`
--

DROP TABLE IF EXISTS `Agrees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Agrees` (
  `UserID` int NOT NULL,
  `RemarkID` int NOT NULL,
  PRIMARY KEY (`UserID`,`RemarkID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Appliers`
--

DROP TABLE IF EXISTS `Appliers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Appliers` (
  `UserID` int NOT NULL,
  `OrderID` int NOT NULL,
  PRIMARY KEY (`UserID`,`OrderID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Likes`
--

DROP TABLE IF EXISTS `Likes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Likes` (
  `UserID` int NOT NULL,
  `OrderID` int NOT NULL,
  PRIMARY KEY (`UserID`,`OrderID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Messages`
--

DROP TABLE IF EXISTS `Messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Messages` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `SenderID` int NOT NULL,
  `ReceiverID` int NOT NULL,
  `Time` datetime NOT NULL,
  `Text` text NOT NULL,
  `HasRead` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Orders`
--

DROP TABLE IF EXISTS `Orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Orders` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `TypeIndex` tinyint NOT NULL DEFAULT '0',
  `Title` varchar(20) NOT NULL,
  `Reward` decimal(9,2) DEFAULT NULL,
  `Deadline` datetime DEFAULT NULL,
  `GiverID` int DEFAULT NULL,
  `TakerID` int DEFAULT NULL,
  `IsTemplate` tinyint NOT NULL,
  `FromAddr` varchar(50) DEFAULT NULL,
  `ToAddr` varchar(50) DEFAULT NULL,
  `IsSelf` tinyint DEFAULT NULL,
  `SizeIndex` tinyint DEFAULT NULL,
  `WeightIndex` tinyint DEFAULT NULL,
  `ExpressCode` varchar(20) DEFAULT NULL,
  `QuestionTypeIndex` tinyint DEFAULT NULL,
  `Duration` int DEFAULT NULL,
  `UnitIndex` tinyint DEFAULT NULL,
  `ReturnTime` datetime DEFAULT NULL,
  `SimpleDesc` varchar(50) DEFAULT NULL,
  `DetailedDesc` text,
  `State` tinyint DEFAULT NULL,
  `PublishTime` datetime DEFAULT NULL,
  `AcceptTime` datetime DEFAULT NULL,
  `SubmitTime` datetime DEFAULT NULL,
  `CompleteTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Remarks`
--

DROP TABLE IF EXISTS `Remarks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Remarks` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `UserID` int NOT NULL,
  `OrderID` int NOT NULL,
  `Time` datetime NOT NULL,
  `Text` text NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS `Users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Users` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `AvatarUrl` text,
  `NickName` varchar(20) DEFAULT NULL,
  `Gender` tinyint NOT NULL DEFAULT '0',
  `Motto` text,
  `CollegeIndex` tinyint NOT NULL DEFAULT '0',
  `GradeIndex` tinyint NOT NULL DEFAULT '0',
  `Dormitory` varchar(50) DEFAULT NULL,
  `StudentID` char(9) DEFAULT NULL,
  `RealName` varchar(20) DEFAULT NULL,
  `Phone` char(11) DEFAULT NULL,
  `Email` varchar(50) DEFAULT NULL,
  `OpenID` char(28) NOT NULL,
  `SessionKey` char(24) DEFAULT NULL,
  `SessionID` char(36) DEFAULT NULL,
  `GiverScoreTotal` decimal(9,1) NOT NULL DEFAULT '0.0',
  `GiverCount` int NOT NULL DEFAULT '0',
  `TakerScoreTotal` decimal(9,1) NOT NULL DEFAULT '0.0',
  `TakerCount` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  UNIQUE KEY `OpenID_UNIQUE` (`OpenID`),
  UNIQUE KEY `StudentID_UNIQUE` (`StudentID`),
  UNIQUE KEY `SessionID_UNIQUE` (`SessionID`)
) ENGINE=InnoDB AUTO_INCREMENT=1005 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-12-26 20:51:57
