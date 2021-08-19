-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: i5d101.p.ssafy.io    Database: ssafy_web_db
-- ------------------------------------------------------
-- Server version	8.0.26-0ubuntu0.20.04.2

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
-- Table structure for table `chat`
--

DROP TABLE IF EXISTS `chat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `message` varchar(255) DEFAULT NULL,
  `room_id` varchar(255) DEFAULT NULL,
  `send_time` varchar(255) DEFAULT NULL,
  `writer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4669 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat`
--

LOCK TABLES `chat` WRITE;
/*!40000 ALTER TABLE `chat` DISABLE KEYS */;
INSERT INTO `chat` VALUES (4631,'안녕하세요','bf43983d-ea31-4d55-a902-db2ecec33d73','1629354135062','nahyeon'),(4632,'갤럭시워치4!!','bf43983d-ea31-4d55-a902-db2ecec33d73','1629354139066','ssafy'),(4633,'상품 마음에 드시나요?','bf43983d-ea31-4d55-a902-db2ecec33d73','1629354140704','nahyeon'),(4634,'넹~~~','bf43983d-ea31-4d55-a902-db2ecec33d73','1629354143309','ssafy'),(4635,'그렇다면','bf43983d-ea31-4d55-a902-db2ecec33d73','1629354145489','nahyeon'),(4636,'입금','bf43983d-ea31-4d55-a902-db2ecec33d73','1629354146805','nahyeon'),(4637,'해주세요~','bf43983d-ea31-4d55-a902-db2ecec33d73','1629354149619','nahyeon'),(4638,'입금바로 할께요!!','bf43983d-ea31-4d55-a902-db2ecec33d73','1629354151654','ssafy'),(4639,'계좌 주세요~','bf43983d-ea31-4d55-a902-db2ecec33d73','1629354154618','ssafy'),(4640,'부산은행 111-11111 입니다','bf43983d-ea31-4d55-a902-db2ecec33d73','1629354155362','nahyeon'),(4641,'^ㅁ^','bf43983d-ea31-4d55-a902-db2ecec33d73','1629354158008','nahyeon'),(4642,'넵','bf43983d-ea31-4d55-a902-db2ecec33d73','1629354158155','ssafy'),(4643,'안녕하세요','fe3ad771-3766-46c9-8202-b70e02509ee4','1629354169115','nahyeon'),(4644,'안녕하세요','fe3ad771-3766-46c9-8202-b70e02509ee4','1629354171434','ssafy'),(4645,'캘럭시 폴드 아끼던건데','fe3ad771-3766-46c9-8202-b70e02509ee4','1629354176718','nahyeon'),(4646,'소중하게 서주세요','fe3ad771-3766-46c9-8202-b70e02509ee4','1629354179207','nahyeon'),(4647,'나현님 저랑 또 거래하시네요','fe3ad771-3766-46c9-8202-b70e02509ee4','1629354180734','ssafy'),(4648,'ㅎㅎㅎ','fe3ad771-3766-46c9-8202-b70e02509ee4','1629354181855','ssafy'),(4649,'부산은행 111-1111 입금 부탁드려요','fe3ad771-3766-46c9-8202-b70e02509ee4','1629354188006','nahyeon'),(4650,'입금할께요~','fe3ad771-3766-46c9-8202-b70e02509ee4','1629354191029','ssafy'),(4651,'저 돈이 급해요','fe3ad771-3766-46c9-8202-b70e02509ee4','1629354192706','nahyeon'),(4652,'감사합니다','fe3ad771-3766-46c9-8202-b70e02509ee4','1629354194499','nahyeon'),(4653,'넵ㅋㅋㅋㅋㅋ','fe3ad771-3766-46c9-8202-b70e02509ee4','1629354195823','ssafy'),(4664,'키보드 구매자입니다~','d63d0228-e957-4a97-b8f7-94c7afbd1b50','1629358867732','ssafy'),(4665,'인냥하세요','d63d0228-e957-4a97-b8f7-94c7afbd1b50','1629358870735','nahyeon'),(4666,'~!!','d63d0228-e957-4a97-b8f7-94c7afbd1b50','1629358871872','nahyeon'),(4667,'입금 부탁드려요','d63d0228-e957-4a97-b8f7-94c7afbd1b50','1629358875504','nahyeon'),(4668,'부산 은행 111-1111','d63d0228-e957-4a97-b8f7-94c7afbd1b50','1629358881225','nahyeon');
/*!40000 ALTER TABLE `chat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `base_price` int DEFAULT NULL,
  `categories` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image_url` varchar(500) DEFAULT NULL,
  `is_sold` bit(1) DEFAULT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `regist_time` datetime(6) DEFAULT NULL,
  `reserve_time` datetime(6) DEFAULT NULL,
  `restrict_time` datetime(6) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `user_user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3ash1pklk3e76ihhq9la4fvcs` (`user_user_id`),
  CONSTRAINT `FK3ash1pklk3e76ihhq9la4fvcs` FOREIGN KEY (`user_user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=229 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (199,200000,'furniture','빈티지 의자입니다! 상태 매우 좋아요','blob:https://i5d101.p.ssafy.io:8083/ad2fb4f9-3352-41fb-b646-9f28b0a66abe',_binary '\0','빈티지의자','2021-08-19 19:45:11.000000','2021-08-20 23:30:05.000000',NULL,NULL,'ssafy'),(200,1000000,'digital','아이폰 파라요~!','blob:https://i5d101.p.ssafy.io:8083/ffefe905-4743-4ed9-8a0a-4028b32a37e6',_binary '\0','아이폰 12 ','2021-08-19 19:45:43.000000','2021-08-24 09:00:00.000000',NULL,NULL,'mytest'),(201,850000,'fashion','고야드반지갑입니다. 시그니처 패턴이 들어간 제품입니다.','blob:https://i5d101.p.ssafy.io:8083/22c82f82-2e9e-44d5-bc08-00a3af73c55d',_binary '\0','고야드반지갑','2021-08-19 19:46:29.000000','2021-08-24 05:00:00.000000',NULL,NULL,'ssafy'),(202,450000,'furniture','남색 쇼파입니다~\n아주 멀쩡해요','blob:https://i5d101.p.ssafy.io:8083/8538195a-aa84-40a6-968a-402c4ada54e9',_binary '\0','남색 쇼파','2021-08-19 19:45:13.000000','2021-08-24 18:00:00.000000',NULL,NULL,'nahyeon'),(203,2100000,'furniture','4인 리클라이너 깨끗하고 예쁘고 편해요','blob:https://i5d101.p.ssafy.io:8083/0c06d6c7-2de6-4798-8821-cdd3c9069d06',_binary '\0','리클라이너','2021-08-19 19:47:08.000000','2021-08-24 05:00:00.000000',NULL,NULL,'take'),(204,1000000,'furniture','진정한 휴식을 원한다면','blob:https://i5d101.p.ssafy.io:8083/4f76dd4a-f96f-4ef6-9e88-4a97f0b60a1c',_binary '\0','리클라이너 쇼파','2021-08-19 19:47:11.000000','2021-08-27 09:00:00.000000',NULL,NULL,'mytest'),(205,99000,'digital','인스탁스 미니 싸다 싸요~','blob:https://i5d101.p.ssafy.io:8083/df7dbfab-5982-4a68-bcba-56f7f51549fc',_binary '\0','인스탁스 스퀘어','2021-08-19 19:47:39.000000','2021-08-30 09:00:00.000000',NULL,NULL,'nahyeon'),(206,1500000,'digital','주황, 녹색, 흰색으로 조합된 삼성 비스포크 냉장고에요~ 인테리어 효과도 좋아요~','blob:https://i5d101.p.ssafy.io:8083/c907cf8e-b5a8-49d0-9f81-1c13c0b58d15',_binary '\0','비스포크','2021-08-19 19:47:31.000000','2021-08-21 00:00:00.000000',NULL,NULL,'ssafy'),(207,230000,'art','심슨 좋아하세요? 왜 망설이세요? 구매하세요','blob:https://i5d101.p.ssafy.io:8083/e865a19b-6158-4294-801e-cba59e163e71',_binary '\0','심슨 패러디','2021-08-19 19:48:04.000000','2021-08-21 02:00:00.000000',NULL,NULL,'take'),(208,300000,'fashion','남자라면','blob:https://i5d101.p.ssafy.io:8083/039bf90b-3f7d-4482-8449-9527970a9ef1',_binary '\0',' 몽블랑 지갑','2021-08-19 19:48:25.000000','2021-08-30 09:00:00.000000',NULL,NULL,'mytest'),(209,9990000,'art','산뜻한 그림 원하나요?\n가지세요~','blob:https://i5d101.p.ssafy.io:8083/5fef8d8f-d883-4d8d-ba56-4080bb23d0d8',_binary '','산뜻한 그림','2021-08-19 19:48:32.000000','2021-08-19 17:00:00.000000',NULL,NULL,'nahyeon'),(210,1200000,'furniture','고급스럽게 생긴 책상 거의 새 거','blob:https://i5d101.p.ssafy.io:8083/45724909-686a-4319-892f-59ac29c87a61',_binary '','책상','2021-08-19 19:49:35.000000','2021-08-19 20:25:00.000000',NULL,NULL,'take'),(211,150000,'digital','따끈따끈 신제품 판매합니다 ','blob:https://i5d101.p.ssafy.io:8083/dc2d7f8d-3f76-4b9c-94e1-07faaffaee73',_binary '','갤럭시 워치4','2021-08-19 19:49:59.000000','2021-08-19 20:15:00.000000',NULL,NULL,'nahyeon'),(212,400000,'furniture','장인가구 침대에요~ 메트리스 미포함 가격입니다','blob:https://i5d101.p.ssafy.io:8083/20b21c1e-ed1a-4402-8fb3-9ae85f9d39a0',_binary '\0','침대프레임','2021-08-19 19:51:39.000000','2021-08-21 20:00:00.000000',NULL,NULL,'ssafy'),(213,100000,'art','똭 걸어두면 똭','blob:https://i5d101.p.ssafy.io:8083/fb4c9a21-ea6c-42f7-89ef-f65338248416',_binary '','현대미술작품','2021-08-19 19:49:33.000000','2021-08-19 19:52:18.000000',NULL,NULL,'mytest'),(214,1320000,'furniture','아이들이 좋아하는 침대','blob:https://i5d101.p.ssafy.io:8083/a6e3d510-120a-4878-8ec8-95736cf9cc3a',_binary '','어린이 침대','2021-08-19 19:52:07.000000','2021-08-19 20:40:00.000000',NULL,NULL,'take'),(215,150000,'furniture','깔끔합니다\n집 꾸미세요~!!','blob:https://i5d101.p.ssafy.io:8083/a6ef0454-d19b-4335-b94b-6e1e2eeac9b3',_binary '','테이블의자 세트','2021-08-19 19:51:09.000000','2021-08-19 21:00:00.000000',NULL,NULL,'nahyeon'),(217,1333000,'fashion','구찌 반지 팔아요 실물이 더 예쁩니다','blob:https://i5d101.p.ssafy.io:8083/67a9e6c5-61fc-4dc8-8424-e06b79fb646c',_binary '','구찌 반지','2021-08-19 19:55:05.000000','2021-08-19 20:45:00.000000',NULL,NULL,'take'),(218,33500000,'fashion','비싼 가방 급하게 팔아요\n','blob:https://i5d101.p.ssafy.io:8083/e6c5635a-7ae1-48c3-9ef7-1ecbca0544b6',_binary '\0','멋진 가방','2021-08-19 19:55:46.000000','2021-08-23 09:00:00.000000',NULL,NULL,'nahyeon'),(219,95000000,'art','신윤복 상춘야흥 진품입니다 확인해 보세요','blob:https://i5d101.p.ssafy.io:8083/579fb418-55cb-4ff2-8254-dc42ba91878d',_binary '\0','상춘야흥','2021-08-19 19:56:48.000000','2021-08-20 01:46:00.000000',NULL,NULL,'take'),(220,8800000,'digital','갤럭시z폴드 팔아요 팔아','blob:https://i5d101.p.ssafy.io:8083/b0464f25-ab59-4a07-948a-f3a7eaf97d12',_binary '','갤럭시 z폴드3','2021-08-19 19:57:09.000000','2021-08-19 20:10:00.000000',NULL,NULL,'nahyeon'),(227,55000,'digital','체리 적축입니다~!!','blob:https://i5d101.p.ssafy.io:8083/72ff7d3b-5f2e-4ef8-b918-fdab32be440c',_binary '','키보드','2021-08-20 01:25:03.000000','2021-08-20 01:50:00.000000',NULL,NULL,'nahyeon');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trade_history`
--

DROP TABLE IF EXISTS `trade_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trade_history` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `buyer` varchar(255) DEFAULT NULL,
  `price` int DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  `room_id` varchar(255) DEFAULT NULL,
  `seller` varchar(255) DEFAULT NULL,
  `trade_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trade_history`
--

LOCK TABLES `trade_history` WRITE;
/*!40000 ALTER TABLE `trade_history` DISABLE KEYS */;
INSERT INTO `trade_history` VALUES (27,'ssafy',8830000,220,'fe3ad771-3766-46c9-8202-b70e02509ee4','nahyeon','2021-08-19 11:03:29.481000'),(28,'ssafy',250000,211,'bf43983d-ea31-4d55-a902-db2ecec33d73','nahyeon','2021-08-19 11:07:53.159000'),(30,NULL,1200000,210,'5540a183-3a25-47a9-8ed0-fe16fcfb9c32','take','2021-08-19 11:23:17.302000'),(33,'nahyeon',1570000,214,'68c92c80-95ce-46aa-a226-9bc49135b73c','take','2021-08-19 11:37:58.766000'),(34,'nahyeon',1453000,217,'218ccf7e-2696-4254-b2f4-0deb7b992214','take','2021-08-19 11:41:58.872000'),(38,'ssafy',70000,227,'d63d0228-e957-4a97-b8f7-94c7afbd1b50','nahyeon','2021-08-19 16:40:57.468000');
/*!40000 ALTER TABLE `trade_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trade_section`
--

DROP TABLE IF EXISTS `trade_section`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trade_section` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `end_time` date DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `max_price` int DEFAULT NULL,
  `price_gap` int DEFAULT NULL,
  `product_id` varchar(255) DEFAULT NULL,
  `start_time` date DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `user_amount` int DEFAULT NULL,
  `user_amount_limit` int DEFAULT NULL,
  `seller` varchar(255) DEFAULT NULL,
  `is_started` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trade_section`
--

LOCK TABLES `trade_section` WRITE;
/*!40000 ALTER TABLE `trade_section` DISABLE KEYS */;
INSERT INTO `trade_section` VALUES (114,NULL,_binary '',8830000,10000,'220',NULL,NULL,'RoomCreatedBynahyeon220',NULL,NULL,'nahyeon',_binary ''),(115,NULL,_binary '',250000,10000,'211',NULL,NULL,'RoomCreatedBynahyeon211',NULL,NULL,'nahyeon',_binary ''),(117,NULL,_binary '',1200000,3000,'210',NULL,NULL,'RoomCreatedBytake210',NULL,NULL,'take',_binary ''),(120,NULL,_binary '',1570000,50000,'214',NULL,NULL,'RoomCreatedBytake214',NULL,NULL,'take',_binary ''),(121,NULL,_binary '',1453000,30000,'217',NULL,NULL,'RoomCreatedBytake217',NULL,NULL,'take',_binary ''),(129,NULL,_binary '',70000,5000,'227',NULL,NULL,'RoomCreatedBynahyeon227',NULL,NULL,'nahyeon',_binary '');
/*!40000 ALTER TABLE `trade_section` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `id` bigint DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `department` varchar(255) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('juns5779','부산','juns5779@naver.com',NULL,'최준성','$2a$10$qej3jbuPRpbO52z1xEpR8uFx1ej/3/x4p6/RArXA1HC001Edf/aY.',NULL,NULL),('mytest','노바빌 303호','lsyeup1206@naver.com',NULL,'이승엽','$2a$10$9yyURTFAOeiljUEx1VY4n.Xk26zeyiDav3qANojxGGooE4VZaTMxi',NULL,NULL),('nahyeon','부산시 사하구','nhkiiim@naver.com',NULL,'김나현','$2a$10$paQsenVMGHJ.lVgJCcrUU.PlCrUtmnfQtJpqvqI5bsWyft1Hal1q.',NULL,NULL),('ssafy','구미시','ssafy123@samsung.com',NULL,'김싸피','$2a$10$Sf3ysL3DsSI.1j8X6DJBE.UZVCMVyGC/ufwE8MQVNV/p3Vt.0BGX2',NULL,NULL),('take','','',NULL,'떼껄룩','$2a$10$PpBRp.7EXA.HSnr8Icyu9uROpGDCDwlDsZHMvu0YrrAov8/JzqfB2',NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wish_product`
--

DROP TABLE IF EXISTS `wish_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wish_product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint DEFAULT NULL,
  `user_user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKntkt4okr23lc23rp1sxs8x79q` (`user_user_id`),
  CONSTRAINT `FKntkt4okr23lc23rp1sxs8x79q` FOREIGN KEY (`user_user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=172 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wish_product`
--

LOCK TABLES `wish_product` WRITE;
/*!40000 ALTER TABLE `wish_product` DISABLE KEYS */;
INSERT INTO `wish_product` VALUES (116,203,'ssafy'),(117,204,'ssafy'),(119,211,'ssafy'),(121,215,'take'),(122,211,'take'),(123,212,'take'),(124,218,'take'),(129,220,'ssafy'),(131,214,'ssafy'),(134,210,'ssafy'),(135,215,'ssafy'),(140,214,'nahyeon'),(142,217,'nahyeon'),(143,203,'nahyeon'),(144,206,'nahyeon'),(149,207,'nahyeon'),(150,204,'nahyeon'),(151,201,'nahyeon'),(152,199,'nahyeon'),(153,212,'nahyeon'),(154,218,'ssafy'),(157,208,'take'),(160,219,'ssafy'),(161,227,'juns5779'),(164,227,'ssafy'),(169,207,'ssafy'),(170,205,'ssafy'),(171,207,'juns5779');
/*!40000 ALTER TABLE `wish_product` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-08-19 16:41:23
