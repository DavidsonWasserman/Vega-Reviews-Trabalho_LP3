-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: banco_projeto_lp3
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `jogos`
--

DROP TABLE IF EXISTS `jogos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jogos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `genero` varchar(50) DEFAULT NULL,
  `desenvolvedora` varchar(100) DEFAULT NULL,
  `sinopse` text,
  `imagem` longtext,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nome` (`nome`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jogos`
--

LOCK TABLES `jogos` WRITE;
/*!40000 ALTER TABLE `jogos` DISABLE KEYS */;
INSERT INTO `jogos` VALUES (1,'Metal Gear Solid 3: Snake Eater','Espionagem Tática','Konami','Terceiro jogo da franquia Metal Gear','MGS3_Cover.jpg'),(7,'Death Stranding 2','Ação','Kojima Productions','Entregar encomendas em um mundo devastado','DS2_Cover.jpg'),(9,'Helldivers 2','Ação','Arrowhead','Espalhe a democracia mais uma vez','HD2_Cover.jpg'),(44,'Hitman: World of Assassination','Estrategia/Furtividade','IO Interactive','Coletanea da trilogia hitman WoA','HWoA_Cover.jpg'),(45,'Marvel Rivals','Hero Shooter','NetEase','Hero Shooter com herois da Marvel Comics','MR_Cover.jpg'),(46,'Jedi Survivor','Soulslite','Respawn','A segunda aventura de Cal Kestis','JS_Cover.jpeg');
/*!40000 ALTER TABLE `jogos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviews_jogos`
--

DROP TABLE IF EXISTS `reviews_jogos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reviews_jogos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_usuario` int NOT NULL,
  `id_jogo` int NOT NULL,
  `comentario` text,
  `nota` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_usuario` (`id_usuario`),
  KEY `id_jogo` (`id_jogo`),
  CONSTRAINT `reviews_jogos_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`),
  CONSTRAINT `reviews_jogos_ibfk_2` FOREIGN KEY (`id_jogo`) REFERENCES `jogos` (`id`),
  CONSTRAINT `reviews_jogos_chk_1` CHECK ((`nota` between 0 and 10))
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews_jogos`
--

LOCK TABLES `reviews_jogos` WRITE;
/*!40000 ALTER TABLE `reviews_jogos` DISABLE KEYS */;
INSERT INTO `reviews_jogos` VALUES (1,1,1,'bom jogo',10),(2,7,7,'um dos melhores de 2025',9),(4,1,7,'esperava mais deste jogo mas ainda assim gostei',7),(5,1,44,'Trilogia defnitiva Hitman',10),(6,1,45,'Overwatch com personagens da marvel',7),(7,8,46,'mal otimizado',3),(8,2,7,'legal',8),(9,4,1,'apice do stealth',10),(10,1,9,'Viva a democracia gerenciada!',9);
/*!40000 ALTER TABLE `reviews_jogos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nickname` varchar(100) NOT NULL,
  `senha` varchar(100) NOT NULL,
  `sobre_mim` text,
  `foto_perfil` longtext,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nickname` (`nickname`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Vega','senha','jogador de jogos','Vega_PFP.jpg'),(2,'usuario1','senha1','eu sou o 1','Vega_PFP.jpg'),(3,'usuario2','senha2','eu sou o 2','Vega_PFP.jpg'),(4,'usuario3','senha3','eu sou o 3','Vega_PFP.jpg'),(5,'usuario4','senha4','eu sou o 4','Vega_PFP.jpg'),(6,'usuario5','senha5','eu sou o 5','Vega_PFP.jpg'),(7,'usuario6','senha6','eu sou o 6','Vega_PFP.jpg'),(8,'usuario7','senha7','eu sou o 7','Vega_PFP.jpg'),(10,'teste','teste','teste','cartaz.png'),(11,'Jogador','senha','Gosto de jogos','Vega_PFP.jpg'),(12,'J0g4d0r','senha','101','Vega_PFP.jpg');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-21 17:59:29
