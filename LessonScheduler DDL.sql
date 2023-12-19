CREATE DATABASE  IF NOT EXISTS `lesson_schedule` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `lesson_schedule`;
-- MySQL dump 10.13  Distrib 8.0.25, for macos11 (x86_64)
--
-- Host: localhost    Database: lesson_schedule
-- ------------------------------------------------------
-- Server version	8.0.25

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
-- Table structure for table `lessons`
--

DROP TABLE IF EXISTS `lessons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lessons` (
  `lesson_id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  `student_id` int NOT NULL,
  `start_date_time` datetime NOT NULL,
  `end_date_time` datetime NOT NULL,
  `lesson_profit` double NOT NULL,
  `meeting_code` varchar(45) DEFAULT NULL,
  `miles_commute` int DEFAULT NULL,
  PRIMARY KEY (`lesson_id`),
  KEY `student_id_idx` (`student_id`),
  CONSTRAINT `student_id` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lessons`
--

LOCK TABLES `lessons` WRITE;
/*!40000 ALTER TABLE `lessons` DISABLE KEYS */;
INSERT INTO `lessons` VALUES (3,'Visit',1,'2023-12-07 00:30:00','2023-12-07 01:00:00',78,'43535',10),(4,'Studio',2,'2023-12-06 00:30:00','2023-12-06 01:15:00',76.5,NULL,NULL),(5,'Studio',2,'2023-12-07 04:00:00','2023-12-07 04:30:00',51,NULL,NULL),(10,'Visit',2,'2023-12-07 04:00:00','2023-12-07 04:30:00',97.81,'sdfsdf',21),(12,'Visit',2,'2023-12-07 04:00:00','2023-12-07 04:30:00',65.41,'sdfsdf',3),(13,'Virtual',1,'2023-12-21 02:30:00','2023-12-21 03:00:00',45,'sdfs',NULL),(14,'Studio',2,'2023-12-08 01:45:00','2023-12-08 02:15:00',51,'34',3),(15,'Studio',2,'2023-12-22 01:15:00','2023-12-22 01:45:00',51,NULL,NULL),(16,'Studio',1,'2023-12-14 01:00:00','2023-12-14 01:45:00',76.5,NULL,NULL),(17,'Virtual',1,'2023-12-20 01:15:00','2023-12-20 01:45:00',45,'duude',NULL),(18,'Studio',2,'2023-12-20 01:15:00','2023-12-20 01:45:00',51,NULL,NULL),(19,'Visit',2,'2023-12-08 00:00:00','2023-12-08 00:30:00',65.41,'22342',3),(20,'Virtual',2,'2023-12-08 00:30:00','2023-12-08 01:00:00',45,'131232',NULL),(22,'Virtual',2,'2023-12-13 11:30:00','2023-12-13 12:00:00',45,'NVB4MN',9);
/*!40000 ALTER TABLE `lessons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `students` (
  `student_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `phone` varchar(10) NOT NULL,
  PRIMARY KEY (`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students`
--

LOCK TABLES `students` WRITE;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
INSERT INTO `students` VALUES (1,'Robby','2345235555'),(2,'Tommy','4535433333'),(20,'Jonathan','1234567890'),(21,'Jon','1234567890'),(22,'Jon','0987654321'),(23,'test','1234567890'),(24,'test','1234567899');
/*!40000 ALTER TABLE `students` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-12  1:22:14
