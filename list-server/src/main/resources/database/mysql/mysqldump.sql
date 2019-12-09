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
-- Table structure for table `activities`
--

DROP TABLE IF EXISTS `activities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `activities` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Title` varchar(45) NOT NULL,
  `DateUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `UNIQUE_Title` (`Title`)
) ENGINE=InnoDB AUTO_INCREMENT=191 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activities`
--

LOCK TABLES `activities` WRITE;
/*!40000 ALTER TABLE `activities` DISABLE KEYS */;
INSERT INTO `activities` VALUES (1,'Musician','2012-09-23 08:19:10'),(2,'Conductor','2012-09-23 08:19:19'),(3,'Director','2012-09-23 08:19:29'),(4,'Actor','2012-09-23 08:19:35'),(5,'Author','2019-01-02 14:43:25');
/*!40000 ALTER TABLE `activities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `artists`
--

DROP TABLE IF EXISTS `artists`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `artists` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Title` varchar(255) NOT NULL,
  `Description` text,
  `DateUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `UNIQUE_Title` (`Title`)
) ENGINE=InnoDB AUTO_INCREMENT=6365 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artists`
--

LOCK TABLES `artists` WRITE;
/*!40000 ALTER TABLE `artists` DISABLE KEYS */;
INSERT INTO `artists` VALUES (1,'Nick Cave & The Bad Seeds',NULL,'2019-09-30 17:12:11'),(2,'!!!',NULL,'2019-09-30 17:12:23');
/*!40000 ALTER TABLE `artists` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `artistsactivitiesitems`
--

DROP TABLE IF EXISTS `artistsactivitiesitems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `artistsactivitiesitems` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `IdArtist` int(11) NOT NULL,
  `IdItem` int(11) NOT NULL,
  `IdActivity` int(11) NOT NULL,
  `DateUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `UNIQUE_Artist_Activity_Item` (`IdArtist`,`IdItem`,`IdActivity`),
  KEY `FK_ArtistsActivitiesItems_Item` (`IdItem`),
  KEY `FK_ArtistsActivitiesItems_Artist` (`IdArtist`),
  KEY `FK_ArtistsActivitiesItems_Activity` (`IdActivity`),
  CONSTRAINT `FK_ArtistsActivitiesItems_Activity` FOREIGN KEY (`IdActivity`) REFERENCES `activities` (`Id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_ArtistsActivitiesItems_Artist` FOREIGN KEY (`IdArtist`) REFERENCES `artists` (`Id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_ArtistsActivitiesItems_Item` FOREIGN KEY (`IdItem`) REFERENCES `items` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=31030 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artistsactivitiesitems`
--

LOCK TABLES `artistsactivitiesitems` WRITE;
/*!40000 ALTER TABLE `artistsactivitiesitems` DISABLE KEYS */;
INSERT INTO `artistsactivitiesitems` VALUES (12995,1,1,1,'2013-03-24 12:48:51');
/*!40000 ALTER TABLE `artistsactivitiesitems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Title` varchar(45) NOT NULL,
  `Parent` int(11) DEFAULT NULL,
  `DateUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `UNIQUE_Title` (`Title`),
  KEY `FK_Category_Parent` (`Parent`),
  CONSTRAINT `FK_Category_Parent` FOREIGN KEY (`Parent`) REFERENCES `categories` (`Id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=184 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Music',NULL,'2014-03-13 05:26:35'),(2,'Films',NULL,'2014-03-13 05:26:38'),(3,'Popular Music',1,'2014-03-13 07:46:44'),(4,'Classical Music',1,'2014-03-13 07:13:37'),(5,'Greek Music',1,'2012-09-23 05:15:52'),(6,'DVD Films',2,'2012-09-23 05:16:16'),(7,'DivX Films',2,'2012-09-23 05:16:23'),(8,'Books',NULL,'2019-01-02 14:41:55');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Title` varchar(45) NOT NULL,
  `DateUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `UNIQUE_Title` (`Title`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (1,'Documentary','2012-09-23 12:38:35'),(2,'Music / Documentary','2012-09-23 12:38:43'),(3,'Short Film','2012-09-23 12:38:51'),(4,'Collection of Short Films','2012-09-23 12:39:00'),(5,'TV Series','2012-09-23 12:39:12'),(6,'Compilation','2012-09-23 14:39:37'),(7,'Live','2012-09-23 14:39:37'),(8,'Tribute Album','2012-09-23 14:39:37'),(9,'Covers Album','2012-09-23 14:39:37'),(10,'Bootleg','2012-09-23 14:39:37'),(11,'OST','2012-09-23 14:39:37'),(12,'EP','2012-09-23 14:39:37'),(13,'Split EP','2012-09-23 14:39:37'),(14,'Single','2012-09-23 14:39:37'),(15,'Split Single','2012-09-23 14:39:37'),(16,'7\'\'','2012-09-23 14:39:37'),(17,'Split 7\'\'','2012-09-23 14:39:37'),(18,'12\'\'','2012-09-23 14:39:37'),(19,'Split 12\'\'','2012-09-23 14:39:37'),(20,'Promo','2015-11-08 12:06:24');
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `commentsitems`
--

DROP TABLE IF EXISTS `commentsitems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `commentsitems` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `IdItem` int(11) NOT NULL,
  `IdComment` int(11) NOT NULL,
  `DateUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `UNIQUE_Comment_Item` (`IdItem`,`IdComment`),
  KEY `FK_CommentsItems_Comment` (`IdComment`),
  KEY `FK_CommentsItems_Item` (`IdItem`),
  CONSTRAINT `FK_CommentsItems_Comment` FOREIGN KEY (`IdComment`) REFERENCES `comments` (`Id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_CommentsItems_Item` FOREIGN KEY (`IdItem`) REFERENCES `items` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3290 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `commentsitems`
--

LOCK TABLES `commentsitems` WRITE;
/*!40000 ALTER TABLE `commentsitems` DISABLE KEYS */;
/*!40000 ALTER TABLE `commentsitems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fixtures`
--

DROP TABLE IF EXISTS `fixtures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fixtures` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Type` varchar(45) NOT NULL,
  `Fixture` varchar(1000) NOT NULL,
  `DateUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=1940 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fixtures`
--

LOCK TABLES `fixtures` WRITE;
/*!40000 ALTER TABLE `fixtures` DISABLE KEYS */;
/*!40000 ALTER TABLE `fixtures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `items`
--

DROP TABLE IF EXISTS `items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `items` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `TitleEng` varchar(255) NOT NULL,
  `TitleEll` varchar(255) DEFAULT NULL,
  `Category` int(11) NOT NULL,
  `Publisher` int(11) DEFAULT NULL,
  `PhotoPath` varchar(45) DEFAULT NULL,
  `Description` text,
  `Year` int(11) DEFAULT NULL,
  `Rating` int(11) DEFAULT NULL,
  `Subtitles` int(11) DEFAULT NULL,
  `Discs` int(11) DEFAULT NULL,
  `Place` int(11) DEFAULT NULL,
  `Pages` int(11) DEFAULT NULL,
  `DateUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`Id`),
  KEY `FK_Item_Category` (`Category`),
  KEY `FK_Item_Subtitles` (`Subtitles`),
  KEY `FK_Item_Publisher` (`Publisher`),
  CONSTRAINT `FK_Item_Category` FOREIGN KEY (`Category`) REFERENCES `categories` (`Id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_Item_Publisher` FOREIGN KEY (`Publisher`) REFERENCES `publishers` (`Id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_Item_Subtitles` FOREIGN KEY (`Subtitles`) REFERENCES `subtitles` (`Id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7318 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `items`
--

LOCK TABLES `items` WRITE;
/*!40000 ALTER TABLE `items` DISABLE KEYS */;
INSERT INTO `items` VALUES (1,'From Her To Eternity',NULL,3,NULL,'item_03786.jpg',NULL,NULL,5,NULL,1,12,NULL,'2019-09-30 17:11:53');
/*!40000 ALTER TABLE `items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `publishers`
--

DROP TABLE IF EXISTS `publishers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `publishers` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Title` varchar(100) NOT NULL,
  `DateUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `UNIQUE_Title` (`Title`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `publishers`
--

LOCK TABLES `publishers` WRITE;
/*!40000 ALTER TABLE `publishers` DISABLE KEYS */;
INSERT INTO `publishers` VALUES (1,'Ροές','2019-01-03 13:49:53'),(2,'Taschen','2019-01-03 14:04:49'),(3,'Penguin Books','2019-01-03 14:04:57'),(4,'Καστανιώτης','2019-01-03 14:05:06'),(5,'Πατάκης','2019-01-03 22:50:28'),(6,'Πλέθρον','2019-01-03 22:51:03'),(7,'Παρατηρητής','2019-01-03 22:51:37'),(8,'Εστία','2019-01-03 22:52:04'),(9,'Αιγόκερως','2019-01-04 11:32:40'),(10,'Αίολος','2019-01-04 12:19:55'),(11,'Οξύ','2019-01-04 12:20:07'),(12,'Ερωδιός','2019-01-04 12:20:32'),(13,'Βαβέλ / Σέλας','2019-01-04 12:21:00'),(14,'Στάχυ','2019-01-04 12:21:13'),(15,'Π. Τραυλός','2019-01-04 12:21:40'),(16,'Σ.Ι. Ζαχαρόπουλος','2019-01-04 13:10:03'),(17,'Γκοβόστης','2019-01-04 13:10:25'),(18,'Αρμός','2019-01-04 13:10:46'),(19,'Άγρα','2019-01-04 13:10:57'),(20,'Δωδώνη','2019-01-04 13:11:20'),(21,'Κάλβος','2019-01-04 13:11:33'),(22,'Μελάνι','2019-01-04 13:11:41'),(23,'Το Βήμα','2019-01-04 13:12:08'),(24,'Μεταίχμιο','2019-01-04 14:46:00'),(25,'Τυφλόμυγα','2019-01-04 14:46:28'),(26,'Α.Α. Λιβάνη','2019-01-04 14:46:40'),(27,'2.13.61 Publications','2019-01-04 14:47:34'),(28,'Κέδρος','2019-01-04 14:48:13'),(29,'Εξάντας','2019-01-04 14:48:30'),(30,'Κάκτος','2019-01-04 14:48:35'),(31,'Faber and Faber','2019-01-04 14:49:10'),(32,'Δομός','2019-01-04 16:10:18'),(33,'Thames & Hudson','2019-01-04 16:17:13'),(34,'Τετράγωνο','2019-01-04 16:24:53'),(35,'Virgin Publishing','2019-01-04 16:35:26'),(36,'Aurum Press','2019-01-04 16:36:20'),(37,'Die Deutsche Bibliothek','2019-01-04 16:39:47'),(38,'Φεστιβάλ Κινηματογράφου Θεσσαλονίκης','2019-01-04 16:42:14'),(39,'Όλον','2019-01-04 16:43:47'),(40,'Filipacchi Publishing','2019-01-04 16:50:00'),(41,'Γαβριηλίδης','2019-01-04 16:53:46'),(42,'Οδυσσέας Χατζόπουλος','2019-01-04 17:04:46'),(43,'Παπαζήσης','2019-01-04 17:05:03'),(44,'Πέλλα','2019-01-04 17:05:34'),(45,'Γράμματα','2019-01-04 17:05:51'),(46,'Άγκυρα','2019-01-04 17:34:00'),(47,'Νησίδες','2019-01-04 17:34:12'),(48,'Μέδουσα / Σέλας','2019-01-04 17:35:12'),(49,'Οδυσσέας','2019-01-04 17:35:39'),(50,'Απόπειρα','2019-01-04 17:35:53'),(51,'Gutenberg','2019-01-04 17:36:13'),(52,'Αντίποδες','2019-01-04 17:36:36'),(53,'Μίνωας','2019-01-04 17:36:47'),(55,'Ίκαρος','2019-01-04 17:37:21'),(56,'Τόπος','2019-01-07 17:07:29'),(57,'Θεμέλιο','2019-01-07 18:05:57'),(58,'Στοχαστής','2019-01-07 18:08:31'),(59,'Ελεύθερος Τύπος','2019-01-07 20:17:46'),(60,'Νεφέλη','2019-01-07 20:27:01'),(61,'Δαμιανός','2019-01-07 20:35:42'),(62,'Καζαντζάκης','2019-01-07 20:48:45'),(63,'Skira Rizzoli','2019-01-07 20:54:35'),(64,'Ερατώ','2019-01-07 21:05:01'),(65,'Επικαιρότητα','2019-01-07 21:15:44'),(66,'Ηριδανός','2019-01-07 21:25:41'),(67,'Νέος Παλμός','2019-01-07 21:28:36'),(68,'Πόλις','2019-01-07 21:30:23'),(69,'Ερμής','2019-01-07 21:55:50'),(70,'Φωτοχώρος','2019-01-07 21:57:46'),(71,'Επίκεντρο','2019-01-07 22:01:02'),(72,'Γνώση','2019-01-08 10:56:01'),(73,'Ύψιλον / Βιβλία','2019-01-08 10:58:00'),(74,'Ελευθεροτυπία','2019-01-08 11:19:52'),(75,'Ράππα','2019-01-09 16:14:16'),(76,'Επίκουρος','2019-01-09 16:18:26'),(77,'Imago','2019-01-09 17:36:37'),(78,'Αστήρ','2019-01-09 17:37:52'),(79,'Aperture','2019-02-03 11:30:03');
/*!40000 ALTER TABLE `publishers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Title` varchar(15) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `DateUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `UNIQUE_Title` (`Title`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'Administrator','2014-09-14 16:18:40');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subtitles`
--

DROP TABLE IF EXISTS `subtitles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subtitles` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Title` varchar(45) NOT NULL,
  `DateUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `UNIQUE_Title` (`Title`)
) ENGINE=InnoDB AUTO_INCREMENT=313 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subtitles`
--

LOCK TABLES `subtitles` WRITE;
/*!40000 ALTER TABLE `subtitles` DISABLE KEYS */;
INSERT INTO `subtitles` VALUES (1,'No Subtitles','2012-09-23 17:49:30'),(2,'Greek Subtitles','2012-09-23 17:50:03'),(3,'English Subtitles','2012-09-23 17:50:10');
/*!40000 ALTER TABLE `subtitles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(45) NOT NULL,
  `Password` varchar(60) DEFAULT NULL,
  `Email` varchar(45) NOT NULL,
  `Role` int(11) NOT NULL,
  `Enabled` tinyint(4) NOT NULL DEFAULT '0',
  `DateUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `UNIQUE_Username` (`Username`),
  UNIQUE KEY `UNIQUE_Email` (`Email`),
  KEY `FK_User_Role` (`Role`),
  CONSTRAINT `FK_User_Role` FOREIGN KEY (`Role`) REFERENCES `roles` (`Id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=210 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'blixabargeld','$2a$10$/TLdpydrZT5PzDDWzqcv3uFGYHfpFEMAFAR8qKEIWyHaEJzNe.Ad2','nikolaos.i.papadopoulos@gmail.com',1,1,'2014-11-28 21:53:35');
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