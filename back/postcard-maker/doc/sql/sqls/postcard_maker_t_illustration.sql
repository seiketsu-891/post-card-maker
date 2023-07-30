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
-- Table structure for table `t_illustration`
--

DROP TABLE IF EXISTS `t_illustration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_illustration` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '插图id',
  `title` varchar(15) NOT NULL COMMENT '插图名称',
  `albumId` bigint(20) NOT NULL COMMENT '所属插图集id',
  `description` varchar(255) DEFAULT NULL COMMENT '图片描述',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `url` varchar(255) NOT NULL COMMENT '资源地址',
  `isVip` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_illustration`
--

LOCK TABLES `t_illustration` WRITE;
/*!40000 ALTER TABLE `t_illustration` DISABLE KEYS */;
INSERT INTO `t_illustration` VALUES (1,'Miss.',2,'女性 电脑 聊天','2015-08-16 03:47:42','2004-05-30 12:09:31','https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/shopping%2FDrawKit%20Vector%20Illustration%20Black%20Friday%20%26%20Online%20Shopping%20(10).png',1),(2,'Ms.',2,'女性 绿色 毛衣  长发 花瓶 窗帘 卡片 紫色','2009-02-06 06:31:08','2002-03-08 03:39:57','https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/shopping%2FDrawKit%20Vector%20Illustration%20Black%20Friday%20%26%20Online%20Shopping%20(2).png',1),(3,'Mrs.',2,'女性 喜欢 点赞 紫色 粉色','2000-12-29 09:41:34','2013-11-14 13:11:55','https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/shopping%2FDrawKit%20Vector%20Illustration%20Black%20Friday%20%26%20Online%20Shopping%20(3).png',0),(4,'Mr.',2,'男性 快递员 礼物 送货  绿色 毛衣','2004-11-15 17:49:59','2015-05-15 04:12:30','https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/shopping%2FDrawKit%20Vector%20Illustration%20Black%20Friday%20%26%20Online%20Shopping%20(4).png',1),(5,'Miss.',2,'女性 绿色 购物 推荐','2003-03-03 07:34:14','2010-03-25 00:32:26','https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/shopping%2FDrawKit%20Vector%20Illustration%20Black%20Friday%20%26%20Online%20Shopping%20(5).png',0),(6,'Mrs.',2,'女性 打球 网球 架子 粉色 吊灯 拆礼物 礼品盒','2017-03-20 09:58:56','2015-06-12 07:10:48','https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/shopping%2FDrawKit%20Vector%20Illustration%20Black%20Friday%20%26%20Online%20Shopping%20(6).png',0),(7,'Prof.',2,'男性 快递员 送货 自行车 礼品 礼物 帽子','2003-06-24 02:32:52','2012-07-17 00:31:17','https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/shopping%2FDrawKit%20Vector%20Illustration%20Black%20Friday%20%26%20Online%20Shopping%20(7).png',1),(8,'Mr.',2,'女性 购物车 手推车 超市 礼品 礼物 眼镜','2011-01-19 02:39:26','2020-11-07 14:53:43','https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/shopping%2FDrawKit%20Vector%20Illustration%20Black%20Friday%20%26%20Online%20Shopping%20(8).png',0),(9,'Ms.',2,'女性 吊灯 购物 笔记本电脑 橙汁 花瓶','2001-09-01 02:30:25','2007-12-14 13:36:57','https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/shopping%2FDrawKit%20Vector%20Illustration%20Black%20Friday%20%26%20Online%20Shopping%20(9).png',0),(10,'Miss.',1,'万圣 书 女巫 紫色 橙色 药水 魔法','2003-11-20 10:09:08','2013-03-04 12:54:46','https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/halloween%2FDrawKit%20Vector%20Illustrations%20Halloween%20Illustrations%20(10).png',0),(11,'Mrs.',1,'僵尸 墓碑 紫色 万圣','2017-10-10 22:18:12','2012-01-03 13:19:50','https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/halloween%2FDrawKit%20Vector%20Illustrations%20Halloween%20Illustrations%20(11).png',0),(12,'Ms.',1,'僵尸 强壮 万圣 紫色 橙色  扳手 镜子 伤疤','2004-01-14 13:43:46','2002-05-15 21:55:30','https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/halloween%2FDrawKit%20Vector%20Illustrations%20Halloween%20Illustrations%20(3).png',0),(13,'Miss.',1,'幽灵 南瓜 万圣','2015-10-01 12:03:52','2000-02-27 17:31:16','https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/halloween%2FDrawKit%20Vector%20Illustrations%20Halloween%20Illustrations%20(4).png',0),(14,'Mrs.',1,'万圣 绷带 南瓜 卫生纸','2021-11-21 21:44:05','2001-12-24 20:02:08','https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/halloween%2FDrawKit%20Vector%20Illustrations%20Halloween%20Illustrations%20(5).png',0),(15,'Prof.',1,'南瓜 录音机 强壮 女巫 棒球 糖果  僵尸','2014-08-28 12:23:02','2002-04-09 10:57:23','https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/halloween%2FDrawKit%20Vector%20Illustrations%20Halloween%20Illustrations%20(6).png',0),(16,'Ms.',1,'南瓜 万圣  蜡烛 糖果 帽子','2009-08-15 02:07:30','2010-05-11 06:04:47','https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/halloween%2FDrawKit%20Vector%20Illustrations%20Halloween%20Illustrations%20(7).png',0),(17,'Mr.',1,'女巫 魔杖 男性 海泽 男孩 万圣','2010-01-16 15:03:41','2004-07-19 18:42:10','https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/halloween%2FDrawKit%20Vector%20Illustrations%20Halloween%20Illustrations%20(8).png',0),(18,'Mrs.',1,'吸血鬼 月亮 蝙蝠 万圣','2001-04-04 04:47:46','2011-10-06 23:57:10','https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/halloween%2FDrawKit%20Vector%20Illustrations%20Halloween%20Illustrations%20(9).png',0),(98,'halloween_1',1,'万圣 树 房子','2023-02-14 17:21:56',NULL,'https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/halloween_1.png',0),(99,'halloween_2',1,'万圣 死神','2023-02-14 17:22:23',NULL,'https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/halloween_2.png',0),(100,'shopping_1',2,'男性 笔记本电脑 短袖 手机 购物 眼镜','2023-02-14 17:22:23',NULL,'https://postcard-maker-1304263782.cos.ap-hongkong.myqcloud.com/shopping%2FDrawKit%20Vector%20Illustration%20Black%20Friday%20%26%20Online%20Shopping%20(1).png',0);
/*!40000 ALTER TABLE `t_illustration` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-30 16:52:30
