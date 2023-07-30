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
-- Table structure for table `t_user_illustration`
--

DROP TABLE IF EXISTS `t_user_illustration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user_illustration` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(500) NOT NULL,
  `userId` bigint(20) NOT NULL,
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user_illustration`
--

LOCK TABLES `t_user_illustration` WRITE;
/*!40000 ALTER TABLE `t_user_illustration` DISABLE KEYS */;
INSERT INTO `t_user_illustration` VALUES (1,'https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/user-images/1/a8f359e6-44af-4869-acc0-f9e8a0776994.jpeg',10,'2023-02-23 21:51:59'),(2,'https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/user-images/1/6253bd23-a2d2-401d-ae18-520fa2a6eb41.png',10,'2023-02-23 22:12:54'),(3,'https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/user-images/1/5e1f3e4e-b3a7-46c8-beb9-458422da9903.png',10,'2023-02-23 22:16:30'),(4,'https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/user-images/1/6d3e21e0-12f3-4adb-a3be-3a9d9948deea.png',10,'2023-02-23 22:17:37'),(5,'https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/user-images/10/faa62785-04dd-44dc-b9af-e442786e9e5f.png',10,'2023-02-24 18:47:46'),(6,'https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/user-images/1/a8f359e6-44af-4869-acc0-f9e8a0776994.jpeg',10,'2023-02-23 21:51:59'),(7,'https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/user-images/1/6253bd23-a2d2-401d-ae18-520fa2a6eb41.png',10,'2023-02-23 22:12:54'),(8,'https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/user-images/1/5e1f3e4e-b3a7-46c8-beb9-458422da9903.png',10,'2023-02-23 22:16:30'),(9,'https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/user-images/1/6d3e21e0-12f3-4adb-a3be-3a9d9948deea.png',10,'2023-02-23 22:17:37'),(10,'https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/user-images/10/faa62785-04dd-44dc-b9af-e442786e9e5f.png',10,'2023-02-24 18:47:46'),(11,'https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/user-images/10/1f21a4cf-433e-4c60-9b6b-5473b0127267.png',10,'2023-02-24 19:12:24'),(12,'https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/user-images/10/4b33965d-45c2-4bc8-a008-670d4f85d157.png',10,'2023-02-24 19:12:41'),(13,'https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/user-images/10/a0776743-86ef-4bc1-9826-9f54eebf2756.png',10,'2023-02-24 19:45:37'),(14,'https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/user-images/10/9089b849-c222-4e58-9375-ae537b656066.png',10,'2023-02-25 17:52:58'),(15,'https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/user-images/10/c14628fc-0c6e-464a-8968-195b9b14caf4.jpg',10,'2023-02-25 17:53:10'),(16,'https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/user-images/10/ce6de9bc-2b7f-4707-89a6-f81cf9f3290e.jpg',10,'2023-02-25 17:53:19'),(17,'https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/user-images/10/81d1d43f-4cff-47bd-bbfa-25c081d6ec97.jpg',10,'2023-02-25 17:53:53'),(18,'https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/user-images/10/87de0c48-fba5-438b-a4b1-83b11ea135c0.jpg',10,'2023-02-25 17:55:05'),(19,'https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/user-images/10/7e9165f3-bf87-4a15-a2de-a70cddb64e9d.png',10,'2023-02-25 17:58:04'),(20,'https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/user-images/10/cd4bb348-c87b-4375-8049-fc3349b75676.png',10,'2023-02-25 17:58:18'),(21,'https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/user-images/10/cdfff27a-f8fb-4e89-a0f5-689f19648254.png',10,'2023-02-25 18:01:35'),(22,'https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/user-images/10/bc350e34-c412-4541-b107-fde26d294902.png',10,'2023-02-25 18:02:27'),(23,'https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/user-images/10/0fd902c3-a318-496f-aa2f-9356e43c09c0.png',10,'2023-02-25 18:02:48'),(24,'https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/user-images/1/34f31432-d50e-4b73-a6a2-b01a8425ae51.png',1,'2023-02-25 20:57:32'),(25,'https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/user-images/10/2a1313db-0201-483b-97e9-b0d2f88e92b5.png',10,'2023-03-01 17:11:07'),(26,'https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/user-images/10/864feb53-7983-49b0-b7a8-d52e79f4b33f.png',10,'2023-07-25 16:15:38'),(27,'https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/user-images/10/21169e71-5c43-44e4-bf83-8b495ba91bc4.png',10,'2023-07-28 20:33:21');
/*!40000 ALTER TABLE `t_user_illustration` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-30 16:52:38
