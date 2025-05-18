-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: holiday_matrix_dbn
-- ------------------------------------------------------
-- Server version	8.0.41

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
-- Table structure for table `annual_matrices`
--

DROP TABLE IF EXISTS `annual_matrices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `annual_matrices` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `dg_validated` bit(1) NOT NULL,
  `dg_validated_at` datetime(6) DEFAULT NULL,
  `dg_validation_status` enum('APPROVED','PENDING','REJECTED') DEFAULT NULL,
  `direct_manager` varchar(255) DEFAULT NULL,
  `employee_count` int NOT NULL,
  `hc` int NOT NULL,
  `hos_validated` bit(1) NOT NULL,
  `hos_validated_at` datetime(6) DEFAULT NULL,
  `hos_validation_status` enum('APPROVED','PENDING','REJECTED') DEFAULT NULL,
  `manager_validation_status` enum('APPROVED','PENDING','REJECTED') DEFAULT NULL,
  `next_level_manager` varchar(255) DEFAULT NULL,
  `non_planned_holiday_criticality` int NOT NULL,
  `organization` varchar(255) DEFAULT NULL,
  `planned_holiday_criticality` int NOT NULL,
  `position` varchar(255) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `year` int NOT NULL,
  `created_by_id` bigint DEFAULT NULL,
  `dg_validated_by_id` bigint DEFAULT NULL,
  `hos_validated_by_id` bigint DEFAULT NULL,
  `manager_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKep1ib10j8omevb1s089od0r76` (`created_by_id`),
  KEY `FKhd382xbmudn1tfrmp0mcfiwhy` (`dg_validated_by_id`),
  KEY `FK6fxv5qv2h41t4i417k163ct69` (`hos_validated_by_id`),
  KEY `FKdq988bsr05hob3h4xfe16vwci` (`manager_id`),
  CONSTRAINT `FK6fxv5qv2h41t4i417k163ct69` FOREIGN KEY (`hos_validated_by_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKdq988bsr05hob3h4xfe16vwci` FOREIGN KEY (`manager_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKep1ib10j8omevb1s089od0r76` FOREIGN KEY (`created_by_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKhd382xbmudn1tfrmp0mcfiwhy` FOREIGN KEY (`dg_validated_by_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `annual_matrices`
--

LOCK TABLES `annual_matrices` WRITE;
/*!40000 ALTER TABLE `annual_matrices` DISABLE KEYS */;
/*!40000 ALTER TABLE `annual_matrices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `holiday_id` bigint DEFAULT NULL,
  `matrix_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8omq0tc18jd43bu5tjh6jvraq` (`user_id`),
  KEY `FK56lxhssnifbh71liomdpvk7n7` (`holiday_id`),
  KEY `FKmidh9jl5mwt7hdw9jtbpe8aoy` (`matrix_id`),
  CONSTRAINT `FK56lxhssnifbh71liomdpvk7n7` FOREIGN KEY (`holiday_id`) REFERENCES `holidays` (`id`),
  CONSTRAINT `FK8omq0tc18jd43bu5tjh6jvraq` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKmidh9jl5mwt7hdw9jtbpe8aoy` FOREIGN KEY (`matrix_id`) REFERENCES `annual_matrices` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employees` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `employee_id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `direct_manager_id` bigint DEFAULT NULL,
  `next_level_manager_id` bigint DEFAULT NULL,
  `position_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKovvvp79dq21byf7svnuekb6iw` (`employee_id`),
  UNIQUE KEY `UKj2dmgsma6pont6kf7nic9elpd` (`user_id`),
  KEY `FKb6wagpd8s9f5lhukycfu96hxh` (`direct_manager_id`),
  KEY `FKco2wq7lfu73h3u5mjhl7th32w` (`next_level_manager_id`),
  KEY `FKngcpgx7fx5kednw3m7u0u8of3` (`position_id`),
  CONSTRAINT `FK69x3vjuy1t5p18a5llb8h2fjx` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKb6wagpd8s9f5lhukycfu96hxh` FOREIGN KEY (`direct_manager_id`) REFERENCES `employees` (`id`),
  CONSTRAINT `FKco2wq7lfu73h3u5mjhl7th32w` FOREIGN KEY (`next_level_manager_id`) REFERENCES `employees` (`id`),
  CONSTRAINT `FKngcpgx7fx5kednw3m7u0u8of3` FOREIGN KEY (`position_id`) REFERENCES `positions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES (6,'rouwa','roua',NULL,NULL,NULL,1);
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `holiday_periods`
--

DROP TABLE IF EXISTS `holiday_periods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `holiday_periods` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `end_date` date NOT NULL,
  `name` varchar(255) NOT NULL,
  `notification_date` datetime(6) DEFAULT NULL,
  `notification_sent` bit(1) DEFAULT NULL,
  `start_date` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `holiday_periods`
--

LOCK TABLES `holiday_periods` WRITE;
/*!40000 ALTER TABLE `holiday_periods` DISABLE KEYS */;
INSERT INTO `holiday_periods` VALUES (1,'2025-05-17','ghghghgh',NULL,_binary '\0','2025-05-15'),(2,'2025-12-31','fermeture estival',NULL,_binary '\0','2025-12-15');
/*!40000 ALTER TABLE `holiday_periods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `holiday_planning`
--

DROP TABLE IF EXISTS `holiday_planning`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `holiday_planning` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `comment` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `dg_validated` bit(1) DEFAULT NULL,
  `dg_validated_at` datetime(6) DEFAULT NULL,
  `hos_validated` bit(1) DEFAULT NULL,
  `hos_validated_at` datetime(6) DEFAULT NULL,
  `manager_validated` bit(1) DEFAULT NULL,
  `manager_validated_at` datetime(6) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `dg_validated_by` bigint DEFAULT NULL,
  `employee_id` bigint DEFAULT NULL,
  `holiday_period_id` bigint DEFAULT NULL,
  `hos_validated_by` bigint DEFAULT NULL,
  `manager_validated_by` bigint DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKovqsf4we9vmnv8ovrvch0jth6` (`created_by`),
  KEY `FKge1rtagw8nqgvufjky1ybwn78` (`dg_validated_by`),
  KEY `FKf0r60ucevxiy7u2t3j1ir45ow` (`employee_id`),
  KEY `FK1btbkltt2uteboy70t8yylhho` (`holiday_period_id`),
  KEY `FKat3yldblx51xc6j1yhinw5wwm` (`hos_validated_by`),
  KEY `FKxj90bv9jrcq7dsltkaei40ot` (`manager_validated_by`),
  KEY `FKm2ka41tf6td1gay9mvgocypol` (`updated_by`),
  CONSTRAINT `FK1btbkltt2uteboy70t8yylhho` FOREIGN KEY (`holiday_period_id`) REFERENCES `holiday_periods` (`id`),
  CONSTRAINT `FKat3yldblx51xc6j1yhinw5wwm` FOREIGN KEY (`hos_validated_by`) REFERENCES `users` (`id`),
  CONSTRAINT `FKf0r60ucevxiy7u2t3j1ir45ow` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`),
  CONSTRAINT `FKge1rtagw8nqgvufjky1ybwn78` FOREIGN KEY (`dg_validated_by`) REFERENCES `users` (`id`),
  CONSTRAINT `FKm2ka41tf6td1gay9mvgocypol` FOREIGN KEY (`updated_by`) REFERENCES `users` (`id`),
  CONSTRAINT `FKovqsf4we9vmnv8ovrvch0jth6` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`),
  CONSTRAINT `FKxj90bv9jrcq7dsltkaei40ot` FOREIGN KEY (`manager_validated_by`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `holiday_planning`
--

LOCK TABLES `holiday_planning` WRITE;
/*!40000 ALTER TABLE `holiday_planning` DISABLE KEYS */;
/*!40000 ALTER TABLE `holiday_planning` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `holidays`
--

DROP TABLE IF EXISTS `holidays`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `holidays` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `criticality_level` int NOT NULL,
  `end_date` date NOT NULL,
  `is_planned` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `start_date` date NOT NULL,
  `status` enum('APPROVED','PENDING','REJECTED') DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4e2a1pjumo9ugc2dhwb4d8ec4` (`user_id`),
  CONSTRAINT `FK4e2a1pjumo9ugc2dhwb4d8ec4` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `holidays`
--

LOCK TABLES `holidays` WRITE;
/*!40000 ALTER TABLE `holidays` DISABLE KEYS */;
/*!40000 ALTER TABLE `holidays` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `matrix_entries`
--

DROP TABLE IF EXISTS `matrix_entries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `matrix_entries` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `headcount` int DEFAULT NULL,
  `non_planned_holiday_critical` bit(1) DEFAULT NULL,
  `non_planned_holiday_low` bit(1) DEFAULT NULL,
  `non_planned_holiday_medium` bit(1) DEFAULT NULL,
  `planned_holiday_critical` bit(1) DEFAULT NULL,
  `planned_holiday_low` bit(1) DEFAULT NULL,
  `planned_holiday_medium` bit(1) DEFAULT NULL,
  `matrix_id` bigint DEFAULT NULL,
  `position_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnmgh9d5og4k87lge2lp3enhx1` (`matrix_id`),
  KEY `FK5ce2aies8gjxg75fbvvnf8jif` (`position_id`),
  CONSTRAINT `FK5ce2aies8gjxg75fbvvnf8jif` FOREIGN KEY (`position_id`) REFERENCES `positions` (`id`),
  CONSTRAINT `FKnmgh9d5og4k87lge2lp3enhx1` FOREIGN KEY (`matrix_id`) REFERENCES `annual_matrices` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `matrix_entries`
--

LOCK TABLES `matrix_entries` WRITE;
/*!40000 ALTER TABLE `matrix_entries` DISABLE KEYS */;
/*!40000 ALTER TABLE `matrix_entries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notifications` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `message` varchar(255) NOT NULL,
  `is_read` bit(1) NOT NULL,
  `type` enum('HOLIDAY_REQUEST','PLANNING_CHANGE','SYSTEM','VALIDATION_REMINDER') DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9y21adhxn0ayjhfocscqox7bh` (`user_id`),
  CONSTRAINT `FK9y21adhxn0ayjhfocscqox7bh` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organizations`
--

DROP TABLE IF EXISTS `organizations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `organizations` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(100) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organizations`
--

LOCK TABLES `organizations` WRITE;
/*!40000 ALTER TABLE `organizations` DISABLE KEYS */;
/*!40000 ALTER TABLE `organizations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `positions`
--

DROP TABLE IF EXISTS `positions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `positions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `critical` bit(1) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `direct_manager_id` bigint DEFAULT NULL,
  `next_level_manager_id` bigint DEFAULT NULL,
  `organization_id` bigint DEFAULT NULL,
  `is_critical` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKk9jqdge85wuer67ymfwgjld5a` (`direct_manager_id`),
  KEY `FKf8nal3ms7yk7k7dx02w55rhgv` (`next_level_manager_id`),
  KEY `FKcayns9gtx5dfrugp6jyw0y8kx` (`organization_id`),
  CONSTRAINT `FKcayns9gtx5dfrugp6jyw0y8kx` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`),
  CONSTRAINT `FKf8nal3ms7yk7k7dx02w55rhgv` FOREIGN KEY (`next_level_manager_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKk9jqdge85wuer67ymfwgjld5a` FOREIGN KEY (`direct_manager_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `positions`
--

LOCK TABLES `positions` WRITE;
/*!40000 ALTER TABLE `positions` DISABLE KEYS */;
/*!40000 ALTER TABLE `positions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `department` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `position` varchar(255) DEFAULT NULL,
  `role` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `manager_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  KEY `FK5p1ci5btqfwvtaqx5n2wxi182` (`manager_id`),
  CONSTRAINT `FK5p1ci5btqfwvtaqx5n2wxi182` FOREIGN KEY (`manager_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,NULL,NULL,NULL,NULL,'$2a$10$/yIky69Cv6UWtETRgKqdVeZjB9w6FbWZBbluPf7HSyHBM91wDj3aK',NULL,'MANAGER','skander',NULL),(2,NULL,NULL,NULL,NULL,'$2a$10$mkBEmSlNIcUSXH2UkYwZiej4FdBOz7lPrt2OBhEdv2niSbdWh2abi',NULL,'EMPLOYEE','rouwa',NULL),(3,'dep1','admin@gmail.com','baya','roua','$2a$10$/yIky69Cv6UWtETRgKqdVeZjB9w6FbWZBbluPf7HSyHBM91wDj3aK','it','ADMIN','roua1',1),(4,'sme','ro2ya@gmail.com','ro2ya','touhami','$2a$10$6ljyi1mpoLINHBhhFL/vSOoPRUiXSeC1udTuQsNMyT8h0jApzlSGW','dev web','HEAD_OF_SERVICE','ro2ya',NULL),(5,'sme','wadiaa@gmail.com','touhami','wadiaa','$2a$10$Fy/vr.cGX6ZC7AI6N0iHC.KpG3UFh/FGqerSY3xqfe8VFffTCA9DO','dev mobile','MANAGER','wadaa',NULL),(6,'sme','bilel@gmail.com','baya','bilel','$2a$10$5gId7ox/g0GDTSw48fnM8O/SfAVPB0N42J2dskUii8BeClDNQ8gDC','dev','DIRECTION_GENERAL','bilel',NULL),(7,'iot','fadi@gmail.com','fadi ','chaabaane','$2a$10$jt.uaZtk7NqjwfoxLnlDsO6TnBIlxAvpgRfEi3q4WJGca0hY7C9Ba','etudiant','HEAD_OF_SERVICE','fadi',NULL),(8,'cs','houssem@gmail.com','houssem','baili','$2a$10$DTKD2/HwBJogvPx5Rx86pu8gqhJndSlGJSVK8Eak5j74sQswVb87G','etudtiant','MANAGER','houssem',NULL),(9,'info','rouatouhami510@gmail.com','Touhami','Rouwa','$2a$10$yWEE/1NTZKb0iWju1j7wMekpEqr1nn0fRXLY/0hNoVgcts8TpUA7y','testeura','MANAGER','rouaa',NULL),(10,'cs','na3ne3@gmail.com','na3bena','lmezyana','$2a$10$o.groxvqA2JlFEYmjQTDU.RJD2m305GCtjvJfcPs6DAV5SUayqGmC','secritaire testeur','EMPLOYEE','na3ne3',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-18  3:14:49
