-- MySQL dump 10.13  Distrib 8.1.0, for Linux (x86_64)
--
-- Host: localhost    Database: topeducation_db_1
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
-- Table structure for table `estudiante`
--

DROP TABLE IF EXISTS `estudiante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estudiante` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre1` varchar(20) NOT NULL,
  `nombre2` varchar(20) NOT NULL,
  `apellido1` varchar(20) NOT NULL,
  `apellido2` varchar(20) NOT NULL,
  `rut` varchar(20) NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  `anio_egreso` int NOT NULL,
  `nombre_colegio` varchar(20) NOT NULL,
  `cuotas_pactadas` int NOT NULL,
  `id_tipo_colegio` int NOT NULL,
  `id_tipo_pago_arancel` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `estudiante_pk` (`rut`),
  KEY `estudiante_tipo_colegio_id_fk` (`id_tipo_colegio`),
  KEY `estudiante_tipo_pago_arancel_id_fk` (`id_tipo_pago_arancel`),
  CONSTRAINT `estudiante_tipo_colegio_id_fk` FOREIGN KEY (`id_tipo_colegio`) REFERENCES `tipo_colegio` (`id`),
  CONSTRAINT `estudiante_tipo_pago_arancel_id_fk` FOREIGN KEY (`id_tipo_pago_arancel`) REFERENCES `tipo_pago_arancel` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estudiante`
--

LOCK TABLES `estudiante` WRITE;
/*!40000 ALTER TABLE `estudiante` DISABLE KEYS */;
INSERT INTO `estudiante` VALUES (22,'n6','n6','ap6','ap6','rut3','1998-10-28',2015,'colegio 6',4,3,2),(23,'n6','n6','ap6','ap6','rut4','1998-10-28',2015,'colegio 6',3,3,2),(24,'n6','n6','ap6','ap6','rut5','1998-10-28',2015,'colegio 6',4,1,1),(25,'n6','n6','ap6','ap6','rut6','1998-10-28',2015,'colegio 6',4,1,1),(26,'n6','n6','ap6','ap6','rut7','1998-10-28',2015,'colegio 6',0,1,1),(27,'n6','n6','ap6','ap6','rut8','1998-10-28',2015,'colegio 6',0,1,1),(28,'n6','n6','ap6','ap6','rut9','1998-10-28',2015,'colegio 6',0,1,1);
/*!40000 ALTER TABLE `estudiante` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_colegio`
--

DROP TABLE IF EXISTS `tipo_colegio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_colegio` (
  `id` int NOT NULL,
  `tipo` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_colegio`
--

LOCK TABLES `tipo_colegio` WRITE;
/*!40000 ALTER TABLE `tipo_colegio` DISABLE KEYS */;
INSERT INTO `tipo_colegio` VALUES (1,'Municipal'),(2,'Subvencionado'),(3,'Privado');
/*!40000 ALTER TABLE `tipo_colegio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_pago_arancel`
--

DROP TABLE IF EXISTS `tipo_pago_arancel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_pago_arancel` (
  `id` int NOT NULL,
  `tipo` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_pago_arancel`
--

LOCK TABLES `tipo_pago_arancel` WRITE;
/*!40000 ALTER TABLE `tipo_pago_arancel` DISABLE KEYS */;
INSERT INTO `tipo_pago_arancel` VALUES (1,'Al contado'),(2,'Cuotas');
/*!40000 ALTER TABLE `tipo_pago_arancel` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-12 21:06:31
