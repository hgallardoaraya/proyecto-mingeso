-- MySQL dump 10.13  Distrib 8.1.0, for Linux (x86_64)
--
-- Host: localhost    Database: topeducation_db_2
-- ------------------------------------------------------
-- Server version	8.1.0

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
-- Table structure for table `descuento_anio_egreso`
--

DROP TABLE IF EXISTS `descuento_anio_egreso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `descuento_anio_egreso` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cantidad_anios_egreso_inferior` int DEFAULT NULL,
  `cantidad_anios_egreso_superior` int DEFAULT NULL,
  `porcentaje_descuento` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `descuento_anio_egreso`
--

LOCK TABLES `descuento_anio_egreso` WRITE;
/*!40000 ALTER TABLE `descuento_anio_egreso` DISABLE KEYS */;
INSERT INTO `descuento_anio_egreso` VALUES (1,0,0,15),(2,1,2,8),(3,3,4,4),(4,5,100,0);
/*!40000 ALTER TABLE `descuento_anio_egreso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `descuento_tipo_colegio`
--

DROP TABLE IF EXISTS `descuento_tipo_colegio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `descuento_tipo_colegio` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_tipo_colegio` int DEFAULT NULL,
  `porcentaje_descuento` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `descuento_tipo_colegio`
--

LOCK TABLES `descuento_tipo_colegio` WRITE;
/*!40000 ALTER TABLE `descuento_tipo_colegio` DISABLE KEYS */;
INSERT INTO `descuento_tipo_colegio` VALUES (1,1,20),(2,2,10),(3,3,0);
/*!40000 ALTER TABLE `descuento_tipo_colegio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `descuento_tipo_pago_arancel`
--

DROP TABLE IF EXISTS `descuento_tipo_pago_arancel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `descuento_tipo_pago_arancel` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_tipo_pago_arancel` int DEFAULT NULL,
  `porcentaje_descuento` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `descuento_tipo_pago_arancel`
--

LOCK TABLES `descuento_tipo_pago_arancel` WRITE;
/*!40000 ALTER TABLE `descuento_tipo_pago_arancel` DISABLE KEYS */;
INSERT INTO `descuento_tipo_pago_arancel` VALUES (1,1,0),(2,2,50);
/*!40000 ALTER TABLE `descuento_tipo_pago_arancel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estado_razon`
--

DROP TABLE IF EXISTS `estado_razon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estado_razon` (
  `id` int NOT NULL AUTO_INCREMENT,
  `estado` varchar(15) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estado_razon`
--

LOCK TABLES `estado_razon` WRITE;
/*!40000 ALTER TABLE `estado_razon` DISABLE KEYS */;
INSERT INTO `estado_razon` VALUES (1,'Pagada'),(2,'Pendiente'),(3,'Atrasada');
/*!40000 ALTER TABLE `estado_razon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `max_cuotas_tipo_colegio`
--

DROP TABLE IF EXISTS `max_cuotas_tipo_colegio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `max_cuotas_tipo_colegio` (
  `id` int NOT NULL AUTO_INCREMENT,
  `max_cuotas` int NOT NULL,
  `id_tipo_colegio` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `max_cuotas_tipo_colegio`
--

LOCK TABLES `max_cuotas_tipo_colegio` WRITE;
/*!40000 ALTER TABLE `max_cuotas_tipo_colegio` DISABLE KEYS */;
INSERT INTO `max_cuotas_tipo_colegio` VALUES (1,10,1),(2,7,2),(3,4,3);
/*!40000 ALTER TABLE `max_cuotas_tipo_colegio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pago`
--

DROP TABLE IF EXISTS `pago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pago` (
  `id` int NOT NULL AUTO_INCREMENT,
  `total` int NOT NULL,
  `fecha` date NOT NULL,
  `id_estudiante` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pago`
--

LOCK TABLES `pago` WRITE;
/*!40000 ALTER TABLE `pago` DISABLE KEYS */;
INSERT INTO `pago` VALUES (3,645000,'2023-11-07',2);
/*!40000 ALTER TABLE `pago` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pago_razon`
--

DROP TABLE IF EXISTS `pago_razon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pago_razon` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_pago` int NOT NULL,
  `id_razon` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `pago_razon_pago_id_fk` (`id_pago`),
  KEY `pago_razon_razon_id_fk` (`id_razon`),
  CONSTRAINT `pago_razon_pago_id_fk` FOREIGN KEY (`id_pago`) REFERENCES `pago` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `pago_razon_razon_id_fk` FOREIGN KEY (`id_razon`) REFERENCES `razon` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pago_razon`
--

LOCK TABLES `pago_razon` WRITE;
/*!40000 ALTER TABLE `pago_razon` DISABLE KEYS */;
/*!40000 ALTER TABLE `pago_razon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `razon`
--

DROP TABLE IF EXISTS `razon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `razon` (
  `id` int NOT NULL AUTO_INCREMENT,
  `numero_razon` int NOT NULL,
  `monto` int NOT NULL,
  `fecha_inicio` date NOT NULL,
  `fecha_fin` date NOT NULL,
  `id_estudiante` int NOT NULL,
  `id_tipo_razon` int DEFAULT NULL,
  `id_estado_razon` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `razon_estado_razon_id_fk` (`id_estado_razon`),
  KEY `razon_tipo_razon_id_fk` (`id_tipo_razon`),
  CONSTRAINT `razon_estado_razon_id_fk` FOREIGN KEY (`id_estado_razon`) REFERENCES `estado_razon` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `razon_tipo_razon_id_fk` FOREIGN KEY (`id_tipo_razon`) REFERENCES `tipo_razon` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `razon`
--

LOCK TABLES `razon` WRITE;
/*!40000 ALTER TABLE `razon` DISABLE KEYS */;
INSERT INTO `razon` VALUES (33,0,70000,'2023-02-24','2023-03-01',22,1,3),(34,1,187500,'2023-04-05','2023-04-10',22,2,3),(35,2,187500,'2023-05-05','2023-05-10',22,2,3),(36,3,187500,'2023-06-05','2023-06-10',22,2,3),(37,4,187500,'2023-07-05','2023-07-10',22,2,3),(38,0,70000,'2023-02-24','2023-03-01',23,1,3),(39,1,250000,'2023-04-05','2023-04-10',23,2,3),(40,2,250000,'2023-05-05','2023-05-10',23,2,3),(41,3,250000,'2023-06-05','2023-06-10',23,2,3),(42,0,70000,'2023-02-24','2023-03-01',24,1,3),(43,1,375000,'2023-04-05','2023-04-10',24,2,3),(44,2,375000,'2023-05-05','2023-05-10',24,2,3),(45,3,375000,'2023-06-05','2023-06-10',24,2,3),(46,4,375000,'2023-07-05','2023-07-10',24,2,3),(47,0,70000,'2023-02-24','2023-03-01',25,1,3),(48,1,375000,'2023-04-05','2023-04-10',25,2,3),(49,2,375000,'2023-05-05','2023-05-10',25,2,3),(50,3,375000,'2023-06-05','2023-06-10',25,2,3),(51,4,375000,'2023-07-05','2023-07-10',25,2,3);
/*!40000 ALTER TABLE `razon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_razon`
--

DROP TABLE IF EXISTS `tipo_razon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_razon` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tipo` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_razon`
--

LOCK TABLES `tipo_razon` WRITE;
/*!40000 ALTER TABLE `tipo_razon` DISABLE KEYS */;
INSERT INTO `tipo_razon` VALUES (1,'Matricula'),(2,'Arancel');
/*!40000 ALTER TABLE `tipo_razon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `total_razon`
--

DROP TABLE IF EXISTS `total_razon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `total_razon` (
  `id` int NOT NULL AUTO_INCREMENT,
  `total` int NOT NULL,
  `id_tipo_razon` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `total_razon_tipo_razon_id_fk` (`id_tipo_razon`),
  CONSTRAINT `total_razon_tipo_razon_id_fk` FOREIGN KEY (`id_tipo_razon`) REFERENCES `tipo_razon` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `total_razon`
--

LOCK TABLES `total_razon` WRITE;
/*!40000 ALTER TABLE `total_razon` DISABLE KEYS */;
INSERT INTO `total_razon` VALUES (1,70000,1),(2,1500000,2);
/*!40000 ALTER TABLE `total_razon` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-12 21:07:04
