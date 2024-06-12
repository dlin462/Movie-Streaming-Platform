-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: cse305_2
-- ------------------------------------------------------
-- Server version	8.0.35

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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `Id` int NOT NULL,
  `DateOpened` date DEFAULT NULL,
  `Type` char(20) DEFAULT NULL,
  `Customer` char(20) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `Customer` (`Customer`),
  CONSTRAINT `account_ibfk_1` FOREIGN KEY (`Customer`) REFERENCES `customer` (`Id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'2006-10-01','unlimited-2','444-44-4444'),(2,'2006-10-15','Unlimited-3','222-22-2222');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `actor`
--

DROP TABLE IF EXISTS `actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `actor` (
  `Id` int NOT NULL,
  `Name` char(20) NOT NULL,
  `Age` int NOT NULL,
  `Gender` char(20) NOT NULL,
  `Rating` int DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor`
--

LOCK TABLES `actor` WRITE;
/*!40000 ALTER TABLE `actor` DISABLE KEYS */;
INSERT INTO `actor` VALUES (1,'Al Pacino',63,'M',5),(2,'Tim Robbins',53,'M',2);
/*!40000 ALTER TABLE `actor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appearedin`
--

DROP TABLE IF EXISTS `appearedin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appearedin` (
  `ActorId` int NOT NULL,
  `MovieId` int NOT NULL,
  PRIMARY KEY (`ActorId`,`MovieId`),
  KEY `MovieId` (`MovieId`),
  CONSTRAINT `appearedin_ibfk_1` FOREIGN KEY (`ActorId`) REFERENCES `actor` (`Id`) ON UPDATE CASCADE,
  CONSTRAINT `appearedin_ibfk_2` FOREIGN KEY (`MovieId`) REFERENCES `movie` (`Id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appearedin`
--

LOCK TABLES `appearedin` WRITE;
/*!40000 ALTER TABLE `appearedin` DISABLE KEYS */;
INSERT INTO `appearedin` VALUES (1,3);
/*!40000 ALTER TABLE `appearedin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `bestsellerlist`
--

DROP TABLE IF EXISTS `bestsellerlist`;
/*!50001 DROP VIEW IF EXISTS `bestsellerlist`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `bestsellerlist` AS SELECT 
 1 AS `Name`,
 1 AS `RentalCount`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `Id` char(20) NOT NULL,
  `Email` char(32) DEFAULT NULL,
  `Rating` int DEFAULT NULL,
  `CreditCardNumber` char(20) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  CONSTRAINT `customer_ibfk_1` FOREIGN KEY (`Id`) REFERENCES `person` (`SSN`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES ('111-11-1111','example@gmail.com',1,'1234-5678-1234-5678'),('222-22-2222','customer@gmail.com',1,'5678-1234-5678-1234'),('333-33-3333','jsmith@ic.sunysb.edu',1,'2345-6789-2345-6789'),('444-44-4444','pml@cs.sunysb.edu',1,'6789-2345-6789-2345');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `Id` char(20) NOT NULL,
  `StartDate` date DEFAULT NULL,
  `HourlyRate` int DEFAULT NULL,
  `EmployeeType` char(20) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`Id`) REFERENCES `person` (`SSN`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES ('789-12-3456','2006-02-02',70,'Manager');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `location` (
  `ZipCode` int NOT NULL,
  `City` varchar(255) DEFAULT NULL,
  `State` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ZipCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (11315,'stony','NY'),(11355,'stony','NY'),(11790,'Stony Brook','NY'),(11794,'Stony Brook','NY'),(93536,'Los Angeles','CA');
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `login` (
  `SSN` char(20) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`SSN`),
  CONSTRAINT `person_login` FOREIGN KEY (`SSN`) REFERENCES `person` (`SSN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login`
--

LOCK TABLES `login` WRITE;
/*!40000 ALTER TABLE `login` DISABLE KEYS */;
INSERT INTO `login` VALUES ('111-11-1111','root@gmail.com','root','manager'),('222-22-2222','customer@gmail.com','root','customer'),('789-12-3456','manager@gmail.com','root','manager');
/*!40000 ALTER TABLE `login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `mostactivecustomers`
--

DROP TABLE IF EXISTS `mostactivecustomers`;
/*!50001 DROP VIEW IF EXISTS `mostactivecustomers`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `mostactivecustomers` AS SELECT 
 1 AS `AccountId`,
 1 AS `RentalCount`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `movie`
--

DROP TABLE IF EXISTS `movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movie` (
  `Id` int NOT NULL,
  `Name` char(20) NOT NULL,
  `Type` char(20) NOT NULL,
  `Rating` int DEFAULT NULL,
  `DistrFee` decimal(10,2) DEFAULT NULL,
  `NumCopies` int DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie`
--

LOCK TABLES `movie` WRITE;
/*!40000 ALTER TABLE `movie` DISABLE KEYS */;
INSERT INTO `movie` VALUES (0,'helklo','Drama',2,800.00,1),(1,'The Godfather','Drama',4,10000.00,3),(2,'Shawshank Redemption','Drama',4,1000.00,2),(3,'Borat','Comedy',3,500.00,1);
/*!40000 ALTER TABLE `movie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movieq`
--

DROP TABLE IF EXISTS `movieq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movieq` (
  `AccountId` int NOT NULL,
  `MovieId` int NOT NULL,
  PRIMARY KEY (`AccountId`,`MovieId`),
  KEY `MovieId` (`MovieId`),
  CONSTRAINT `movieq_ibfk_1` FOREIGN KEY (`AccountId`) REFERENCES `account` (`Id`) ON UPDATE CASCADE,
  CONSTRAINT `movieq_ibfk_2` FOREIGN KEY (`MovieId`) REFERENCES `movie` (`Id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movieq`
--

LOCK TABLES `movieq` WRITE;
/*!40000 ALTER TABLE `movieq` DISABLE KEYS */;
INSERT INTO `movieq` VALUES (2,2),(1,3),(2,3);
/*!40000 ALTER TABLE `movieq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `person` (
  `SSN` char(20) NOT NULL,
  `LastName` char(20) NOT NULL,
  `FirstName` char(20) NOT NULL,
  `Address` char(20) DEFAULT NULL,
  `ZipCode` int DEFAULT NULL,
  `Telephone` char(20) DEFAULT NULL,
  PRIMARY KEY (`SSN`),
  KEY `ZipCode` (`ZipCode`),
  CONSTRAINT `person_ibfk_1` FOREIGN KEY (`ZipCode`) REFERENCES `location` (`ZipCode`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES ('011-11-1111','lin','david','12345',11355,'1234567890'),('111-11-1111','Yang','Shang','123 Success Street',11790,'516-632-8959'),('123-45-6789','Smith','David','123 College road',11790,'516-215-2345'),('133-04-2231','lin','david','12345',11355,'1234567890'),('222-22-2222','Du','Victor','456 Fortune Road',11790,'516-632-4360'),('333-33-3333','Smith','John','789 Peace Blvd.',93536,'315-443-4321'),('444-44-4444','Philip','Lewis','135 Knowledge Lane',11794,'516-666-8888'),('789-12-3456','Warren','David','456 Sunken Street',11794,'631-632-9987'),('888-88-8888','Heo','Jun','123 Stony Brook Rd',11790,'631-888-8888'),('914-23-3643','lin','david','12345321',11315,'7461114324'),('924-23-3633','lin','david','12345321',11315,'7461114324'),('934-23-3333','lin','david','12345321',11355,'7461114321'),('934-23-3633','lin','david','12345321',11355,'7461114324'),('999-88-7777','Baik','Inuk','3 College Drive',11790,'631-633-0069');
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rental`
--

DROP TABLE IF EXISTS `rental`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rental` (
  `AccountId` int NOT NULL,
  `CustRepId` char(20) NOT NULL,
  `OrderId` int NOT NULL,
  `MovieId` int NOT NULL,
  PRIMARY KEY (`AccountId`,`CustRepId`,`OrderId`,`MovieId`),
  KEY `CustRepId` (`CustRepId`),
  KEY `OrderId` (`OrderId`),
  KEY `MovieId` (`MovieId`),
  CONSTRAINT `rental_ibfk_1` FOREIGN KEY (`AccountId`) REFERENCES `account` (`Id`) ON UPDATE CASCADE,
  CONSTRAINT `rental_ibfk_2` FOREIGN KEY (`CustRepId`) REFERENCES `employee` (`Id`) ON UPDATE CASCADE,
  CONSTRAINT `rental_ibfk_3` FOREIGN KEY (`OrderId`) REFERENCES `rentalorder` (`Id`) ON UPDATE CASCADE,
  CONSTRAINT `rental_ibfk_4` FOREIGN KEY (`MovieId`) REFERENCES `movie` (`Id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rental`
--

LOCK TABLES `rental` WRITE;
/*!40000 ALTER TABLE `rental` DISABLE KEYS */;
INSERT INTO `rental` VALUES (1,'123-45-6789',1,1),(2,'123-45-6789',2,3),(1,'789-12-3456',3,3),(2,'789-12-3456',4,2),(2,'123-45-6789',5,1);
/*!40000 ALTER TABLE `rental` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rentalorder`
--

DROP TABLE IF EXISTS `rentalorder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rentalorder` (
  `Id` int NOT NULL,
  `DateTime` datetime DEFAULT NULL,
  `ReturnDate` date DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rentalorder`
--

LOCK TABLES `rentalorder` WRITE;
/*!40000 ALTER TABLE `rentalorder` DISABLE KEYS */;
INSERT INTO `rentalorder` VALUES (1,'2009-11-11 10:00:00','2009-11-14'),(2,'2009-11-11 18:15:00',NULL),(3,'2009-11-12 09:30:00',NULL),(4,'2009-11-21 22:22:00',NULL),(5,'2009-12-25 10:00:00',NULL);
/*!40000 ALTER TABLE `rentalorder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `bestsellerlist`
--

/*!50001 DROP VIEW IF EXISTS `bestsellerlist`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `bestsellerlist` AS select `m`.`Name` AS `Name`,count(0) AS `RentalCount` from (`movie` `m` join `rental` `r` on((`r`.`MovieId` = `m`.`Id`))) group by `m`.`Name` order by `RentalCount` desc limit 10 */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `mostactivecustomers`
--

/*!50001 DROP VIEW IF EXISTS `mostactivecustomers`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `mostactivecustomers` AS select `rental`.`AccountId` AS `AccountId`,count(0) AS `RentalCount` from `rental` group by `rental`.`AccountId` order by `RentalCount` desc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-06 21:06:05
