-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: easydoctor
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `appointments`
--

DROP TABLE IF EXISTS `appointments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `canceled_at` datetime DEFAULT NULL,
  `end` datetime DEFAULT NULL,
  `incurred` double DEFAULT NULL,
  `reviewed` tinyint(1) DEFAULT '0',
  `start` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `id_canceler` int DEFAULT NULL,
  `id_doctor` int DEFAULT NULL,
  `id_invoice` int DEFAULT NULL,
  `id_packages` int DEFAULT NULL,
  `id_patient` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKp6776km2twsco3koejhjwb3vk` (`id_canceler`),
  KEY `FKge8rtyq5ytmh61302collabh8` (`id_doctor`),
  KEY `FKlxykh4mtiqydrw2t3cm4nwd9h` (`id_invoice`),
  KEY `FKsfh3cmdvg5ark045cbbvcxp5k` (`id_packages`),
  KEY `FK2qy49b66j7a055m24wbxkk74m` (`id_patient`),
  CONSTRAINT `FK2qy49b66j7a055m24wbxkk74m` FOREIGN KEY (`id_patient`) REFERENCES `patients` (`id_patient`),
  CONSTRAINT `FKge8rtyq5ytmh61302collabh8` FOREIGN KEY (`id_doctor`) REFERENCES `doctors` (`id_doctor`),
  CONSTRAINT `FKlxykh4mtiqydrw2t3cm4nwd9h` FOREIGN KEY (`id_invoice`) REFERENCES `invoices` (`id`),
  CONSTRAINT `FKp6776km2twsco3koejhjwb3vk` FOREIGN KEY (`id_canceler`) REFERENCES `users` (`id`),
  CONSTRAINT `FKsfh3cmdvg5ark045cbbvcxp5k` FOREIGN KEY (`id_packages`) REFERENCES `packages` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointments`
--

LOCK TABLES `appointments` WRITE;
/*!40000 ALTER TABLE `appointments` DISABLE KEYS */;
INSERT INTO `appointments` VALUES (1,NULL,'2022-08-24 10:00:00',25000,0,'2022-08-24 09:00:00','INVOICED',NULL,2,1,1,3),(2,NULL,'2022-08-24 11:00:00',NULL,0,'2022-08-24 10:00:00','SCHEDULED',NULL,2,NULL,1,3),(3,NULL,'2022-08-23 10:00:00',NULL,0,'2022-08-23 09:00:00','FINISHED',NULL,2,NULL,1,3),(4,'2022-08-23 09:57:46','2022-08-25 11:00:00',NULL,0,'2022-08-25 10:00:00','CANCELED',3,2,NULL,1,3),(5,NULL,'2022-08-23 08:00:00',NULL,0,'2022-08-23 07:00:00','FINISHED',NULL,2,NULL,1,3),(6,NULL,'2022-08-19 08:00:00',NULL,0,'2022-08-19 07:00:00','CONFIRMED',NULL,2,NULL,1,3),(7,NULL,'2022-08-20 08:00:00',NULL,0,'2022-08-20 07:00:00','CONFIRMED',NULL,2,NULL,1,3),(8,NULL,'2022-08-26 11:00:00',NULL,1,'2022-08-26 10:00:00','INVOICED',NULL,2,2,2,11);
/*!40000 ALTER TABLE `appointments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clinics`
--

DROP TABLE IF EXISTS `clinics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clinics` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `website` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clinics`
--

LOCK TABLES `clinics` WRITE;
/*!40000 ALTER TABLE `clinics` DISABLE KEYS */;
INSERT INTO `clinics` VALUES (1,'Quận Ngũ Hành Sơn',NULL,'Phòng khám Tâm Đức',NULL,NULL),(2,'Quận Ngũ Hành Sơn',NULL,'Phòng khám Thiện Tâm',NULL,NULL),(3,'Quận Hải Châu',NULL,'Phòng khám Đa Khoa ',NULL,NULL),(4,'Quận Sơn Trà',NULL,'Phòng khám Ngũ Hành Sơn',NULL,NULL),(5,'Quận Sơn Trà',NULL,'Phòng khám An Nghi',NULL,NULL);
/*!40000 ALTER TABLE `clinics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` varchar(255) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `parent_id` bigint DEFAULT NULL,
  `post_id` bigint DEFAULT NULL,
  `update_at` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (1,'2022-08-23 09:49:26','mời các bạn bình luận tại đây',NULL,1,'2022-08-23 09:49:26',4);
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `declarations`
--

DROP TABLE IF EXISTS `declarations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `declarations` (
  `id` int NOT NULL AUTO_INCREMENT,
  `background` varchar(255) DEFAULT NULL,
  `blood` varchar(255) DEFAULT NULL,
  `medicine` varchar(255) DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `symptom` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `declarations`
--

LOCK TABLES `declarations` WRITE;
/*!40000 ALTER TABLE `declarations` DISABLE KEYS */;
INSERT INTO `declarations` VALUES (1,'Bệnh tiều đường','O','covide atra','không','đau đầu, chóng mặt'),(2,'Bệnh tiều đường','O','parasitamo','no','mệt mỏi');
/*!40000 ALTER TABLE `declarations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctors`
--

DROP TABLE IF EXISTS `doctors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctors` (
  `description` varchar(255) DEFAULT NULL,
  `start_practice_date` int DEFAULT NULL,
  `id_doctor` int NOT NULL,
  `id_clinic` int DEFAULT NULL,
  `id_specialty` int DEFAULT NULL,
  PRIMARY KEY (`id_doctor`),
  KEY `FKin6cbifehx64v1fny30fs1pyl` (`id_clinic`),
  KEY `FK5yweqbip3qfw6lqqc35xgiadv` (`id_specialty`),
  CONSTRAINT `FK5yweqbip3qfw6lqqc35xgiadv` FOREIGN KEY (`id_specialty`) REFERENCES `specialties` (`id`),
  CONSTRAINT `FKin6cbifehx64v1fny30fs1pyl` FOREIGN KEY (`id_clinic`) REFERENCES `clinics` (`id`),
  CONSTRAINT `FKr15slrvedif40ffxyfmtd51uf` FOREIGN KEY (`id_doctor`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctors`
--

LOCK TABLES `doctors` WRITE;
/*!40000 ALTER TABLE `doctors` DISABLE KEYS */;
INSERT INTO `doctors` VALUES ('Tận tình giúp đỡ bệnh nhân',NULL,2,1,1),('Hỗ trợ nhiệt tình',NULL,4,1,2),('Chăm sóc chu đáo',NULL,5,2,5),('Kinh nghiệm lâu năm',NULL,6,2,3),('Hỗ trợ nhiệt tình',NULL,7,1,2),('Tiền không phải là tất cả',NULL,8,3,5),('Sức khỏe người bệnh là trên hết',NULL,9,4,3),('Lương y như từ mẫu',NULL,10,3,1);
/*!40000 ALTER TABLE `doctors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `file_model`
--

DROP TABLE IF EXISTS `file_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `file_model` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content_type` varchar(255) DEFAULT NULL,
  `data` longblob,
  `name` varchar(255) DEFAULT NULL,
  `id_history` int DEFAULT NULL,
  `id_user` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK831cgq7ip6huav6jxcthktwir` (`id_history`),
  KEY `FKbs18yb3t5ohjk81w77rlke6mc` (`id_user`),
  CONSTRAINT `FK831cgq7ip6huav6jxcthktwir` FOREIGN KEY (`id_history`) REFERENCES `histories` (`id`),
  CONSTRAINT `FKbs18yb3t5ohjk81w77rlke6mc` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file_model`
--

LOCK TABLES `file_model` WRITE;
/*!40000 ALTER TABLE `file_model` DISABLE KEYS */;
INSERT INTO `file_model` VALUES (1,'application/octet-stream','','',1,3),(2,'application/octet-stream','','',2,11);
/*!40000 ALTER TABLE `file_model` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `histories`
--

DROP TABLE IF EXISTS `histories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `histories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `advice` varchar(255) DEFAULT NULL,
  `diagnose` varchar(255) DEFAULT NULL,
  `doctor` varchar(255) DEFAULT NULL,
  `medicine` varchar(255) DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `paid` tinyint(1) DEFAULT '0',
  `pulished` tinyint(1) DEFAULT '0',
  `result` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `id_appointment` int DEFAULT NULL,
  `id_patient` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrvdgn4r631b6drodo8fvyyo8o` (`id_appointment`),
  KEY `FKha653vmso52s31f95j4n1qavb` (`id_patient`),
  CONSTRAINT `FKha653vmso52s31f95j4n1qavb` FOREIGN KEY (`id_patient`) REFERENCES `patients` (`id_patient`),
  CONSTRAINT `FKrvdgn4r631b6drodo8fvyyo8o` FOREIGN KEY (`id_appointment`) REFERENCES `appointments` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `histories`
--

LOCK TABLES `histories` WRITE;
/*!40000 ALTER TABLE `histories` DISABLE KEYS */;
INSERT INTO `histories` VALUES (1,'ăn uống nghỉ ngơi','bênh trầm cảm','BS CKII Hà Ngọc Hùng','arama dona','tu học phật pháp',1,1,NULL,'Toa thuốc','2022-08-23 09:48:41',1,3),(2,'ăn nhiều','bênh hoang cau','BS CKII Hà Ngọc Hùng','medisin','bệnh uống thuốc',1,1,NULL,'Toa thuốc','2022-08-23 10:11:52',8,11);
/*!40000 ALTER TABLE `histories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoices`
--

DROP TABLE IF EXISTS `invoices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoices` (
  `id` int NOT NULL AUTO_INCREMENT,
  `issued` datetime DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `total_amount` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoices`
--

LOCK TABLES `invoices` WRITE;
/*!40000 ALTER TABLE `invoices` DISABLE KEYS */;
INSERT INTO `invoices` VALUES (1,'2022-08-23 09:48:41','HD: 2022/8-ID: 1','paid',125000),(2,'2022-08-23 10:11:52','HD: 2022/8-ID: 2','paid',500000);
/*!40000 ALTER TABLE `invoices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `messages` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `id_appointment` int DEFAULT NULL,
  `id_author` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsmn5cex2sloybw03f8mro28pc` (`id_appointment`),
  KEY `FK5dcl3cja17jdqq4p66k7o35lh` (`id_author`),
  CONSTRAINT `FK5dcl3cja17jdqq4p66k7o35lh` FOREIGN KEY (`id_author`) REFERENCES `users` (`id`),
  CONSTRAINT `FKsmn5cex2sloybw03f8mro28pc` FOREIGN KEY (`id_appointment`) REFERENCES `appointments` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notifications` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `is_read` bit(1) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `id_user` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhrebgqe9mgp6x2erxipscbxpi` (`id_user`),
  CONSTRAINT `FKhrebgqe9mgp6x2erxipscbxpi` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
INSERT INTO `notifications` VALUES (1,'2022-08-23 09:46:55',_binary '','Bác sĩ: BS CKII Hà Ngọc Hùng có cuộc hẹn khám bệnh với bệnh nhân: Tống Phước Gia Huy vào lúc 2022-08-24T09:00','Cuộc hẹn mới đã lên lịch','/appointments/1',2),(2,'2022-08-23 09:48:41',_binary '\0','Xuất hóa đơn đã được gửi tới bạn','Xuất hóa đơn','/invoices/download/1',3),(3,'2022-08-23 09:54:41',_binary '\0','Bác sĩ: BS CKII Hà Ngọc Hùng có cuộc hẹn khám bệnh với bệnh nhân: Tống Phước Gia Huy vào lúc 2022-08-24T10:00','Cuộc hẹn mới đã lên lịch','/appointments/2',2),(4,'2022-08-23 09:57:46',_binary '\0','Bệnh nhân Tống Phước Gia Huy đã hủy lịch khám vào lúc 2022-08-25T10:00','Lịch khám đã bị hủy','/appointments/4',2),(5,'2022-08-23 10:05:30',_binary '\0','Lịch khám đã kết thúc, bạn có thể từ chối cuộc hẹn nếu nó không diễn ra cho đến khi 2022-08-24T10:00','Lịch khám đã kết thúc','/appointments/3',3),(6,'2022-08-23 10:10:58',_binary '\0','Bác sĩ: BS CKII Hà Ngọc Hùng có cuộc hẹn khám bệnh với bệnh nhân: null vào lúc 2022-08-26T10:00','Cuộc hẹn mới đã lên lịch','/appointments/8',2),(7,'2022-08-23 10:11:52',_binary '\0','Xuất hóa đơn đã được gửi tới bạn','Xuất hóa đơn','/invoices/download/2',11);
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `packages`
--

DROP TABLE IF EXISTS `packages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `packages` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `duration` int DEFAULT NULL,
  `editable` tinyint(1) DEFAULT '0',
  `name` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `packages`
--

LOCK TABLES `packages` WRITE;
/*!40000 ALTER TABLE `packages` DISABLE KEYS */;
INSERT INTO `packages` VALUES (1,'Chăm sóc tùy nhu cầu bệnh nhân',60,1,'Gói cơ bản',100000),(2,'Chăm sóc toàn diện sức khỏe bệnh nhân',60,1,'Gói nâng cao',500000),(3,'Chăm sóc trọn gói trong vòng 6 tháng',60,1,'Gói toàn diện',1000000);
/*!40000 ALTER TABLE `packages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `packages_doctors`
--

DROP TABLE IF EXISTS `packages_doctors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `packages_doctors` (
  `id_packages` int NOT NULL,
  `id_user` int NOT NULL,
  KEY `FKb2x8edpx17jhynr93cjbukdsn` (`id_user`),
  KEY `FKjuaobb6j9an5j7d8mjn8gmjsh` (`id_packages`),
  CONSTRAINT `FKb2x8edpx17jhynr93cjbukdsn` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`),
  CONSTRAINT `FKjuaobb6j9an5j7d8mjn8gmjsh` FOREIGN KEY (`id_packages`) REFERENCES `packages` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `packages_doctors`
--

LOCK TABLES `packages_doctors` WRITE;
/*!40000 ALTER TABLE `packages_doctors` DISABLE KEYS */;
INSERT INTO `packages_doctors` VALUES (1,2),(2,2);
/*!40000 ALTER TABLE `packages_doctors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patients`
--

DROP TABLE IF EXISTS `patients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patients` (
  `address` varchar(255) DEFAULT NULL,
  `id_patient` int NOT NULL,
  `id_declaration` int DEFAULT NULL,
  PRIMARY KEY (`id_patient`),
  KEY `FK7c3fvjecsq8fv4yk8n69ehblf` (`id_declaration`),
  CONSTRAINT `FK7c3fvjecsq8fv4yk8n69ehblf` FOREIGN KEY (`id_declaration`) REFERENCES `declarations` (`id`),
  CONSTRAINT `FKe3hxnol25b1iotegv811dapev` FOREIGN KEY (`id_patient`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patients`
--

LOCK TABLES `patients` WRITE;
/*!40000 ALTER TABLE `patients` DISABLE KEYS */;
INSERT INTO `patients` VALUES ('61 Lê Minh, Huế',3,1),('can tho',11,2);
/*!40000 ALTER TABLE `patients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patients_doctors`
--

DROP TABLE IF EXISTS `patients_doctors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patients_doctors` (
  `id_patient` int NOT NULL,
  `id_doctor` int NOT NULL,
  KEY `FKjc2x3tobuw59xmy4agmheds5u` (`id_doctor`),
  KEY `FK4l182cx5w2cxunamrdbi7813a` (`id_patient`),
  CONSTRAINT `FK4l182cx5w2cxunamrdbi7813a` FOREIGN KEY (`id_patient`) REFERENCES `patients` (`id_patient`),
  CONSTRAINT `FKjc2x3tobuw59xmy4agmheds5u` FOREIGN KEY (`id_doctor`) REFERENCES `doctors` (`id_doctor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patients_doctors`
--

LOCK TABLES `patients_doctors` WRITE;
/*!40000 ALTER TABLE `patients_doctors` DISABLE KEYS */;
/*!40000 ALTER TABLE `patients_doctors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `img` longtext,
  `likes` varchar(255) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `special_id` bigint DEFAULT NULL,
  `total_like` bigint DEFAULT NULL,
  `update_at` datetime DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES (1,NULL,'3','bài đăng ngày 18/7/2022',2,2,'2022-08-23 09:49:13',4);
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fullname` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `problem` varchar(255) DEFAULT NULL,
  `responses` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reviews` (
  `id` int NOT NULL AUTO_INCREMENT,
  `feedback` varchar(100) DEFAULT NULL,
  `rating` int DEFAULT NULL,
  `id_doctor` int DEFAULT NULL,
  `id_patient` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKp5hdw13vls1v7exia4mw6bsqv` (`id_doctor`),
  KEY `FKrti84vci4rmp2j3ykewmlq6bc` (`id_patient`),
  CONSTRAINT `FKp5hdw13vls1v7exia4mw6bsqv` FOREIGN KEY (`id_doctor`) REFERENCES `doctors` (`id_doctor`),
  CONSTRAINT `FKrti84vci4rmp2j3ykewmlq6bc` FOREIGN KEY (`id_patient`) REFERENCES `patients` (`id_patient`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews`
--

LOCK TABLES `reviews` WRITE;
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
INSERT INTO `reviews` VALUES (1,'bác sĩ rất tận tình giúp đỡ cảm ơn nhiều ạ',5,2,11);
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_DOCTOR'),(3,'ROLE_PATIENT');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `specialties`
--

DROP TABLE IF EXISTS `specialties`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `specialties` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `specialties`
--

LOCK TABLES `specialties` WRITE;
/*!40000 ALTER TABLE `specialties` DISABLE KEYS */;
INSERT INTO `specialties` VALUES (1,NULL,'Khoa ngoại thần kinh'),(2,NULL,'Khoa mắt'),(3,NULL,'Khoa nội'),(4,NULL,'Khoa tim'),(5,NULL,'Khoa xương khớp'),(6,NULL,'Khoa tai mũi họng');
/*!40000 ALTER TABLE `specialties` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `active` tinyint(1) DEFAULT '0',
  `age` int DEFAULT NULL,
  `confirmation_token` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT '1',
  `fullname` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `profile_img` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,0,NULL,'f9027735-6fd9-4426-ba57-0a5d6dls7772','admin@gmail.com',1,'ADMIN',NULL,NULL,'$2a$10$Th3jovQba84Dul0dnXFxiOBTBKSx4s26sXi4k9s.NUge62i8rur92','/img/avatar.png','admin'),(2,0,NULL,'f9027735-6fd9-4426-ba57-0a5d64jdsa772','huytvde140135@fpt.edu.vn',1,'BS CKII Hà Ngọc Hùng',NULL,NULL,'$2a$10$Th3jovQba84Dul0dnXFxiOBTBKSx4s26sXi4k9s.NUge62i8rur92','/img/avatar.png','doctor'),(3,0,NULL,'f9027735-6fd9-4426-ba57-0a5d646f7smd2','tpgiahuy5@gmail.com',1,'Tống Phước Gia Huy',NULL,NULL,'$2a$10$Th3jovQba84Dul0dnXFxiOBTBKSx4s26sXi4k9s.NUge62i8rur92','/img/avatar.png','huyhue'),(4,0,NULL,'f9027735-6fd9-4426-ba57-0a5skd46f7772','doctor1@gmail.com',1,'PGS.TS.Phạm Thị Bích',NULL,NULL,'$2a$10$Th3jovQba84Dul0dnXFxiOBTBKSx4s26sXi4k9s.NUge62i8rur92','/img/avatar.png','doctor1'),(5,0,NULL,'f9027735-6fd9-4426-ba57-0a5d646fns','doctor2@gmail.com',1,'PGS.TS.Đinh Ngọc Sơn',NULL,NULL,'$2a$10$Th3jovQba84Dul0dnXFxiOBTBKSx4s26sXi4k9s.NUge62i8rur92','/img/avatar.png','doctor2'),(6,0,NULL,'f9027735-6fd9-4426-ba57-0a5d5s2f7772','doctor3@gmail.com',1,'TS.Nguyễn Hoàng Long',NULL,NULL,'$2a$10$Th3jovQba84Dul0dnXFxiOBTBKSx4s26sXi4k9s.NUge62i8rur92','/img/avatar.png','doctor3'),(7,0,NULL,'f9027735-6fd9-4426-ba57-0a5d646kdo72','doctor4@gmail.com',1,'Ths.BS.Trần Đắc Đại',NULL,NULL,'$2a$10$Th3jovQba84Dul0dnXFxiOBTBKSx4s26sXi4k9s.NUge62i8rur92','/img/avatar.png','doctor4'),(8,0,NULL,'f9027735-6fd9-4426-ba57-0a5d646kdi72','doctor5@gmail.com',1,'BS.Nguyễn Thị Ngọc Lan',NULL,NULL,'$2a$10$Th3jovQba84Dul0dnXFxiOBTBKSx4s26sXi4k9s.NUge62i8rur92','/img/avatar.png','doctor5'),(9,0,NULL,'f9027735-6fd9-4426-ba57-0a5d646f7dj2','doctor6@gmail.com',1,'BS CKII. Nguyễn Trọng Nghĩa',NULL,NULL,'$2a$10$Th3jovQba84Dul0dnXFxiOBTBKSx4s26sXi4k9s.NUge62i8rur92','/img/avatar.png','doctor6'),(10,0,NULL,'f9027735-6fd9-4426-ba57-0a5d646f5272','doctor7@gmail.com',1,'TS.Lê Phong',NULL,NULL,'$2a$10$Th3jovQba84Dul0dnXFxiOBTBKSx4s26sXi4k9s.NUge62i8rur92','/img/avatar.png','doctor7'),(11,0,26,'e1544503-fd77-48da-aa21-3570db202de5','benhnhan@gmail.com',1,'Phan Thị Lan Anh','FEMALE','0355210056','$2a$10$WUM7H5yIKRAsztWK4hH8O.yKfvmg9vNKuvbThDCC5d/Z96s/7H6bS','/img/avatar.png','benhnhan');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_roles` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  KEY `FKj6m8fwv7oqv74fcehir1a9ffy` (`role_id`),
  KEY `FK2o0jvgh89lemvvo17cbqvdxaa` (`user_id`),
  CONSTRAINT `FK2o0jvgh89lemvvo17cbqvdxaa` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKj6m8fwv7oqv74fcehir1a9ffy` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_roles`
--

LOCK TABLES `users_roles` WRITE;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` VALUES (1,1),(2,2),(3,3),(4,2),(5,2),(6,2),(7,2),(8,2),(9,2),(10,2),(11,3);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `working_plans`
--

DROP TABLE IF EXISTS `working_plans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `working_plans` (
  `id_doctor` int NOT NULL,
  `friday` json DEFAULT NULL,
  `monday` json DEFAULT NULL,
  `saturday` json DEFAULT NULL,
  `sunday` json DEFAULT NULL,
  `thursday` json DEFAULT NULL,
  `tuesday` json DEFAULT NULL,
  `wednesday` json DEFAULT NULL,
  PRIMARY KEY (`id_doctor`),
  CONSTRAINT `FKho0o1e5x7u3y4l3yqhu47b9q1` FOREIGN KEY (`id_doctor`) REFERENCES `doctors` (`id_doctor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `working_plans`
--

LOCK TABLES `working_plans` WRITE;
/*!40000 ALTER TABLE `working_plans` DISABLE KEYS */;
INSERT INTO `working_plans` VALUES (2,'{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [10, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [10, 0]}]}'),(4,'{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}'),(5,'{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}'),(6,'{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}'),(7,'{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}'),(8,'{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}'),(9,'{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}'),(10,'{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}','{\"breaks\": [], \"workingHours\": {\"end\": [18, 0], \"start\": [6, 0]}, \"timePeroidsWithBreaksExcluded\": [{\"end\": [18, 0], \"start\": [6, 0]}]}');
/*!40000 ALTER TABLE `working_plans` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-08-23 10:19:03
