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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adoption`
--

LOCK TABLES `adoption` WRITE;
/*!40000 ALTER TABLE `adoption` DISABLE KEYS */;
INSERT INTO `adoption` VALUES (1,3,1,'只能说这就是原神给我的骄傲的资本',0,'2023-04-26 21:31:36');
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
INSERT INTO `animal` VALUES (1,'馆长','大家好，我是馆长',0,NULL),(20,'提米','嗨，我素提米',1,'/static/images/animal/20.png'),(21,'小二','这是小儿',0,'/root/AnimalManagement/src/main/resources/static/images/animal/temp/2f94bc4c-c8e5-4460-a0d8-42245d67a97a.jpg'),(22,'小9','这是小9',0,'/static/images/animal/22.png'),(23,'测试动物','测试档案',0,'/root/AnimalManagement/src/main/resources/static/images/animal/temp/f3330ee0-cf51-4a8c-ad83-afcfd8f3c14e.jpg'),(24,'小1','1',0,'/static/images/animal/24.png'),(25,'小2','2',0,'/static/images/animal/25.png'),(26,'小3','3',0,'/static/images/animal/26.png'),(27,'小4','4',0,'/static/images/animal/27.png'),(28,'小5','5',0,'/static/images/animal/28.png'),(29,'小6','6',0,'/static/images/animal/29.png'),(30,'小7','7',0,'/static/images/animal/30.png'),(31,'小8','8',0,'/static/images/animal/31.png'),(32,'小10','10',0,'/static/images/animal/32.png'),(33,'小11','11',0,'/static/images/animal/33.png'),(34,'小12','12',0,'/static/images/animal/34.png'),(35,'小13','13',0,'/static/images/animal/35.png'),(36,'小14','14',1,'/static/images/animal/36.png'),(37,'小15','15',1,'/static/images/animal/37.png'),(38,'小16','16',0,'/static/images/animal/38.png'),(39,'小17','17',0,'/static/images/animal/39.png'),(40,'小18','18',0,'/static/images/animal/40.png'),(41,'小19','19',0,'/static/images/animal/41.png'),(42,'小20','20',0,'/static/images/animal/42.png'),(43,'小21','21',0,'/static/images/animal/43.png'),(44,'小22','22',0,'/static/images/animal/44.png'),(45,'小23','23',0,'/static/images/animal/45.png'),(46,'小24','24',0,'/static/images/animal/46.png'),(47,'小25','25',0,'/static/images/animal/47.png'),(48,'小26','26',0,'/static/images/animal/48.png'),(49,'小27','27',0,'/static/images/animal/49.png'),(50,'小28','28',0,'/static/images/animal/50.png'),(51,'小29','29',0,'/static/images/animal/51.png'),(52,'小30','30',0,'/static/images/animal/52.png'),(53,'阿红','234',1,'/root/AnimalManagement/src/main/resources/static/images/animal/temp/34861864-d7ed-4350-95eb-a10a06845a4a.jpg'),(54,'咪咪','猫',0,'/root/AnimalManagement/src/main/resources/static/images/animal/temp/b4d8e8d9-c3a9-45b5-a0cd-246acb5d4c5c.jpg');
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
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (26,3,18,'试试','2023-04-27 14:18:29',0,0,1,1),(27,3,18,'试一试','2023-04-27 14:19:17',0,0,1,1),(28,3,18,'再来一个','2023-04-27 14:19:21',1,0,1,0),(29,3,20,'asd','2023-04-27 14:22:01',0,0,1,1),(30,3,20,'qwq','2023-04-27 14:22:05',1,0,1,0),(35,9,18,'你干嘛','2023-04-28 12:34:45',0,0,0,0),(44,3,19,'我的妈呀','2023-05-03 01:56:08',0,0,1,0);
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
INSERT INTO `commentlike` VALUES (9,28),(3,30);
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_user`
--

LOCK TABLES `sys_role_user` WRITE;
/*!40000 ALTER TABLE `sys_role_user` DISABLE KEYS */;
INSERT INTO `sys_role_user` VALUES (1,2,1),(2,3,2),(3,4,2),(4,5,2),(5,6,2),(6,7,2),(7,8,2),(8,9,2),(9,10,2);
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (2,'admin','$2a$10$5T851lZ7bc2U87zjt/9S6OkwmLW62tLeGLB2aCmq3XRZHA7OI7Dqa','NORMAL'),(3,'user','$2a$10$szHoqQ64g66PymVJkip98.Fap21Csy8w.RD8v5Dhq08BMEZ9KaSmS','NORMAL'),(4,'pxttt','$2a$10$LokC.pRvYA9ytrThYHiaUujiPxDKi72n2wat/JqrZtHX8OARa9..e','NORMAL'),(5,'MaYouSYQ','$2a$10$ywSM6kU6yqxIpHBqMSYsrOY2fjOZY7Ec9qrqeiJkrZgTjIdvg4LTm','NORMAL'),(6,'dsw','$2a$10$w9hN4ictRhnj9wMXy63MNOw5sS7fB6X18xQO76DSAxaMv0vBgpR0u','NORMAL'),(7,'syncline','$2a$10$Lg13X95/WJyScA2HHwl0p.OaMi8.GmGjWQ.ZhYnvICD9u03mpXTSa','NORMAL'),(8,'jht','$2a$10$D0Yk8tz4Xw0fsNSaXj4IzeX6vUgwsZ8pSn5JneHKjIuU9BP867aca','NORMAL'),(9,'zyjj','$2a$10$fkQzkx6KuFVwu8gsZ8XUsupHqEVd651/fRT5pnh/4qIS6bm6bDAFC','NORMAL'),(10,'syq','$2a$10$BlATmeAmbdSfV8yIg4.SZOYrdbDol2du8SKHCx/3BCoe0xUKr5tTK','NORMAL');
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` VALUES (6,'原神'),(2,'提米'),(3,'狗'),(4,'猫'),(5,'白色'),(1,'馆长');
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
  `animal_id` int NOT NULL,
  `time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `location_x` int NOT NULL,
  `location_y` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `animal_id` (`animal_id`),
  CONSTRAINT `track_ibfk_1` FOREIGN KEY (`animal_id`) REFERENCES `animal` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `track`
--

LOCK TABLES `track` WRITE;
/*!40000 ALTER TABLE `track` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tweet`
--

LOCK TABLES `tweet` WRITE;
/*!40000 ALTER TABLE `tweet` DISABLE KEYS */;
INSERT INTO `tweet` VALUES (15,3,'114514','1919810','/static/images/tweet/15_0.png','2023-04-27 09:44:26',0,0,0,0,0,0,0,2,1,0),(18,3,'12345','45678','/static/images/tweet/18_0.png;/static/images/tweet/18_1.png','2023-04-27 09:52:34',0,0,0,2,4,0,0,1,1,0),(19,3,'jjj','fghjgfghf','/static/images/tweet/19_0.png;/static/images/tweet/19_1.png;/static/images/tweet/19_2.png;/static/images/tweet/19_3.png','2023-04-27 10:00:39',0,0,0,0,1,0,0,1,1,0),(20,3,'1111314','14122','/static/images/tweet/20_0.png;/static/images/tweet/20_1.png;/static/images/tweet/20_2.png','2023-04-27 10:03:52',0,0,1,0,2,0,0,1,1,0),(21,3,'dfsfsd','dffafdssddas','/static/images/tweet/21_0.png','2023-04-27 11:07:39',0,0,1,1,0,0,0,1,1,0),(22,3,'dsasd','aafafaffaf','/static/images/tweet/22_0.png','2023-04-27 11:11:13',0,0,0,0,0,1,1,1,1,0),(23,3,'helpppppp','dddddd','/static/images/tweet/23_0.png','2023-04-27 11:14:03',0,0,0,0,0,1,1,1,1,0),(24,3,'fafa','fff','/static/images/tweet/24_0.png','2023-04-27 11:15:47',0,0,0,0,0,0,0,1,1,0),(25,5,'原神新活动上线','免费领原石了！','/static/images/tweet/25_0.png','2023-04-27 11:46:08',0,0,2,1,0,0,0,1,1,0),(30,3,'是是是','洒洒水','/static/images/tweet/30_0.png','2023-04-27 14:24:31',0,0,1,1,0,0,0,1,1,0),(31,7,'求助','这是一个求助','/static/images/tweet/31_0.png','2023-04-27 14:30:59',0,0,0,0,0,1,1,1,1,0),(32,5,'馆长的消失','今天没有在学校里找到馆长','/static/images/tweet/32_0.png','2023-04-27 14:32:11',0,0,0,0,0,0,0,1,1,0),(33,3,'帖子1','鱼鱼','/static/images/tweet/33_0.png','2023-04-27 14:37:48',0,0,0,0,0,0,0,1,1,0),(41,3,'asd','qwq','/static/images/tweet/41_0.png','2023-04-27 14:47:09',0,0,0,0,0,0,0,1,1,0),(44,8,'小9','小9','/static/images/tweet/44_0.png','2023-04-27 15:24:04',0,0,1,1,0,0,0,1,1,0),(50,8,'测试空','测试空','/static/images/tweet/50_0.png','2023-04-27 23:22:35',0,0,0,0,0,0,0,1,1,0),(53,10,'这是我发的第一个贴子','你好','/static/images/tweet/53_0.png','2023-05-04 12:35:06',0,0,0,0,0,0,0,0,1,0),(54,10,'贴子可见性测试','看得到吗','/static/images/tweet/54_0.png;/static/images/tweet/54_1.png','2023-05-04 12:36:20',0,0,0,0,0,0,0,0,1,0);
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
INSERT INTO `tweetlike` VALUES (3,20),(3,21),(5,25),(10,25),(9,30),(10,44);
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
INSERT INTO `tweetstar` VALUES (3,18),(8,18),(8,21),(10,25),(9,30),(10,44);
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
  `avatar` varchar(128) DEFAULT NULL,
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
INSERT INTO `userinfo` VALUES (2,'admin','20000000@buaa.edu.cn','15000000000','This is a bio','path',0),(3,'user','20000001@buaa.edu.cn','15000000001','abcd','/static/images/user/3.png',0),(4,'pxttt','2267413596@qq.com','15816546794','这个人是个OP，还没有个性签名','/static/images/user/4.png',0),(5,'MaYouSYQ','411141617@qq.com','15338620897','这个人是个OP，还没有个性签名',NULL,0),(6,'dsw','1250747862@qq.com','13680065777','这个人是个OP，还没有个性签名',NULL,0),(7,'syncline','1050826076@qq.com','13070110605','这个人是个OP，还没有个性签名',NULL,0),(8,'jht','1462256245@qq.com','15558308888','我的个性签名','/static/images/user/8.png',0),(9,'zyjj','germainzhongying@163.com','12345678989','这个人是个OP，还没有个性签名',NULL,0),(10,'syq','m411141617@163.com','15599202806','这个人是个OP，还没有个性签名',NULL,0);
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

-- Dump completed on 2023-05-04 16:30:33
