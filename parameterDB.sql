-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: mydb
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
-- Table structure for table `literature`
--

DROP TABLE IF EXISTS `literature`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `literature` (
                              `id` int NOT NULL AUTO_INCREMENT,
                              `site` text,
                              `title` text,
                              `prts_id` int DEFAULT NULL,
                              PRIMARY KEY (`id`),
                              KEY `fk_literature_prts` (`prts_id`),
                              CONSTRAINT `fk_literature_prts` FOREIGN KEY (`prts_id`) REFERENCES `prts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `literature`
--

LOCK TABLES `literature` WRITE;
/*!40000 ALTER TABLE `literature` DISABLE KEYS */;
INSERT INTO `literature` VALUES (1,'Ссылка','Литература',1),(2,'Новая ссылка','Лит-ра',6);
/*!40000 ALTER TABLE `literature` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `name_prts`
--

DROP TABLE IF EXISTS `name_prts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `name_prts` (
                             `id` int NOT NULL AUTO_INCREMENT,
                             `name_altr` varchar(45) DEFAULT NULL,
                             `abbr_altr` varchar(45) DEFAULT NULL,
                             `nvers` int DEFAULT NULL,
                             `prts_id` int NOT NULL,
                             PRIMARY KEY (`id`),
                             KEY `fk_name_prts` (`prts_id`),
                             CONSTRAINT `fk_name_prts` FOREIGN KEY (`prts_id`) REFERENCES `prts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `name_prts`
--

LOCK TABLES `name_prts` WRITE;
/*!40000 ALTER TABLE `name_prts` DISABLE KEYS */;
INSERT INTO `name_prts` VALUES (1,'Имя','мя',1,6),(2,'Наименование','наимен',2,2);
/*!40000 ALTER TABLE `name_prts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `param_group`
--

DROP TABLE IF EXISTS `param_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `param_group` (
                               `id` int NOT NULL AUTO_INCREMENT,
                               `title` varchar(100) DEFAULT NULL,
                               `prts_id` int DEFAULT NULL,
                               `type_prts_id` int DEFAULT NULL,
                               PRIMARY KEY (`id`),
                               KEY `fk_group_type` (`type_prts_id`),
                               KEY `fk_group_prts` (`prts_id`),
                               CONSTRAINT `fk_group_prts` FOREIGN KEY (`prts_id`) REFERENCES `prts` (`id`),
                               CONSTRAINT `fk_group_type` FOREIGN KEY (`type_prts_id`) REFERENCES `type_prts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `param_group`
--

LOCK TABLES `param_group` WRITE;
/*!40000 ALTER TABLE `param_group` DISABLE KEYS */;
INSERT INTO `param_group` VALUES (20,'Сердечно-сосудистая система',2,2),(21,'Дыхательная система',NULL,1),(22,'Костно- мышечная система',NULL,1),(23,'Нервная система',NULL,1),(24,'Эндокринная система',NULL,1);
/*!40000 ALTER TABLE `param_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prts`
--

DROP TABLE IF EXISTS `prts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prts` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `vid` varchar(45) DEFAULT NULL,
                        `edinizmeren` varchar(45) DEFAULT NULL,
                        `opisaniefp` text,
                        `oblastprimenen` text,
                        `namefp` varchar(45) DEFAULT NULL,
                        `refer` varchar(45) DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prts`
--

LOCK TABLES `prts` WRITE;
/*!40000 ALTER TABLE `prts` DISABLE KEYS */;
INSERT INTO `prts` VALUES (1,'vid1','edin','opis','oblast','name','refer'),(2,'vid2','edin2','opis2','oblast2','name2','refer2'),(3,'vid3','edin','opis','oblast','name','refer'),(4,'vid4','edin','opis','oblast','name','refer'),(6,'Вид','Единица','Описание','Область','Название','Ссылка');
/*!40000 ALTER TABLE `prts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prv_prts`
--

DROP TABLE IF EXISTS `prv_prts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prv_prts` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `metod_izmrn` text,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prv_prts`
--

LOCK TABLES `prv_prts` WRITE;
/*!40000 ALTER TABLE `prv_prts` DISABLE KEYS */;
INSERT INTO `prv_prts` VALUES (2,'Методика 1'),(3,'Методика 2'),(4,'Методика 3');
/*!40000 ALTER TABLE `prv_prts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `type_prts`
--

DROP TABLE IF EXISTS `type_prts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type_prts` (
                             `id` int NOT NULL AUTO_INCREMENT,
                             `name_tp` varchar(45) DEFAULT NULL,
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `type_prts`
--

LOCK TABLES `type_prts` WRITE;
/*!40000 ALTER TABLE `type_prts` DISABLE KEYS */;
INSERT INTO `type_prts` VALUES (1,'Физиологическая система'),(2,'Психологическая система'),(3,'Акустическая система'),(4,'Психофизиологическая система');
/*!40000 ALTER TABLE `type_prts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vid_prts`
--

DROP TABLE IF EXISTS `vid_prts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vid_prts` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `prv_prts_id` int DEFAULT NULL,
                            `vtr_prts_id` int DEFAULT NULL,
                            `prts_id` int DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            KEY `fk_vid_prts` (`prts_id`),
                            KEY `fk_vid_vtr_prts` (`vtr_prts_id`),
                            KEY `fk_vid_prv_prts` (`prv_prts_id`),
                            CONSTRAINT `fk_vid_prts` FOREIGN KEY (`prts_id`) REFERENCES `prts` (`id`),
                            CONSTRAINT `fk_vid_prv_prts` FOREIGN KEY (`prv_prts_id`) REFERENCES `prv_prts` (`id`),
                            CONSTRAINT `fk_vid_vtr_prts` FOREIGN KEY (`vtr_prts_id`) REFERENCES `vtr_prts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vid_prts`
--

LOCK TABLES `vid_prts` WRITE;
/*!40000 ALTER TABLE `vid_prts` DISABLE KEYS */;
INSERT INTO `vid_prts` VALUES (2,2,1,1);
/*!40000 ALTER TABLE `vid_prts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vtr_prts`
--

DROP TABLE IF EXISTS `vtr_prts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vtr_prts` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `formula` blob,
                            `prim` text,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vtr_prts`
--

LOCK TABLES `vtr_prts` WRITE;
/*!40000 ALTER TABLE `vtr_prts` DISABLE KEYS */;
INSERT INTO `vtr_prts` VALUES (1,NULL,'прим'),(2,NULL,'прим прим');
/*!40000 ALTER TABLE `vtr_prts` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-04-08  3:04:17
