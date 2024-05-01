-- MySQL dump 10.13  Distrib 8.3.0, for Win64 (x86_64)
--
-- Host: localhost    Database: eshop_db
-- ------------------------------------------------------
-- Server version	8.3.0

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
-- Table structure for table `app_user`
--

DROP TABLE IF EXISTS `app_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_user` (
  `id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `surname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_user`
--

LOCK TABLES `app_user` WRITE;
/*!40000 ALTER TABLE `app_user` DISABLE KEYS */;
INSERT INTO `app_user` VALUES ('0203a5ad-bf86-486b-843e-146687bf8770','testUser_0129ac5a-d172-4e2c-921c-0283fb85a48e','Test','User','$2a$10$HLW4u3JpwTaowY9Qpfn8HeWytrttsdq5RZiGCUEIKii2mpnuBms2i','USER'),('3625b86e-5171-473b-85a2-1aabf79ce97b','testUser_8fe2d738-7b68-4b1d-beaa-3876af44083f','Test','User','$2a$10$OnrEWUB63WKUgQckhuKXXe5aseMW2Cr/ubFMZnOal5OFi59Siw1im','USER'),('3d013286-981f-4d82-b5c9-2cf89ce6faf3','testUser_b6bcf3a5-a2c5-499a-9abd-be8cb616503e','Test','User','$2a$10$8I1dyKE113lLBkxRdYtYReVxaOpOCD3/7ZodNTNmBkcTW/y6mo1ke','USER'),('3e9b9939-1d57-4b05-816c-2ad8b943ccdf','testUser_8df2b7b5-d745-46fc-8bc7-de96c0912572','Test','User','$2a$10$tuJiB9lPtCvCafN/CbDUUewadvvi4iJm5k8OfDrCnfuVbh4LeVWaS','USER'),('4e645cda-f819-4739-89a2-96897ca0c9b0','testUser_a3222265-dbff-47a5-baa4-796a28c6408e','Test','User','$2a$10$W4Tefmevib.Y89l8mB1ZROxNGWPWJBXuZg0zcOQHhcIqPhBjwI5TC','USER'),('5f699865-83f3-4a24-92ec-712027537f49','testUser_eaef1e64-cade-4c3c-bb46-75d917876291','Test','User','$2a$10$eDL/VIcS9x9Ro.1sAdMuSO3jRLrwUmDXbkqQq454HuE3e83kIr/9q','USER'),('6250dbe5-6411-4eea-a8b9-4d62337f5f27','testUser_23ed3cf1-93d8-4cc2-a367-e20d99186bb0','Test','User','$2a$10$Rr7EKJMj0MjI0WVzksdPAuTSTUiaNQaEWYBlF53jg21as/Z38MS02','USER'),('7f4aeea6-016d-455f-ae64-bfde053a4e57','testUser_b43273e5-41c7-4426-b00c-3431d195bbc8','Test','User','$2a$10$3mP1h9X.fvqwwtT8RrMlmOkkzmwQojrBZ1xId4bXsj8u3dSMeyaVy','USER'),('8c66cf1b-463f-4ac0-a71b-cfa8e4f32ae9','testUser_9c73c37f-915b-4545-8e1e-aa0540070061','Test','User','$2a$10$JR.MVMzda.yKCm27tExIVOSPyOUZoGWZKxnSr6ItFSSNNQQNDk8p.','USER'),('960920a5-4eb8-4a2b-8668-3bcb811aecaf','testUser_3d416491-6b0b-4e70-89fd-ff8e58e2b8ba','Test','User','$2a$10$9xWRkPYvL1pYb122rw6zD.xSWwqiG1yfQfxL3TtSpZ0NT/JD51MA2','USER'),('9c9d8f76-060a-4a91-8357-27a37b9c9c75','admin','Dana','Merth','$2a$10$6HgZEg7llMIe2kckEwN2m.v.Jkak0C/8xA/e1bbhMYAFDhESTXrLW','ADMIN'),('af7558dc-fda2-494e-bab9-b088aedc26ca','testUser_3d6d2f45-48b5-4e43-9d48-89f61ad590c1','Test','User','$2a$10$BETaasO4u2k4oHaJhknUl.a4kWFRlxmX.H6G5eTYQD3mmWwvdRR6W','USER'),('afcb2fb3-b752-478d-9173-3b711d9facb0','testUser_cbe50bd5-3db0-4cd5-8e75-9a77f33e09e4','Test','User','$2a$10$iCnziyOUW4VLKO/JG3DFiesOUWB/QotNxFH3lyyBkNYdF5u296vzm','USER'),('c0583c99-3c82-4903-8192-d32dcfab8542','testUser_45c8a5b0-d1e0-4c72-9181-8c8fd566b518','Test','User','$2a$10$QPCK.0EQNiiafvcbHgDpPun10PBs/lAhI0Dgg7Gs7HMhFYjDg1/PS','USER'),('c1aeca13-9d06-4895-9911-b37ff0d57df6','testUser_dc0c04db-024f-4407-9bf5-9352811ecace','Test','User','$2a$10$2HouRmyZhi84qHdYT1GIIerKURQ.0YikRl2iNlegMReRVJ.5tUOCC','USER'),('d5c73023-984b-4139-a17a-7415b0740504','testUser_0cf7ce21-7a9e-4883-86a1-070f32ac068e','Test','User','$2a$10$pc2T4CPfQJ2aLUCwEux5OOYIyRd19lX9Ef0387IzXHTysXQVaoiN.','USER'),('deb5e576-2e3d-4839-b39a-3bd9666b3337','testUser_de716c8c-007b-41a8-8226-ca120735d54a','Test','User','$2a$10$1XnyNNevd.eNBj5rQhmAK.hwb9iLjoiar02wch6bZOO3TTSS8R1VW','USER'),('f50b2a05-c478-4dae-ab9b-d40bccd9730a','testUser_80d9f164-7231-43e3-a0ca-ab55cf0f5d57','Test','User','$2a$10$sRm0.PC1sE8uj49a0z7HTOwahllYpev033D8FsOSurA7VrB1sPvfe','USER'),('faa71e22-aac4-447e-8178-4068e541c7a1','test','John1','Smith','$2a$10$vijU8dHP6LBxBKLNLcjUFOQoEAt1ySFy6egt4YClg2wqO9H5d7rKG','USER'),('fbbc8579-b1b5-494f-8730-8d6bc6fc5bae','testUser_dd684a2c-fc56-4ff7-8929-6c8815f738ff','Test','User','$2a$10$gOaJGPGpmF1v6sb7XxSUjOWacqsFrITzZ9QDytV/xbciRRXeKNd5S','USER');
/*!40000 ALTER TABLE `app_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_item`
--

DROP TABLE IF EXISTS `cart_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_item` (
  `id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `id_item` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `id_user` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `volume` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `FKnyv9elili7rv0sc82s3e91i0r` (`id_item`),
  KEY `FK7rjm2yx2xivcfhook7wvs885v` (`id_user`),
  CONSTRAINT `FK7rjm2yx2xivcfhook7wvs885v` FOREIGN KEY (`id_user`) REFERENCES `app_user` (`id`),
  CONSTRAINT `FKnyv9elili7rv0sc82s3e91i0r` FOREIGN KEY (`id_item`) REFERENCES `item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_item`
--

LOCK TABLES `cart_item` WRITE;
/*!40000 ALTER TABLE `cart_item` DISABLE KEYS */;
INSERT INTO `cart_item` VALUES ('2c78ec99-76db-4295-aea5-bc03d502ae31','e485735f-c8f1-11ee-b215-309c23b0824d','9c9d8f76-060a-4a91-8357-27a37b9c9c75',1);
/*!40000 ALTER TABLE `cart_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item` (
  `id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `product_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `product_code` int NOT NULL,
  `price` double NOT NULL,
  `volume` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ID_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES ('e485735f-c8f1-11ee-b215-309c23b0824d','Samsung Galaxy S21',3423,699.99,81),('e485772b-c8f1-11ee-b215-309c23b0824d','Google Pixel 6',7887,599.99,0),('e4857815-c8f1-11ee-b215-309c23b0824d','MacBook Air',7614,999.99,0),('e48578f4-c8f1-11ee-b215-309c23b0824d','Sony PlayStation 5',1188,499.99,6),('e48579ed-c8f1-11ee-b215-309c23b0824d','Xbox Series X',5559,499.99,3),('e4857aa9-c8f1-11ee-b215-309c23b0824d','Nintendo Switch',7386,299.99,7),('e4857bf8-c8f1-11ee-b215-309c23b0824d','Dell XPS 13',4984,899.99,3),('e4857d49-c8f1-11ee-b215-309c23b0824d','HP Spectre x360',1185,1099.99,3),('e4857e8c-c8f1-11ee-b215-309c23b0824d','Bose QuietComfort 35 II',7424,299.99,3),('e4857fb6-c8f1-11ee-b215-309c23b0824d','Sony WH-1000XM4',4852,349.99,3),('e48580f0-c8f1-11ee-b215-309c23b0824d','JBL Flip 5',4752,119.99,3),('e485821b-c8f1-11ee-b215-309c23b0824d','Apple Watch Series 7',9834,399.99,1),('e485835b-c8f1-11ee-b215-309c23b0824d','Fitbit Charge 5',3023,179.99,2),('e4858485-c8f1-11ee-b215-309c23b0824d','GoPro HERO10 Black',9241,399.99,5),('e48586cc-c8f1-11ee-b215-309c23b0824d','DJI Mavic Air 2',3976,799.99,4),('e4858808-c8f1-11ee-b215-309c23b0824d','Kindle Paperwhite',8974,129.99,5),('e485892f-c8f1-11ee-b215-309c23b0824d','iPad Pro',4036,799.99,1),('e4858a52-c8f1-11ee-b215-309c23b0824d','Samsung Galaxy Tab S7',3131,649.99,7),('e4858b62-c8f1-11ee-b215-309c23b0824d','Microsoft Surface Pro 7',7991,749.99,3),('e4858ca2-c8f1-11ee-b215-309c23b0824d','Asus ROG Phone 5',4792,699.99,1),('e4858ddf-c8f1-11ee-b215-309c23b0824d','Alienware m15 R4',1663,1999.99,8),('e4858f1c-c8f1-11ee-b215-309c23b0824d','LG OLED55C1',9935,1499.99,7),('e485909a-c8f1-11ee-b215-309c23b0824d','Sonos One',2024,199.99,8),('e48591d9-c8f1-11ee-b215-309c23b0824d','Canon EOS R5',8137,3899.99,6),('e4859320-c8f1-11ee-b215-309c23b0824d','Nikon Z6 II',1509,1999.99,5),('e4859436-c8f1-11ee-b215-309c23b0824d','Garmin Fenix 6',1917,599.99,3),('e4859552-c8f1-11ee-b215-309c23b0824d','Roku Streaming Stick+',7278,49.99,4),('e4859653-c8f1-11ee-b215-309c23b0824d','Philips Hue White and Color Ambiance',7533,99.99,3);
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `id_user` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `date_time` datetime NOT NULL,
  `total_price` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `FKjxax6v0cwq6w6ekpylt0kd0ci` (`id_user`),
  CONSTRAINT `FKjxax6v0cwq6w6ekpylt0kd0ci` FOREIGN KEY (`id_user`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES ('54e34a56-fe09-405b-b39f-e924bed5196e','9c9d8f76-060a-4a91-8357-27a37b9c9c75','2024-04-30 12:59:46',2099.97),('7117b3df-0f36-4dc6-bca3-4f80841a0821','faa71e22-aac4-447e-8178-4068e541c7a1','2024-04-25 11:32:02',1399.98),('954d9224-5296-4d38-846b-2e2e732e1131','faa71e22-aac4-447e-8178-4068e541c7a1','2024-04-25 11:31:45',2099.97),('a23b5fd9-31bc-4a11-9cdc-e8ebc903ffe3','faa71e22-aac4-447e-8178-4068e541c7a1','2024-04-25 11:27:23',5399.93),('d16cd6c5-4d6a-4fd6-9e0a-2f6cf4a3215c','faa71e22-aac4-447e-8178-4068e541c7a1','2024-04-25 13:09:24',1749.97),('df0c7b22-7b3d-4a97-b3ff-712ac3bf6ac1','faa71e22-aac4-447e-8178-4068e541c7a1','2024-04-25 11:30:51',2099.97),('fe8d4bb5-acdc-4371-a312-25b5f5d6ac43','faa71e22-aac4-447e-8178-4068e541c7a1','2024-04-25 11:28:48',2099.97);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item` (
  `id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `id_user` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `id_item` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `id_order` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `volume` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `FK1wa74xlhlae38x57e0hec77ef` (`id_item`),
  KEY `FK91bchbncxidkjypdysx5pvwyb` (`id_order`),
  KEY `FK9gfdm3wvhd3eyvvs1u5vdkau9` (`id_user`),
  CONSTRAINT `FK1wa74xlhlae38x57e0hec77ef` FOREIGN KEY (`id_item`) REFERENCES `item` (`id`),
  CONSTRAINT `FK91bchbncxidkjypdysx5pvwyb` FOREIGN KEY (`id_order`) REFERENCES `order` (`id`),
  CONSTRAINT `FK9gfdm3wvhd3eyvvs1u5vdkau9` FOREIGN KEY (`id_user`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
INSERT INTO `order_item` VALUES ('01e84074-b9ad-4069-b6b0-845d3792a6f7','faa71e22-aac4-447e-8178-4068e541c7a1','e4857815-c8f1-11ee-b215-309c23b0824d','a23b5fd9-31bc-4a11-9cdc-e8ebc903ffe3',1),('11495aef-9158-4440-9780-08462b8d86c8','faa71e22-aac4-447e-8178-4068e541c7a1','e4857fb6-c8f1-11ee-b215-309c23b0824d','d16cd6c5-4d6a-4fd6-9e0a-2f6cf4a3215c',1),('36a1581b-c0e1-47f0-bd9a-6174771f158c','faa71e22-aac4-447e-8178-4068e541c7a1','e485735f-c8f1-11ee-b215-309c23b0824d','a23b5fd9-31bc-4a11-9cdc-e8ebc903ffe3',3),('4473800c-804a-4d99-8532-79854c52f9dd','faa71e22-aac4-447e-8178-4068e541c7a1','e485735f-c8f1-11ee-b215-309c23b0824d','954d9224-5296-4d38-846b-2e2e732e1131',3),('49f821a2-7aae-4b79-b5ba-762837c31beb','faa71e22-aac4-447e-8178-4068e541c7a1','e4857d49-c8f1-11ee-b215-309c23b0824d','a23b5fd9-31bc-4a11-9cdc-e8ebc903ffe3',1),('5be77f82-47c3-444b-a52e-bbfe3e206afb','faa71e22-aac4-447e-8178-4068e541c7a1','e485735f-c8f1-11ee-b215-309c23b0824d','7117b3df-0f36-4dc6-bca3-4f80841a0821',2),('7157c4ef-b7e3-4a93-8e91-302b463f5ba3','faa71e22-aac4-447e-8178-4068e541c7a1','e485772b-c8f1-11ee-b215-309c23b0824d','a23b5fd9-31bc-4a11-9cdc-e8ebc903ffe3',2),('947c941b-4853-4876-ad34-1ddbb5043dd3','faa71e22-aac4-447e-8178-4068e541c7a1','e485735f-c8f1-11ee-b215-309c23b0824d','df0c7b22-7b3d-4a97-b3ff-712ac3bf6ac1',3),('b01e2a83-fc20-492d-9c98-eddffb79de77','9c9d8f76-060a-4a91-8357-27a37b9c9c75','e485735f-c8f1-11ee-b215-309c23b0824d','54e34a56-fe09-405b-b39f-e924bed5196e',3),('b62a62f1-2a1e-4465-8eaa-e8631c8fdc11','faa71e22-aac4-447e-8178-4068e541c7a1','e485735f-c8f1-11ee-b215-309c23b0824d','d16cd6c5-4d6a-4fd6-9e0a-2f6cf4a3215c',2),('f1e8eb87-fd49-413b-be7f-9030159b192a','faa71e22-aac4-447e-8178-4068e541c7a1','e485735f-c8f1-11ee-b215-309c23b0824d','fe8d4bb5-acdc-4371-a312-25b5f5d6ac43',3);
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'eshop_db'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-01 21:22:11
