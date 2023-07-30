-- MySQL dump 10.13  Distrib 8.0.31, for macos12 (x86_64)
--
-- Host: localhost    Database: postcard_maker
-- ------------------------------------------------------
-- Server version	8.0.18

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
-- Table structure for table `t_element`
--

DROP TABLE IF EXISTS `t_element`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_element` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `width` double DEFAULT NULL,
  `height` double DEFAULT NULL,
  `left` double DEFAULT NULL,
  `top` double DEFAULT NULL,
  `zoomX` double DEFAULT NULL,
  `zoomY` double DEFAULT NULL,
  `scaleX` double DEFAULT NULL,
  `scaleY` double DEFAULT NULL,
  `angle` double DEFAULT NULL,
  `type` varchar(5) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `order` bigint(20) DEFAULT NULL,
  `projectId` bigint(20) DEFAULT NULL,
  `content` varchar(500) DEFAULT NULL,
  `fontId` bigint(20) DEFAULT NULL,
  `fill` varchar(255) DEFAULT NULL,
  `shapeCode` varchar(255) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `data` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_element`
--

LOCK TABLES `t_element` WRITE;
/*!40000 ALTER TABLE `t_element` DISABLE KEYS */;
INSERT INTO `t_element` VALUES (1,100,200,1,1,1,1,1,1,0,'0',NULL,NULL,1,1,'fff',1,'pink','S01',NULL,NULL),(2,300,200,0,0,1,1,1,1,0,'0','2023-02-25 23:09:30',NULL,1,23,NULL,NULL,'red','S01',NULL,NULL),(3,300,200,0,0,1,1,1,1,0,'1','2023-02-25 23:09:30',NULL,1,23,'S01',1,'pink',NULL,NULL,NULL),(4,300,500,0,0,1,1,1,1,0,'2','2023-02-25 23:09:30','2023-02-26 11:10:02',1,23,NULL,NULL,NULL,NULL,'https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/user-images/1/a8f359e6-44af-4869-acc0-f9e8a0776994.jpeg',NULL),(5,300,200,0,0,1,1,1,1,0,'0','2023-02-25 23:12:30',NULL,1,24,NULL,NULL,'red','S01',NULL,NULL),(6,300,200,0,0,1,1,1,1,0,'1','2023-02-25 23:12:30',NULL,1,24,'S01',1,'pink',NULL,NULL,NULL),(7,300,200,0,0,1,1,1,1,0,'2','2023-02-25 23:12:30',NULL,1,24,NULL,NULL,NULL,NULL,'https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/user-images/1/a8f359e6-44af-4869-acc0-f9e8a0776994.jpeg',NULL),(8,300,200,0,0,1,1,1,1,0,'2','2023-02-26 11:05:27',NULL,1,NULL,NULL,NULL,NULL,NULL,'https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/user-images/1/a8f359e6-44af-4869-acc0-f9e8a0776994.jpeg',NULL);
/*!40000 ALTER TABLE `t_element` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-30 16:52:36
