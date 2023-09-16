-- MySQL dump 10.13  Distrib 8.1.0, for Win64 (x86_64)
--
-- Host: localhost    Database: topeducation
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
  `id` int NOT NULL,
  `cantidad_anios_egreso_inferior` int NOT NULL,
  `cantidad_anios_egreso_superior` int NOT NULL,
  `porcentaje_descuento` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `descuento_anio_egreso`
--

LOCK TABLES `descuento_anio_egreso` WRITE;
/*!40000 ALTER TABLE `descuento_anio_egreso` DISABLE KEYS */;
INSERT INTO `descuento_anio_egreso` VALUES (0,0,0,15),(1,1,2,8),(2,3,4,4),(3,5,100,0);
/*!40000 ALTER TABLE `descuento_anio_egreso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `descuento_puntaje_prueba`
--

DROP TABLE IF EXISTS `descuento_puntaje_prueba`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `descuento_puntaje_prueba` (
  `id` int NOT NULL,
  `puntaje_inferior` int NOT NULL,
  `puntaje_superior` int NOT NULL,
  `porcentaje_descuento` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `descuento_puntaje_prueba`
--

LOCK TABLES `descuento_puntaje_prueba` WRITE;
/*!40000 ALTER TABLE `descuento_puntaje_prueba` DISABLE KEYS */;
INSERT INTO `descuento_puntaje_prueba` VALUES (0,950,1000,10),(1,900,949,5),(2,850,899,2),(3,0,849,0);
/*!40000 ALTER TABLE `descuento_puntaje_prueba` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `descuento_tipo_colegio`
--

DROP TABLE IF EXISTS `descuento_tipo_colegio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `descuento_tipo_colegio` (
  `id` int NOT NULL,
  `id_tipo_colegio` int NOT NULL,
  `porcentaje_descuento` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `descuento_tipo_colegio_tipo_colegio_id_fk` (`id_tipo_colegio`),
  CONSTRAINT `descuento_tipo_colegio_tipo_colegio_id_fk` FOREIGN KEY (`id_tipo_colegio`) REFERENCES `tipo_colegio` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `descuento_tipo_colegio`
--

LOCK TABLES `descuento_tipo_colegio` WRITE;
/*!40000 ALTER TABLE `descuento_tipo_colegio` DISABLE KEYS */;
INSERT INTO `descuento_tipo_colegio` VALUES (0,0,20),(1,1,10),(2,2,0);
/*!40000 ALTER TABLE `descuento_tipo_colegio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `descuento_tipo_pago_arancel`
--

DROP TABLE IF EXISTS `descuento_tipo_pago_arancel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `descuento_tipo_pago_arancel` (
  `id` int NOT NULL,
  `id_tipo_pago_arancel` int NOT NULL,
  `porcentaje_descuento` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `descuento_tipo_pago_arancel_tipo_pago_arancel_id_fk` (`id_tipo_pago_arancel`),
  CONSTRAINT `descuento_tipo_pago_arancel_tipo_pago_arancel_id_fk` FOREIGN KEY (`id_tipo_pago_arancel`) REFERENCES `tipo_pago_arancel` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `descuento_tipo_pago_arancel`
--

LOCK TABLES `descuento_tipo_pago_arancel` WRITE;
/*!40000 ALTER TABLE `descuento_tipo_pago_arancel` DISABLE KEYS */;
INSERT INTO `descuento_tipo_pago_arancel` VALUES (0,0,0),(1,1,50);
/*!40000 ALTER TABLE `descuento_tipo_pago_arancel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estado_razon`
--

DROP TABLE IF EXISTS `estado_razon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estado_razon` (
  `id` int NOT NULL,
  `estado` varchar(15) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estado_razon`
--

LOCK TABLES `estado_razon` WRITE;
/*!40000 ALTER TABLE `estado_razon` DISABLE KEYS */;
INSERT INTO `estado_razon` VALUES (0,'PAGADA'),(1,'PENDIENTE'),(2,'ATRASADA');
/*!40000 ALTER TABLE `estado_razon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estudiante`
--

DROP TABLE IF EXISTS `estudiante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estudiante` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre1` varchar(255) DEFAULT NULL,
  `nombre2` varchar(255) DEFAULT NULL,
  `apellido1` varchar(255) DEFAULT NULL,
  `apellido2` varchar(255) DEFAULT NULL,
  `rut` varchar(12) NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  `anio_egreso` int DEFAULT NULL,
  `nombre_colegio` varchar(255) DEFAULT NULL,
  `id_tipo_colegio` int NOT NULL,
  `id_tipo_pago_arancel` int NOT NULL,
  `id_interes_meses_atraso` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tipo_colegio_estudiante_idx` (`id_tipo_colegio`),
  KEY `fk_tipo_pago_arancel_estudiante_idx` (`id_tipo_pago_arancel`),
  KEY `fk_interes_meses_atraso_estudiante_idx` (`id_interes_meses_atraso`),
  CONSTRAINT `estudiante_interes_meses_atraso_id_fk` FOREIGN KEY (`id_interes_meses_atraso`) REFERENCES `interes_meses_atraso` (`id`),
  CONSTRAINT `estudiante_tipo_colegio_id_fk` FOREIGN KEY (`id_tipo_colegio`) REFERENCES `tipo_colegio` (`id`),
  CONSTRAINT `estudiante_tipo_pago_arancel_id_fk` FOREIGN KEY (`id_tipo_pago_arancel`) REFERENCES `tipo_pago_arancel` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estudiante`
--

LOCK TABLES `estudiante` WRITE;
/*!40000 ALTER TABLE `estudiante` DISABLE KEYS */;
INSERT INTO `estudiante` VALUES (1,'Hector','Manuel','Gallardo','Araya','20.285.942-9','1999-10-27',2017,'Pedro de Valdivia',2,0,0);
/*!40000 ALTER TABLE `estudiante` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estudiante_descuento`
--

DROP TABLE IF EXISTS `estudiante_descuento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estudiante_descuento` (
  `id` int NOT NULL,
  `id_tipo_descuento` int NOT NULL,
  `id_estudiante` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `estudiante_descuento_estudiante_id_fk` (`id_estudiante`),
  KEY `estudiante_descuento_tipo_descuento_id_fk` (`id_tipo_descuento`),
  CONSTRAINT `estudiante_descuento_tipo_descuento_id_fk` FOREIGN KEY (`id_tipo_descuento`) REFERENCES `tipo_descuento` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estudiante_descuento`
--

LOCK TABLES `estudiante_descuento` WRITE;
/*!40000 ALTER TABLE `estudiante_descuento` DISABLE KEYS */;
/*!40000 ALTER TABLE `estudiante_descuento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `examen`
--

DROP TABLE IF EXISTS `examen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `examen` (
  `id` int NOT NULL,
  `id_estudiante` int NOT NULL,
  `fecha` date NOT NULL,
  `puntaje` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_estudiante_examen_idx` (`id_estudiante`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examen`
--

LOCK TABLES `examen` WRITE;
/*!40000 ALTER TABLE `examen` DISABLE KEYS */;
/*!40000 ALTER TABLE `examen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interes_meses_atraso`
--

DROP TABLE IF EXISTS `interes_meses_atraso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `interes_meses_atraso` (
  `id` int NOT NULL,
  `meses_atraso` int NOT NULL,
  `porcentaje_interes` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interes_meses_atraso`
--

LOCK TABLES `interes_meses_atraso` WRITE;
/*!40000 ALTER TABLE `interes_meses_atraso` DISABLE KEYS */;
INSERT INTO `interes_meses_atraso` VALUES (0,0,0),(1,1,3),(2,2,6),(3,3,9),(4,4,15);
/*!40000 ALTER TABLE `interes_meses_atraso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `max_cuotas_tipo_colegio`
--

DROP TABLE IF EXISTS `max_cuotas_tipo_colegio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `max_cuotas_tipo_colegio` (
  `id` int NOT NULL,
  `id_tipo_colegio` int NOT NULL,
  `max_cuotas` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_tipo_colegio_max_cuotas_tipo_colegio` (`id_tipo_colegio`) /*!80000 INVISIBLE */,
  CONSTRAINT `max_cuotas_tipo_colegio_tipo_colegio_id_fk` FOREIGN KEY (`id_tipo_colegio`) REFERENCES `tipo_colegio` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `max_cuotas_tipo_colegio`
--

LOCK TABLES `max_cuotas_tipo_colegio` WRITE;
/*!40000 ALTER TABLE `max_cuotas_tipo_colegio` DISABLE KEYS */;
INSERT INTO `max_cuotas_tipo_colegio` VALUES (0,0,10),(1,1,7),(2,2,4);
/*!40000 ALTER TABLE `max_cuotas_tipo_colegio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pago`
--

DROP TABLE IF EXISTS `pago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pago` (
  `id` int NOT NULL,
  `id_estudiante` int NOT NULL,
  `total` int NOT NULL,
  `fecha` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `pago_estudiante_id_fk` (`id_estudiante`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pago`
--

LOCK TABLES `pago` WRITE;
/*!40000 ALTER TABLE `pago` DISABLE KEYS */;
/*!40000 ALTER TABLE `pago` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pago_razon`
--

DROP TABLE IF EXISTS `pago_razon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pago_razon` (
  `id` int NOT NULL,
  `id_pago` int NOT NULL,
  `id_razon` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_pago_pago_razon_idx` (`id_pago`),
  CONSTRAINT `pago_razon_pago_id_fk` FOREIGN KEY (`id_pago`) REFERENCES `pago` (`id`),
  CONSTRAINT `pago_razon_pago_id_fk2` FOREIGN KEY (`id_pago`) REFERENCES `pago` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
  `id` int NOT NULL,
  `id_tipo_razon` int NOT NULL,
  `numero_razon` int NOT NULL,
  `monto` int NOT NULL,
  `id_estado_razon` int NOT NULL,
  `fecha_inicio` date NOT NULL,
  `fecha_fin` date NOT NULL,
  `id_estudiante` int NOT NULL,
  `estado` varchar(255) DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tipo_razon_razon_idx` (`id_tipo_razon`),
  KEY `fk_estado_razon_razon_idx` (`id_estado_razon`) /*!80000 INVISIBLE */,
  KEY `fk_estudiante_razon_idx` (`id_estudiante`),
  CONSTRAINT `razon_estado_razon_id_fk` FOREIGN KEY (`id_estado_razon`) REFERENCES `estado_razon` (`id`),
  CONSTRAINT `razon_tipo_razon_id_fk` FOREIGN KEY (`id_tipo_razon`) REFERENCES `tipo_razon` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `razon`
--

LOCK TABLES `razon` WRITE;
/*!40000 ALTER TABLE `razon` DISABLE KEYS */;
/*!40000 ALTER TABLE `razon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_colegio`
--

DROP TABLE IF EXISTS `tipo_colegio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_colegio` (
  `id` int NOT NULL,
  `tipo` varchar(15) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tipo_UNIQUE` (`tipo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_colegio`
--

LOCK TABLES `tipo_colegio` WRITE;
/*!40000 ALTER TABLE `tipo_colegio` DISABLE KEYS */;
INSERT INTO `tipo_colegio` VALUES (0,'MUNICIPAL'),(2,'PRIVADO'),(1,'SUBVENCIONADO');
/*!40000 ALTER TABLE `tipo_colegio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_descuento`
--

DROP TABLE IF EXISTS `tipo_descuento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_descuento` (
  `id` int NOT NULL,
  `tipo` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_descuento`
--

LOCK TABLES `tipo_descuento` WRITE;
/*!40000 ALTER TABLE `tipo_descuento` DISABLE KEYS */;
INSERT INTO `tipo_descuento` VALUES (0,'TIPO_COLEGIO'),(1,'ANIO_EGRESO'),(2,'PUNTAJE_PRUEBAS'),(3,'TIPO_PAGO_ARANCEL');
/*!40000 ALTER TABLE `tipo_descuento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_pago_arancel`
--

DROP TABLE IF EXISTS `tipo_pago_arancel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_pago_arancel` (
  `id` int NOT NULL,
  `tipo` varchar(15) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tipo_UNIQUE` (`tipo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_pago_arancel`
--

LOCK TABLES `tipo_pago_arancel` WRITE;
/*!40000 ALTER TABLE `tipo_pago_arancel` DISABLE KEYS */;
INSERT INTO `tipo_pago_arancel` VALUES (1,'AL_CONTADO'),(0,'CUOTAS');
/*!40000 ALTER TABLE `tipo_pago_arancel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_razon`
--

DROP TABLE IF EXISTS `tipo_razon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_razon` (
  `id` int NOT NULL,
  `tipo` varchar(15) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='		';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_razon`
--

LOCK TABLES `tipo_razon` WRITE;
/*!40000 ALTER TABLE `tipo_razon` DISABLE KEYS */;
INSERT INTO `tipo_razon` VALUES (0,'MATRICULA'),(1,'ARANCEL');
/*!40000 ALTER TABLE `tipo_razon` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-16 19:23:10
