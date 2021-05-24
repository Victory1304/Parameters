-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: biomedical_indicators
-- ------------------------------------------------------
-- Server version	8.0.23

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
-- Table structure for table `additional_name`
--

DROP TABLE IF EXISTS `additional_name`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `additional_name` (
  `id` int NOT NULL AUTO_INCREMENT,
  `abbreviation` varchar(25) DEFAULT NULL,
  `decoding_abbrev` varchar(500) DEFAULT NULL,
  `id_language` int DEFAULT NULL,
  `id_basicname` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idLanguage` (`id_language`),
  KEY `idBasicName` (`id_basicname`),
  CONSTRAINT `additional_name_ibfk_1` FOREIGN KEY (`id_language`) REFERENCES `language` (`id`),
  CONSTRAINT `additional_name_ibfk_2` FOREIGN KEY (`id_basicname`) REFERENCES `basic_name_indicator` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `basic_name_indicator`
--

DROP TABLE IF EXISTS `basic_name_indicator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `basic_name_indicator` (
  `id` int NOT NULL AUTO_INCREMENT,
  `latin_name` varchar(500) NOT NULL,
  `unit_measure` varchar(100) DEFAULT NULL,
  `description` text,
  `id_groupsystem` int DEFAULT NULL,
  `id_system` int DEFAULT NULL,
  `id_type` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idGroupSystems` (`id_groupsystem`),
  KEY `idSystem` (`id_system`),
  KEY `idType_indicator` (`id_type`),
  CONSTRAINT `basic_name_indicator_ibfk_1` FOREIGN KEY (`id_groupsystem`) REFERENCES `group_systems` (`id`),
  CONSTRAINT `basic_name_indicator_ibfk_2` FOREIGN KEY (`id_system`) REFERENCES `systems` (`id`),
  CONSTRAINT `basic_name_indicator_ibfk_3` FOREIGN KEY (`id_type`) REFERENCES `type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `equipment`
--

DROP TABLE IF EXISTS `equipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `equipment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(500) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `formula`
--

DROP TABLE IF EXISTS `formula`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `formula` (
  `id` int NOT NULL AUTO_INCREMENT,
  `calculation` varchar(255) DEFAULT NULL,
  `normal_value` float DEFAULT NULL,
  `borderline_value` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `group_systems`
--

DROP TABLE IF EXISTS `group_systems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_systems` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(500) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `language`
--

DROP TABLE IF EXISTS `language`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `language` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `literature`
--

DROP TABLE IF EXISTS `literature`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `literature` (
  `id` int NOT NULL AUTO_INCREMENT,
  `website` text,
  `title` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `method`
--

DROP TABLE IF EXISTS `method`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `method` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name_russian` varchar(500) DEFAULT NULL,
  `name_foreign` varchar(500) NOT NULL,
  `conditions` text,
  `restrictions` text,
  `normal_value` float DEFAULT NULL,
  `borderline_value` varchar(25) DEFAULT NULL,
  `id_source` int DEFAULT NULL,
  `info` text,
  PRIMARY KEY (`id`),
  KEY `idSource_method` (`id_source`),
  CONSTRAINT `method_ibfk_1` FOREIGN KEY (`id_source`) REFERENCES `source` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `source`
--

DROP TABLE IF EXISTS `source`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `source` (
  `id` int NOT NULL AUTO_INCREMENT,
  `website` text,
  `title` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `systems`
--

DROP TABLE IF EXISTS `systems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `systems` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(500) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `type`
--

DROP TABLE IF EXISTS `type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `unite_basic_formula`
--

DROP TABLE IF EXISTS `unite_basic_formula`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `unite_basic_formula` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_formula` int DEFAULT NULL,
  `id_basicname` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idFormula` (`id_formula`),
  KEY `idBasicName` (`id_basicname`),
  CONSTRAINT `unite_basic_formula_ibfk_1` FOREIGN KEY (`id_formula`) REFERENCES `formula` (`id`),
  CONSTRAINT `unite_basic_formula_ibfk_2` FOREIGN KEY (`id_basicname`) REFERENCES `basic_name_indicator` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `unite_indicator_method`
--

DROP TABLE IF EXISTS `unite_indicator_method`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `unite_indicator_method` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_method` int DEFAULT NULL,
  `id_basicname` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idMethod` (`id_method`),
  KEY `idBasicName` (`id_basicname`),
  CONSTRAINT `unite_indicator_method_ibfk_1` FOREIGN KEY (`id_method`) REFERENCES `method` (`id`),
  CONSTRAINT `unite_indicator_method_ibfk_2` FOREIGN KEY (`id_basicname`) REFERENCES `basic_name_indicator` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `unite_literature_name`
--

DROP TABLE IF EXISTS `unite_literature_name`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `unite_literature_name` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_literature` int DEFAULT NULL,
  `id_addname` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idLiteratures` (`id_literature`),
  KEY `idAddName` (`id_addname`),
  CONSTRAINT `unite_literature_name_ibfk_1` FOREIGN KEY (`id_literature`) REFERENCES `literature` (`id`),
  CONSTRAINT `unite_literature_name_ibfk_2` FOREIGN KEY (`id_addname`) REFERENCES `additional_name` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `unite_method_equipment`
--

DROP TABLE IF EXISTS `unite_method_equipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `unite_method_equipment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_method` int DEFAULT NULL,
  `id_equipment` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idMethod` (`id_method`),
  KEY `idEquipment` (`id_equipment`),
  CONSTRAINT `unite_method_equipment_ibfk_1` FOREIGN KEY (`id_method`) REFERENCES `method` (`id`),
  CONSTRAINT `unite_method_equipment_ibfk_2` FOREIGN KEY (`id_equipment`) REFERENCES `equipment` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-24  5:49:18
