CREATE DATABASE  IF NOT EXISTS `comp4342` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `comp4342`;
-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: comp4342
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS customer;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE customer (
  id int unsigned NOT NULL AUTO_INCREMENT,
  email varchar(255) NOT NULL,
  first_name varchar(255) NOT NULL,
  last_name varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  gender varchar(1) NOT NULL,
  phone varchar(8) NOT NULL,
  address_1 varchar(100) NOT NULL,
  address_2 varchar(100) NOT NULL,
  address_3 varchar(100) NOT NULL,
  birth_date date NOT NULL,
  salt int NOT NULL,
  PRIMARY KEY (id),
  KEY gender (gender),
  CONSTRAINT customer_ibfk_1 FOREIGN KEY (gender) REFERENCES gender_label (gender)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

INSERT INTO customer VALUES (13,'testac@gmail.com','leung','kyrie','6675cb60a1df4545c2da83e87c385b5b1a4d0df5a3d003e38521aa69abe733a6','M','12312312','fqwfqw','bsdbsd','sgsgr','2021-04-08',589270);
INSERT INTO customer VALUES (14,'newtest@gmail.com','Kyrie','Leung','4cd08ebb796832c4d36d8826160dbf2ee3b83758978faed7c31c3f4ac9fa1654','M','65189185','room 1604','Tin Kun Building','New Territories','2000-04-10',232940);

--
-- Table structure for table `gender_label`
--

DROP TABLE IF EXISTS gender_label;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE gender_label (
  gender varchar(1) NOT NULL,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (gender)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gender_label`
--

INSERT INTO gender_label VALUES ('F','Female');
INSERT INTO gender_label VALUES ('M','Male');

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS transaction;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction` (
  id int unsigned NOT NULL AUTO_INCREMENT,
  customer_id int unsigned NOT NULL,
  amount decimal(10,2) unsigned NOT NULL,
  c_datetime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY customer_id (customer_id),
  CONSTRAINT transaction_ibfk_1 FOREIGN KEY (customer_id) REFERENCES customer (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--


--
-- Table structure for table `transaction_detail`
--

DROP TABLE IF EXISTS transaction_detail;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE transaction_detail (
  transaction_id int unsigned NOT NULL,
  product_id varchar(255) NOT NULL,
  PRIMARY KEY (transaction_id,product_id),
  CONSTRAINT transaction_detail_ibfk_1 FOREIGN KEY (transaction_id) REFERENCES `transaction` (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction_detail`
--


--
-- Table structure for table `transaction_notification`
--

DROP TABLE IF EXISTS transaction_notification;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE transaction_notification (
  id int unsigned NOT NULL AUTO_INCREMENT,
  transaction_id int unsigned NOT NULL,
  remark text NOT NULL,
  c_datetime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY transaction_id (transaction_id),
  CONSTRAINT transaction_notification_ibfk_1 FOREIGN KEY (transaction_id) REFERENCES `transaction` (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction_notification`
--


--
-- Table structure for table `transaction_payment`
--

DROP TABLE IF EXISTS transaction_payment;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE transaction_payment (
  id int unsigned NOT NULL AUTO_INCREMENT,
  transaction_id int unsigned NOT NULL,
  pan varchar(255) NOT NULL,
  card_exp_date varchar(255) NOT NULL,
  amount decimal(10,2) unsigned NOT NULL,
  c_datetime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY transaction_id (transaction_id),
  CONSTRAINT transaction_payment_ibfk_1 FOREIGN KEY (transaction_id) REFERENCES `transaction` (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction_payment`
--


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed