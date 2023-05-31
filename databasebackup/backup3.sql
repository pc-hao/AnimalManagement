-- MySQL dump 10.13  Distrib 8.0.26, for Linux (x86_64)
--
-- Host: localhost    Database: animalmanagement
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `adoption`
--

DROP TABLE IF EXISTS `adoption`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adoption` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `animal_id` int NOT NULL,
  `reason` varchar(1024) NOT NULL,
  `censored` int NOT NULL DEFAULT '0',
  `time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `animal_id` (`animal_id`),
  CONSTRAINT `adoption_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `adoption_ibfk_2` FOREIGN KEY (`animal_id`) REFERENCES `animal` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adoption`
--

LOCK TABLES `adoption` WRITE;
/*!40000 ALTER TABLE `adoption` DISABLE KEYS */;
INSERT INTO `adoption` VALUES (1,3,1,'只能说这就是原神给我的骄傲的资本',1,'2023-04-26 21:31:36'),(2,7,22,'我希望领养',1,'2023-05-11 14:16:57'),(3,8,24,'我要',1,'2023-05-17 11:12:47'),(4,8,38,'uuuuuu',1,'2023-05-17 16:17:31'),(5,8,42,'我要领养我要领养我要领养我要领养我要领养我要领养我要领养我要领养',1,'2023-05-17 20:38:03'),(6,8,54,'',0,'2023-05-18 04:00:27'),(7,3,47,'我会好好照顾她的',1,'2023-05-18 10:33:18'),(8,11,1,'就是玩，诶~',0,'2023-05-18 11:45:35');
/*!40000 ALTER TABLE `adoption` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `animal`
--

DROP TABLE IF EXISTS `animal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `animal` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `intro` varchar(256) NOT NULL DEFAULT '管理员是个OP，还没设置动物介绍',
  `adopted` tinyint(1) NOT NULL DEFAULT '0',
  `avatar` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `animal`
--

LOCK TABLES `animal` WRITE;
/*!40000 ALTER TABLE `animal` DISABLE KEYS */;
INSERT INTO `animal` VALUES (1,'馆长','大家好，我是馆长',0,'/static/images/animal/54.png'),(20,'提米','嗨，我素提米',1,'/static/images/animal/20.png'),(21,'小二','这是小儿',0,'/static/images/animal/21.png'),(22,'小9','这是小9',1,'/static/images/animal/22.png'),(23,'测试动物','测试档案',0,'/static/images/animal/23.png'),(24,'小1','1',0,'/static/images/animal/24_0.png;/static/images/animal/24_1.png'),(25,'小2','2',0,'/static/images/animal/25.png'),(26,'小3','3',0,'/static/images/animal/26.png'),(27,'小4','4',0,'/static/images/animal/27.png'),(28,'小5','5',0,'/static/images/animal/28.png'),(29,'小6','6',0,'/static/images/animal/29.png'),(30,'小7','7',0,'/static/images/animal/30.png'),(31,'小8','8',0,'/static/images/animal/31.png'),(32,'小10','10',0,'/static/images/animal/32.png'),(33,'小11','11',0,'/static/images/animal/33.png'),(34,'小12','12',0,'/static/images/animal/34.png'),(35,'小13','13',0,'/static/images/animal/35.png'),(36,'小14','这是小14',1,'/static/images/animal/36.png'),(37,'小15','15',1,'/static/images/animal/37.png'),(38,'小16','16',0,'/static/images/animal/38.png'),(39,'小17','17',0,'/static/images/animal/39.png'),(40,'小18','18',0,'/static/images/animal/40.png'),(41,'小19','19',0,'/static/images/animal/41.png'),(42,'小20','20',1,'/static/images/animal/42.png'),(43,'小21','21',0,'/static/images/animal/43.png'),(44,'小22','22',0,'/static/images/animal/44.png'),(45,'小23','23',0,'/static/images/animal/45.png'),(46,'小24','24',0,'/static/images/animal/46.png'),(47,'小25','25',1,'/static/images/animal/47.png'),(48,'小26','26',0,'/static/images/animal/48.png'),(49,'小27','27',0,'/static/images/animal/49.png'),(50,'小28','28',0,'/static/images/animal/50.png'),(51,'小29','29',0,'/static/images/animal/51.png'),(52,'小30','30',0,'/static/images/animal/52.png'),(53,'阿红','234',1,'/static/images/animal/53.png'),(54,'咪咪','猫',0,'/static/images/animal/54.png');
/*!40000 ALTER TABLE `animal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `tweet_id` int NOT NULL,
  `content` varchar(1024) NOT NULL,
  `time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `likes` int NOT NULL DEFAULT '0',
  `is_help` tinyint(1) NOT NULL DEFAULT '0',
  `censored` int NOT NULL DEFAULT '0',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `tweet_id` (`tweet_id`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`tweet_id`) REFERENCES `tweet` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (26,3,18,'试试','2023-04-27 14:18:29',0,0,1,1),(27,3,18,'试一试','2023-04-27 14:19:17',0,0,1,1),(28,3,18,'再来一个','2023-04-27 14:19:21',2,0,1,1),(29,3,20,'asd','2023-04-27 14:22:01',0,0,1,1),(30,3,20,'qwq','2023-04-27 14:22:05',1,0,1,0),(35,9,18,'你干嘛','2023-04-28 12:34:45',2,0,1,0),(44,3,19,'我的妈呀','2023-05-03 01:56:08',0,0,1,1),(45,3,55,'这是个什么求助（恼！','2023-05-05 19:44:38',1,0,1,0),(46,3,55,'我的评论被吞了？','2023-05-05 19:47:31',0,0,1,0),(47,2,55,'自求多福吧','2023-05-09 21:48:20',0,0,2,0),(48,2,55,'玩原神玩的','2023-05-09 21:56:02',0,0,2,0),(49,2,55,'吸氧','2023-05-09 22:00:46',0,0,1,0),(50,2,55,'别让我审核自己的评论！','2023-05-09 22:03:55',0,0,1,0),(51,2,55,'1','2023-05-09 22:12:30',0,0,1,0),(52,3,44,'长得好丑','2023-05-10 23:33:14',0,0,2,0),(53,8,50,'大家好','2023-05-17 23:34:10',0,0,1,0),(54,10,22,'test','2023-05-18 09:30:52',0,0,1,0),(55,10,56,'试试','2023-05-18 09:37:11',0,0,2,0),(56,3,32,'哈哈哈哈','2023-05-20 10:09:26',0,0,1,0),(57,3,55,'大大的疑惑','2023-05-21 10:25:48',0,0,1,0),(58,3,55,'什么都不懂啊','2023-05-21 10:26:05',1,0,1,0),(59,3,55,'多发一些评论','2023-05-21 10:27:13',0,0,1,0),(60,3,55,'球球管理通过啊','2023-05-21 10:27:37',0,0,1,0),(61,3,55,'？？？','2023-05-21 10:27:58',0,0,1,0),(62,3,55,'！！！','2023-05-21 10:28:02',0,0,1,0),(63,3,55,'qwq','2023-05-21 10:28:16',0,0,1,0),(64,2,55,'所以你觉得你自己很可爱是吧？','2023-05-21 10:28:24',0,0,1,0),(65,3,55,'不过审什么的不要啊','2023-05-21 10:28:30',0,0,1,0),(66,3,55,'o\np\ny\ny\nd\ns','2023-05-21 10:28:46',0,0,1,0),(67,3,55,'longlonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglong','2023-05-21 10:31:34',0,0,1,0);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `commentlike`
--

DROP TABLE IF EXISTS `commentlike`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `commentlike` (
  `user_id` int NOT NULL,
  `comment_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`comment_id`),
  KEY `comment_id` (`comment_id`),
  CONSTRAINT `commentlike_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `commentlike_ibfk_2` FOREIGN KEY (`comment_id`) REFERENCES `comment` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `commentlike`
--

LOCK TABLES `commentlike` WRITE;
/*!40000 ALTER TABLE `commentlike` DISABLE KEYS */;
INSERT INTO `commentlike` VALUES (4,28),(9,28),(3,30),(3,35),(4,35),(3,45),(3,58);
/*!40000 ALTER TABLE `commentlike` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `content` varchar(1024) NOT NULL,
  `read` tinyint(1) NOT NULL DEFAULT '0',
  `time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `message_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (10,7,'您的领养申请已通过，请等待工作人员进一步联系',0,'2023-05-17 20:48:53'),(46,3,'您的贴子：“这只猫猫好酷”未能通过，理由如下：',1,'2023-05-17 22:46:53'),(51,3,'您的贴子：“这只猫猫好酷”未能通过，理由如下：',1,'2023-05-17 22:47:01'),(52,3,'您的贴子：“这只猫猫好酷”未能通过，理由如下：',1,'2023-05-17 22:47:03'),(53,3,'您的贴子：“这只猫猫好酷”未能通过，理由如下：',1,'2023-05-17 22:47:04'),(54,8,'您的贴子：“ssss”未能通过，理由如下：\n',0,'2023-05-17 22:47:06'),(55,8,'您的贴子：“ssss”未能通过，理由如下：\n',0,'2023-05-17 22:47:08'),(56,8,'您的贴子：“啊打发”未能通过，理由如下：\n',0,'2023-05-17 22:47:10'),(57,8,'您的贴子：“ddd”未能通过，理由如下：\n',0,'2023-05-17 22:47:12'),(58,8,'您的贴子：“123”未能通过，理由如下：\n',0,'2023-05-17 22:47:14'),(59,8,'您的贴子：“123”未能通过，理由如下：\n',0,'2023-05-17 22:47:16'),(60,8,'您的贴子：“adsf”未能通过，理由如下：\n',0,'2023-05-17 22:47:18'),(61,8,'您的贴子：“hhhh”未能通过，理由如下：\n',0,'2023-05-17 22:47:35'),(62,8,'您的贴子：“hhhhhh”未能通过，理由如下：\n',0,'2023-05-17 22:47:37'),(63,8,'您的贴子：“1234”未能通过，理由如下：\n',0,'2023-05-17 22:47:39'),(64,8,'您的贴子：“123134”未能通过，理由如下：\n',0,'2023-05-17 22:47:41'),(65,8,'您的贴子：“123123”未能通过，理由如下：\n',0,'2023-05-17 22:47:43'),(66,8,'您的贴子：“123123”未能通过，理由如下：\n',0,'2023-05-17 22:47:45'),(67,11,'您的贴子：“好大一摊乖白啊”已通过',0,'2023-05-18 13:21:25'),(68,11,'您的贴子：“帅帅的花花”已通过',0,'2023-05-18 13:21:28'),(69,11,'您的贴子：“可爱的绿茶捏”已通过',0,'2023-05-18 13:21:32'),(70,11,'您的贴子：“我爱小乖”已通过',0,'2023-05-18 13:21:36'),(71,3,'您的贴子：“TEST”未能通过，理由如下：',1,'2023-05-18 13:21:42'),(72,8,'您的贴子：“不要通过”未能通过，理由如下：\n',0,'2023-05-18 13:21:45'),(73,8,'您的贴子：“初次”未能通过，理由如下：\n',0,'2023-05-18 13:21:47'),(74,8,'您的贴子：“123123”未能通过，理由如下：\n',0,'2023-05-18 13:21:51'),(75,10,'您的评论：“试试”未能通过，理由如下：\n你在test什么？',1,'2023-05-18 13:22:20'),(76,10,'您的评论：“test”已通过',1,'2023-05-18 13:22:48'),(77,2,'您的评论：“自求多福吧”未能通过，理由如下：\n',0,'2023-05-18 13:22:54'),(78,2,'您的评论：“玩原神玩的”未能通过，理由如下：\n',0,'2023-05-18 13:22:56'),(79,8,'您的评论：“大家好”已通过',1,'2023-05-18 13:23:01'),(80,3,'您的领养申请已通过，请等待工作人员进一步联系',1,'2023-05-18 13:23:25'),(81,3,'您的评论：“哈哈哈哈”已通过',0,'2023-05-21 10:28:36'),(82,3,'您的评论：“我的评论被吞了？”已通过',0,'2023-05-21 10:28:47'),(83,2,'您的评论：“吸氧”已通过',0,'2023-05-21 10:28:48'),(84,2,'您的评论：“别让我审核自己的评论！”已通过',0,'2023-05-21 10:28:50'),(85,3,'您的评论：“大大的疑惑”已通过',0,'2023-05-21 10:28:51'),(86,3,'您的评论：“什么都不懂啊”已通过',0,'2023-05-21 10:28:53'),(87,3,'您的评论：“多发一些评论”已通过',0,'2023-05-21 10:28:54'),(88,3,'您的评论：“球球管理通过啊”已通过',0,'2023-05-21 10:28:55'),(89,3,'您的评论：“？？？”已通过',0,'2023-05-21 10:28:56'),(90,3,'您的评论：“！！！”已通过',0,'2023-05-21 10:28:56'),(91,3,'您的评论：“qwq”已通过',0,'2023-05-21 10:28:57'),(92,3,'您的评论：“不过审什么的不要啊”已通过',0,'2023-05-21 10:28:58'),(93,3,'您的评论：“o\np\ny\ny\nd\ns”已通过',0,'2023-05-21 10:28:58'),(94,8,'您的贴子：“测试”已通过',0,'2023-05-21 10:29:02'),(95,3,'您的评论：“longlonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglong”已通过',0,'2023-05-21 10:32:28');
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'ADMIN'),(2,'USER');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_user`
--

DROP TABLE IF EXISTS `sys_role_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `role_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `sys_role_user_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `sys_role_user_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_user`
--

LOCK TABLES `sys_role_user` WRITE;
/*!40000 ALTER TABLE `sys_role_user` DISABLE KEYS */;
INSERT INTO `sys_role_user` VALUES (1,2,1),(2,3,2),(3,4,2),(4,5,2),(5,6,2),(6,7,2),(7,8,2),(8,9,2),(9,10,2),(10,11,2),(11,12,2);
/*!40000 ALTER TABLE `sys_role_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL,
  `password` varchar(256) NOT NULL,
  `status` varchar(10) NOT NULL COMMENT 'NORMAL正常  PROHIBIT禁用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (2,'admin','$2a$10$5T851lZ7bc2U87zjt/9S6OkwmLW62tLeGLB2aCmq3XRZHA7OI7Dqa','NORMAL'),(3,'user','$2a$10$szHoqQ64g66PymVJkip98.Fap21Csy8w.RD8v5Dhq08BMEZ9KaSmS','NORMAL'),(4,'pxttt','$2a$10$LokC.pRvYA9ytrThYHiaUujiPxDKi72n2wat/JqrZtHX8OARa9..e','NORMAL'),(5,'MaYouSYQ','$2a$10$ywSM6kU6yqxIpHBqMSYsrOY2fjOZY7Ec9qrqeiJkrZgTjIdvg4LTm','NORMAL'),(6,'dsw','$2a$10$w9hN4ictRhnj9wMXy63MNOw5sS7fB6X18xQO76DSAxaMv0vBgpR0u','NORMAL'),(7,'syncline','$2a$10$Lg13X95/WJyScA2HHwl0p.OaMi8.GmGjWQ.ZhYnvICD9u03mpXTSa','NORMAL'),(8,'jht','$2a$10$nzvs2tM0mnZCsaiM3P.k9u6709lOhbBBC2k.Er7ZzgHRZqqTJkGZe','NORMAL'),(9,'zyjj','$2a$10$fkQzkx6KuFVwu8gsZ8XUsupHqEVd651/fRT5pnh/4qIS6bm6bDAFC','NORMAL'),(10,'syq','$2a$10$BlATmeAmbdSfV8yIg4.SZOYrdbDol2du8SKHCx/3BCoe0xUKr5tTK','NORMAL'),(11,'ddsw','$2a$10$/CrU.S3vTT.MOvxkb8HgNuOHufnW5l4klnyRkMjFJeAY.pCy0DHxi','NORMAL'),(12,'asd','$2a$10$EwbK7z/sB9gwTVKMoRtUzO8gP4.6IuS9B.zI/9Pl7dncrZLs5a7tG','NORMAL');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tag` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `content` (`content`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` VALUES (11,'111'),(7,'123'),(8,'hhhh'),(10,'mmmm'),(9,'uuu'),(12,'乖白'),(6,'原神'),(13,'可爱'),(2,'提米'),(3,'狗'),(4,'猫'),(5,'白色'),(15,'绿茶'),(14,'花花'),(1,'馆长'),(16,'鸳鸯');
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `token`
--

DROP TABLE IF EXISTS `token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `token` (
  `token` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`token`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `token_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `token`
--

LOCK TABLES `token` WRITE;
/*!40000 ALTER TABLE `token` DISABLE KEYS */;
/*!40000 ALTER TABLE `token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `track`
--

DROP TABLE IF EXISTS `track`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `track` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `animal_id` int NOT NULL,
  `time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `location` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `animal_id` (`animal_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `track_ibfk_1` FOREIGN KEY (`animal_id`) REFERENCES `animal` (`id`),
  CONSTRAINT `track_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `track`
--

LOCK TABLES `track` WRITE;
/*!40000 ALTER TABLE `track` DISABLE KEYS */;
INSERT INTO `track` VALUES (1,3,24,'2023-05-17 20:08:00',6),(2,3,24,'2023-05-18 08:31:00',0),(3,11,1,'2023-05-18 10:12:00',0),(4,11,1,'2023-05-18 11:12:00',0),(5,3,36,'2023-05-20 10:21:00',0),(6,3,24,'2023-05-20 10:32:00',0),(7,3,24,'2023-05-20 10:32:00',0),(8,3,32,'2023-05-20 09:37:00',13),(9,3,24,'2023-05-20 10:40:00',8);
/*!40000 ALTER TABLE `track` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tweet`
--

DROP TABLE IF EXISTS `tweet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tweet` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `title` varchar(100) DEFAULT NULL,
  `content` varchar(1024) NOT NULL DEFAULT '',
  `images` varchar(640) DEFAULT NULL,
  `time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `views` int NOT NULL DEFAULT '0',
  `views_weekly` int NOT NULL DEFAULT '0',
  `likes` int NOT NULL DEFAULT '0',
  `stars` int NOT NULL DEFAULT '0',
  `comments` int NOT NULL DEFAULT '0',
  `is_help` tinyint(1) NOT NULL DEFAULT '0',
  `solved` tinyint(1) NOT NULL DEFAULT '0',
  `censored` int NOT NULL DEFAULT '0',
  `published` tinyint(1) NOT NULL DEFAULT '0',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `tweet_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=129 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tweet`
--

LOCK TABLES `tweet` WRITE;
/*!40000 ALTER TABLE `tweet` DISABLE KEYS */;
INSERT INTO `tweet` VALUES (15,3,'114514','1919810','/static/images/tweet/15_0.png','2023-04-27 09:44:26',0,0,0,0,0,0,0,2,1,0),(18,3,'12345','45678','/static/images/tweet/18_0.png;/static/images/tweet/18_1.png','2023-04-27 09:52:34',0,0,3,3,4,0,0,1,1,0),(19,3,'jjj','fghjgfghf','/static/images/tweet/19_0.png;/static/images/tweet/19_1.png;/static/images/tweet/19_2.png;/static/images/tweet/19_3.png','2023-04-27 10:00:39',0,0,5,2,1,0,0,1,1,0),(20,3,'1111314','14122','/static/images/tweet/20_0.png;/static/images/tweet/20_1.png;/static/images/tweet/20_2.png','2023-04-27 10:03:52',0,0,1,0,2,0,0,1,1,0),(21,3,'dfsfsd','dffafdssddas','/static/images/tweet/21_0.png','2023-04-27 11:07:39',0,0,1,0,0,0,0,1,1,0),(22,3,'dsasd','aafafaffaf','/static/images/tweet/22_0.png','2023-04-27 11:11:13',0,0,1,2,1,1,1,1,1,0),(23,3,'helpppppp','dddddd','/static/images/tweet/23_0.png','2023-04-27 11:14:03',0,0,1,0,0,1,0,1,1,0),(24,3,'fafa','fff','/static/images/tweet/24_0.png','2023-04-27 11:15:47',0,0,0,1,0,0,0,1,1,0),(25,5,'原神新活动上线','免费领原石了！','/static/images/tweet/25_0.png','2023-04-27 11:46:08',0,0,25,1,0,0,0,1,1,0),(30,3,'是是是','洒洒水','/static/images/tweet/30_0.png','2023-04-27 14:24:31',0,0,1,1,0,0,0,1,1,0),(31,7,'求助','这是一个求助','/static/images/tweet/31_0.png','2023-04-27 14:30:59',0,0,1,2,0,1,1,1,1,0),(32,5,'馆长的消失','今天没有在学校里找到馆长','/static/images/tweet/32_0.png','2023-04-27 14:32:11',0,0,0,0,1,0,0,1,1,0),(33,3,'帖子1','鱼鱼','/static/images/tweet/33_0.png','2023-04-27 14:37:48',0,0,0,0,0,0,0,1,1,0),(41,3,'asd','qwq','/static/images/tweet/41_0.png','2023-04-27 14:47:09',0,0,1,0,0,0,0,1,1,0),(44,8,'小9','小9','/static/images/tweet/44_0.png','2023-04-27 15:24:04',0,0,1,2,1,0,1,1,1,1),(50,8,'测试空','测试空','/static/images/tweet/50_0.png','2023-04-27 23:22:35',0,0,0,1,1,0,0,1,1,1),(53,10,'这是我发的第一个贴子','你好','/static/images/tweet/53_0.png','2023-05-04 12:35:06',0,0,0,0,0,0,0,1,1,0),(54,10,'贴子可见性测试','看得到吗','/static/images/tweet/54_0.png;/static/images/tweet/54_1.png','2023-05-04 12:36:20',0,0,1,1,0,0,0,1,1,0),(55,3,'我要求助','被这只猫猫可爱死了','/static/images/tweet/55_0.png','2023-05-05 16:38:42',0,0,3,2,18,1,1,1,1,0),(56,3,'没猫猫照片我很难受','啊啊啊啊啊','/static/images/tweet/56_0.png','2023-05-10 23:37:24',0,0,0,0,1,1,1,1,1,0),(57,3,'测试求助','测试待审核求助','/static/images/tweet/57_0.png','2023-05-13 22:00:29',0,0,0,0,0,1,0,2,1,0),(58,8,'112233445566778899','ceshi',NULL,'2023-05-17 20:09:22',0,0,0,0,0,1,0,0,1,0),(59,8,'这是一只猫猫','这只猫猫好可爱啊',NULL,'2023-05-17 22:10:31',0,0,0,0,0,0,0,2,1,1),(60,8,'这是一只猫猫','这只猫猫好可爱啊',NULL,'2023-05-17 22:10:38',0,0,0,0,0,0,0,2,1,1),(61,8,'这是一只猫猫','这只猫猫好可爱啊',NULL,'2023-05-17 22:10:43',0,0,0,0,0,0,0,2,1,1),(62,8,'这是一只猫猫','这只猫猫好可爱啊',NULL,'2023-05-17 22:11:04',0,0,0,0,0,0,0,2,1,1),(63,3,'这只猫猫好酷','好酷一只猫猫',NULL,'2023-05-17 22:11:33',0,0,0,0,0,0,0,2,1,0),(64,3,'这只猫猫好酷','好酷一只猫猫',NULL,'2023-05-17 22:11:38',0,0,0,0,0,0,0,2,1,0),(65,3,'这只猫猫好酷','好酷一只猫猫',NULL,'2023-05-17 22:11:46',0,0,0,0,0,0,0,2,1,0),(66,3,'这只猫猫好酷','好酷一只猫猫',NULL,'2023-05-17 22:12:06',0,0,0,0,0,0,0,2,1,0),(67,3,'这只猫猫好酷','好酷一只猫猫',NULL,'2023-05-17 22:12:13',0,0,0,0,0,0,0,2,1,0),(68,3,'这只猫猫好酷','好酷一只猫猫',NULL,'2023-05-17 22:12:16',0,0,0,0,0,0,0,2,1,0),(69,3,'这只猫猫好酷','好酷一只猫猫',NULL,'2023-05-17 22:12:17',0,0,0,0,0,0,0,2,1,0),(70,3,'这只猫猫好酷','好酷一只猫猫',NULL,'2023-05-17 22:12:17',0,0,0,0,0,0,0,2,1,0),(71,3,'这只猫猫好酷','好酷一只猫猫',NULL,'2023-05-17 22:12:17',0,0,0,0,0,0,0,2,1,0),(72,3,'这只猫猫好酷','好酷一只猫猫',NULL,'2023-05-17 22:12:17',0,0,0,0,0,0,0,2,1,0),(73,3,'这只猫猫好酷','好酷一只猫猫',NULL,'2023-05-17 22:12:18',0,0,0,0,0,0,0,2,1,0),(74,3,'这只猫猫好酷','好酷一只猫猫',NULL,'2023-05-17 22:12:18',0,0,0,0,0,0,0,2,1,0),(75,3,'这只猫猫好酷','好酷一只猫猫',NULL,'2023-05-17 22:12:18',0,0,0,0,0,0,0,2,1,0),(76,3,'这只猫猫好酷','好酷一只猫猫',NULL,'2023-05-17 22:12:18',0,0,0,0,0,0,0,2,1,0),(77,3,'这只猫猫好酷','好酷一只猫猫',NULL,'2023-05-17 22:12:18',0,0,0,0,0,0,0,2,1,0),(78,3,'这只猫猫好酷','好酷一只猫猫',NULL,'2023-05-17 22:12:19',0,0,0,0,0,0,0,2,1,0),(79,3,'这只猫猫好酷','好酷一只猫猫',NULL,'2023-05-17 22:12:19',0,0,0,0,0,0,0,2,1,0),(80,3,'这只猫猫好酷','好酷一只猫猫',NULL,'2023-05-17 22:12:19',0,0,0,0,0,0,0,2,1,0),(81,3,'这只猫猫好酷','好酷一只猫猫',NULL,'2023-05-17 22:12:19',0,0,0,0,0,0,0,2,1,0),(82,3,'这只猫猫好酷','好酷一只猫猫',NULL,'2023-05-17 22:12:20',0,0,0,0,0,0,0,2,1,0),(83,3,'这只猫猫好酷','好酷一只猫猫',NULL,'2023-05-17 22:12:20',0,0,0,0,0,0,0,2,1,0),(84,3,'这只猫猫好酷','好酷一只猫猫',NULL,'2023-05-17 22:12:20',0,0,0,0,0,0,0,2,1,0),(85,3,'这只猫猫好酷','好酷一只猫猫',NULL,'2023-05-17 22:12:20',0,0,0,0,0,0,0,2,1,0),(86,3,'这只猫猫好酷','好酷一只猫猫',NULL,'2023-05-17 22:12:21',0,0,0,0,0,0,0,2,1,0),(87,3,'这只猫猫好酷','好酷一只猫猫',NULL,'2023-05-17 22:12:21',0,0,0,0,0,0,0,2,1,0),(88,3,'这只猫猫好酷','好酷一只猫猫',NULL,'2023-05-17 22:12:22',0,0,0,0,0,0,0,2,1,0),(89,3,'这只猫猫好酷','好酷一只猫猫',NULL,'2023-05-17 22:12:22',0,0,0,0,0,0,0,2,1,0),(90,3,'这只猫猫好酷','好酷一只猫猫',NULL,'2023-05-17 22:12:22',0,0,0,0,0,0,0,2,1,0),(91,3,'这只猫猫好酷','好酷一只猫猫',NULL,'2023-05-17 22:12:23',0,0,0,0,0,0,0,2,1,0),(92,3,'这只猫猫好酷','好酷一只猫猫',NULL,'2023-05-17 22:12:23',0,0,0,0,0,0,0,2,1,0),(93,3,'这只猫猫好酷','好酷一只猫猫',NULL,'2023-05-17 22:12:23',0,0,0,0,0,0,0,2,1,0),(94,3,'这只猫猫好酷','好酷一只猫猫',NULL,'2023-05-17 22:12:23',0,0,0,0,0,0,0,2,1,0),(95,3,'这只猫猫好酷','好酷一只猫猫',NULL,'2023-05-17 22:12:23',0,0,0,0,0,0,0,2,1,0),(96,3,'这只猫猫好酷','好酷一只猫猫',NULL,'2023-05-17 22:12:24',0,0,0,0,0,0,0,2,1,0),(97,3,'这只猫猫好酷','好酷一只猫猫',NULL,'2023-05-17 22:12:24',0,0,0,0,0,0,0,2,1,0),(98,3,'这只猫猫好酷','好酷一只猫猫',NULL,'2023-05-17 22:12:24',0,0,0,0,0,0,0,2,1,0),(99,3,'这只猫猫好酷','好酷一只猫猫',NULL,'2023-05-17 22:12:24',0,0,0,0,0,0,0,2,1,0),(100,3,'这只猫猫好酷','好酷一只猫猫',NULL,'2023-05-17 22:12:25',0,0,0,0,0,0,0,2,1,0),(101,8,'ssss','ssssss',NULL,'2023-05-17 22:20:03',0,0,0,0,0,0,0,2,1,1),(102,8,'ssss','ssssss',NULL,'2023-05-17 22:21:24',0,0,0,0,0,0,0,2,1,1),(103,8,'啊打发','啊打发撒旦',NULL,'2023-05-17 22:21:47',0,0,0,0,0,0,0,2,1,1),(104,8,'ddd','dddddd',NULL,'2023-05-17 22:23:04',0,0,0,0,0,0,0,2,1,1),(105,8,'123','123131231',NULL,'2023-05-17 22:26:48',0,0,0,0,0,0,0,2,1,1),(106,8,'123','123131231',NULL,'2023-05-17 22:26:58',0,0,0,0,0,0,0,2,1,1),(107,8,'adsf','adsfasdf',NULL,'2023-05-17 22:27:28',0,0,0,0,0,0,0,2,1,1),(108,8,'123123','123123123',NULL,'2023-05-17 22:28:12',0,0,0,0,0,0,0,2,1,1),(109,8,'123123','12313131',NULL,'2023-05-17 22:29:08',0,0,0,0,0,0,0,2,1,1),(110,8,'123123','12313131',NULL,'2023-05-17 22:29:16',0,0,0,0,0,0,0,2,1,1),(111,8,'123134','1241441',NULL,'2023-05-17 22:30:59',0,0,0,0,0,0,0,2,1,1),(112,8,'1234','1233123',NULL,'2023-05-17 22:37:43',0,0,0,0,0,0,0,2,1,1),(113,8,'hhhhhh','哈哈哈哈哈哈哈哈哈',NULL,'2023-05-17 22:45:30',0,0,0,0,0,0,0,2,1,1),(114,8,'hhhh','hhhhhhhh',NULL,'2023-05-17 22:46:19',0,0,0,0,0,0,0,2,1,1),(115,8,'jjjjj','jjjjjjjj','/static/images/tweet/115_0.png','2023-05-17 22:47:58',0,0,0,0,0,0,0,0,1,1),(116,8,'阿萨飒飒阿萨','啊啊飒飒飒飒',NULL,'2023-05-17 22:54:42',0,0,0,0,0,1,0,0,1,0),(117,8,'1111','1111111',NULL,'2023-05-17 22:55:09',0,0,0,0,0,1,0,0,1,0),(118,8,'初次','11','/static/images/tweet/118_0.png;/static/images/tweet/118_1.png','2023-05-17 22:56:34',0,0,0,0,0,0,0,2,1,1),(119,8,'111','1111',NULL,'2023-05-17 22:56:43',0,0,0,0,0,1,0,0,1,0),(120,11,'好大一摊乖白啊','好肥美的乖宝','/static/images/tweet/120_0.png','2023-05-18 11:21:56',0,0,1,1,0,0,0,1,1,0),(121,11,'帅帅的花花','他真的，好帅啊','/static/images/tweet/121_0.png','2023-05-18 11:24:02',0,0,2,1,0,0,0,1,1,0),(122,11,'可爱的绿茶捏','绿茶~','/static/images/tweet/122_0.png','2023-05-18 11:24:32',0,0,1,1,0,0,0,1,1,0),(123,3,'TEST','resr','/static/images/tweet/123_0.png','2023-05-18 12:12:06',0,0,0,0,0,0,0,2,1,0),(124,8,'不要通过','如题','/static/images/tweet/124_0.png','2023-05-18 12:13:25',0,0,0,0,0,0,0,2,1,1),(125,11,'我爱小乖','它真的好可爱(๑• . •๑)','/static/images/tweet/125_0.png','2023-05-18 13:13:03',0,0,1,1,0,0,0,1,1,0),(126,8,'测试','测试','/static/images/tweet/126_0.png','2023-05-20 11:15:35',0,0,0,0,0,0,0,0,1,1),(127,8,'测试','测试','/static/images/tweet/127_0.png','2023-05-20 11:16:28',0,0,0,0,0,0,0,1,1,0),(128,8,'ceshi','ceshi','/static/images/tweet/128_0.png','2023-05-20 12:29:11',0,0,0,0,0,0,0,0,1,1);
/*!40000 ALTER TABLE `tweet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tweetlike`
--

DROP TABLE IF EXISTS `tweetlike`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tweetlike` (
  `user_id` int NOT NULL,
  `tweet_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`tweet_id`),
  KEY `tweet_id` (`tweet_id`),
  CONSTRAINT `tweetlike_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `tweetlike_ibfk_2` FOREIGN KEY (`tweet_id`) REFERENCES `tweet` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tweetlike`
--

LOCK TABLES `tweetlike` WRITE;
/*!40000 ALTER TABLE `tweetlike` DISABLE KEYS */;
INSERT INTO `tweetlike` VALUES (3,18),(4,18),(11,18),(3,19),(4,19),(8,19),(10,19),(11,19),(3,20),(3,21),(10,22),(3,23),(5,25),(10,25),(9,30),(7,31),(11,41),(10,44),(3,54),(3,55),(4,55),(8,55),(11,120),(10,121),(11,121),(11,122),(11,125);
/*!40000 ALTER TABLE `tweetlike` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tweetstar`
--

DROP TABLE IF EXISTS `tweetstar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tweetstar` (
  `user_id` int NOT NULL,
  `tweet_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`tweet_id`),
  KEY `tweet_id` (`tweet_id`),
  CONSTRAINT `tweetstar_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `tweetstar_ibfk_2` FOREIGN KEY (`tweet_id`) REFERENCES `tweet` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tweetstar`
--

LOCK TABLES `tweetstar` WRITE;
/*!40000 ALTER TABLE `tweetstar` DISABLE KEYS */;
INSERT INTO `tweetstar` VALUES (3,18),(4,18),(8,18),(4,19),(8,19),(8,22),(10,22),(10,24),(10,25),(9,30),(3,31),(7,31),(8,44),(10,44),(3,50),(3,54),(3,55),(4,55),(11,120),(11,121),(11,122),(11,125);
/*!40000 ALTER TABLE `tweetstar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tweettag`
--

DROP TABLE IF EXISTS `tweettag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tweettag` (
  `tweet_id` int NOT NULL,
  `tag_id` int NOT NULL,
  PRIMARY KEY (`tweet_id`,`tag_id`),
  KEY `tag_id` (`tag_id`),
  CONSTRAINT `tweettag_ibfk_1` FOREIGN KEY (`tweet_id`) REFERENCES `tweet` (`id`) ON DELETE CASCADE,
  CONSTRAINT `tweettag_ibfk_2` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tweettag`
--

LOCK TABLES `tweettag` WRITE;
/*!40000 ALTER TABLE `tweettag` DISABLE KEYS */;
INSERT INTO `tweettag` VALUES (44,8),(44,9),(115,10),(118,11),(120,12),(120,13),(121,14),(122,15),(123,16);
/*!40000 ALTER TABLE `tweettag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userinfo`
--

DROP TABLE IF EXISTS `userinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `userinfo` (
  `id` int NOT NULL,
  `username` varchar(32) NOT NULL,
  `email` varchar(64) NOT NULL,
  `phone` varchar(12) NOT NULL DEFAULT 'Empty',
  `bio` varchar(64) NOT NULL DEFAULT '这个人是个OP，还没有个性签名',
  `avatar` varchar(128) NOT NULL DEFAULT '/static/images/user/default.png',
  `blacked` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`),
  CONSTRAINT `userinfo_ibfk_1` FOREIGN KEY (`id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userinfo`
--

LOCK TABLES `userinfo` WRITE;
/*!40000 ALTER TABLE `userinfo` DISABLE KEYS */;
INSERT INTO `userinfo` VALUES (2,'admin','20000000@buaa.edu.cn','15000000000','This is a bio','/static/images/user/default.png',0),(3,'user','20000001@buaa.edu.cn','15000000001','我猫瘾太重啦！','/static/images/user/3.png',0),(4,'pxttt','2267413596@qq.com','15816546794','这个人是个OP，还没有个性签名','/static/images/user/4.png',0),(5,'MaYouSYQ','411141617@qq.com','15338620897','这个人是个OP，还没有个性签名','/static/images/user/default.png',0),(6,'dsw','1250747862@qq.com','13680065777','这个人是个OP，还没有个性签名','/static/images/user/default.png',0),(7,'syncline','1050826076@qq.com','13070110605','这个人是个OP，还没有个性签名','/static/images/user/default.png',0),(8,'jht','1462256245@qq.com','15558308888','白马，哼，定叫他有来无回','/static/images/user/8.png',0),(9,'zyjj','germainzhongying@163.com','12345678989','这个人是个OP，还没有个性签名','/static/images/user/default.png',0),(10,'syq','m411141617@163.com','15599202806','这个人是个OP，还没有个性签名','/static/images/user/default.png',0),(11,'ddsw','dingshengweibh@163.com','18922500652','这个人是个OP，还没有个性签名','/static/images/user/default.png',0),(12,'asd','2606565321@qq.com','19139859586','这个人是个OP，还没有个性签名','/static/images/user/default.png',0);
/*!40000 ALTER TABLE `userinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `verification`
--

DROP TABLE IF EXISTS `verification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `verification` (
  `email` varchar(50) NOT NULL,
  `veri_code` varchar(50) NOT NULL,
  `start_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `verification`
--

LOCK TABLES `verification` WRITE;
/*!40000 ALTER TABLE `verification` DISABLE KEYS */;
/*!40000 ALTER TABLE `verification` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-21 13:03:30
