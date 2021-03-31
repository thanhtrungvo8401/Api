CREATE DATABASE `Japanese_DB` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `auth` (
  `id` binary(16) NOT NULL,
  `createdDate` datetime NOT NULL,
  `isActive` bit(1) NOT NULL,
  `updatedDate` datetime NOT NULL,
  `menuId` binary(16) NOT NULL,
  `roleId` binary(16) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `center` (
  `id` binary(16) NOT NULL,
  `centerName` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `createdDate` datetime NOT NULL,
  `isActive` bit(1) NOT NULL,
  `updatedDate` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `centerName_UNIQUE` (`centerName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `menu` (
  `id` binary(16) NOT NULL,
  `createdDate` datetime NOT NULL,
  `isActive` bit(1) NOT NULL,
  `name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `regex` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `updatedDate` datetime NOT NULL,
  `url` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `parentId` binary(16) DEFAULT NULL,
  `method` varchar(8) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `role` (
  `id` binary(16) NOT NULL,
  `createdDate` datetime NOT NULL,
  `description` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `isActive` bit(1) NOT NULL,
  `roleName` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `updatedDate` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `roleName_UNIQUE` (`roleName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--CREATE TABLE `room` (
--  `id` binary(16) NOT NULL,
--  `createdDate` datetime NOT NULL,
--  `isActive` bit(1) NOT NULL,
--  `roomName` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
--  `updatedDate` datetime NOT NULL,
--  `centerId` binary(16) DEFAULT NULL,
--  PRIMARY KEY (`id`)
--) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
--
--CREATE TABLE `room_members` (
--  `roomId` binary(16) NOT NULL,
--  `memberId` binary(16) NOT NULL
--) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
--
--CREATE TABLE `room_setVocas` (
--  `roomId` binary(16) NOT NULL,
--  `setVocaId` binary(16) NOT NULL
--) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--CREATE TABLE `room_teachers` (
--  `roomId` binary(16) NOT NULL,
--  `teacherId` binary(16) NOT NULL
--) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `set_voca` (
  `id` binary(16) NOT NULL,
  `maxVoca` tinyint NOT NULL,
  `createdDate` datetime NOT NULL,
  `isActive` bit(1) NOT NULL,
  `setName` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `updatedDate` datetime NOT NULL,
  `authorId` binary(16) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE `user` (
  `id` binary(16) NOT NULL,
  `accessToken` char(11) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `createdDate` datetime NOT NULL,
  `email` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `isActive` bit(1) NOT NULL,
  `password` binary(60) NOT NULL,
  `updatedDate` datetime NOT NULL,
  `roleId` binary(16) NOT NULL,
  `centerId` binary(16) DEFAULT NOT NULL,
  `maxSetVocas` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE `voca` (
  `id` binary(16) NOT NULL,
  `createdDate` datetime NOT NULL,
  `isActive` bit(1) NOT NULL,
  `kanji` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `meaning` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `sentence` varchar(150) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `updatedDate` datetime NOT NULL,
  `voca` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `setId` binary(16) NOT NULL,
  `code` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `remember_group` (
  `id` binary(16) NOT NULL,
  `createdDate` datetime NOT NULL,
  `isActive` bit(1) NOT NULL,
  `updatedDate` datetime NOT NULL,
  `vocaCodes` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `activeCodes` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `ownerId` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_remember_group__owner` (`ownerId`),
  CONSTRAINT `FK_remember_group__owner` FOREIGN KEY (`ownerId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `test_group` (
  `id` binary(16) NOT NULL,
  `createdDate` datetime NOT NULL,
  `isActive` bit(1) NOT NULL,
  `updatedDate` datetime NOT NULL,
  `vocaCodes` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `corrects` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
   `name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `ownerId` binary(16) NOT NULL,
  KEY `FK_test_group__owner` (`ownerId`),
  CONSTRAINT `FK_test_group__owner` FOREIGN KEY (`ownerId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

ALTER TABLE `auth`
  ADD KEY `FK3qw51q11lsis16ne16jbfmhh4` (`menuId`),
  ADD KEY `FKmj6lyn5c4yowu3jxgq4pa8cgg` (`roleId`),
  ADD CONSTRAINT `FK3qw51q11lsis16ne16jbfmhh4` FOREIGN KEY (`menuId`) REFERENCES `menu` (`id`),
  ADD CONSTRAINT `FKmj6lyn5c4yowu3jxgq4pa8cgg` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`);
ALTER TABLE `menu`
  ADD UNIQUE KEY `name_UNIQUE` (`name`),
  ADD KEY `FKbfc1llceenf99rg3fybss8u77` (`parentId`),
  ADD CONSTRAINT `FKbfc1llceenf99rg3fybss8u77` FOREIGN KEY (`parentId`) REFERENCES `menu` (`id`);
--ALTER TABLE `room`
--  ADD KEY `FK7p81crppa1ner7rsq445ji8kd` (`centerId`),
--  ADD CONSTRAINT `FK7p81crppa1ner7rsq445ji8kd` FOREIGN KEY (`centerId`) REFERENCES `center` (`id`);
--ALTER TABLE `room_members`
--  ADD KEY `FK6bf0bl04tpeq5i97b9w7l57kb` (`memberId`),
--  ADD KEY `FKsdu8981apo9pn8ttlpdnwvulo` (`roomId`),
--  ADD CONSTRAINT `FK6bf0bl04tpeq5i97b9w7l57kb` FOREIGN KEY (`memberId`) REFERENCES `user` (`id`),
--  ADD CONSTRAINT `FKsdu8981apo9pn8ttlpdnwvulo` FOREIGN KEY (`roomId`) REFERENCES `room` (`id`);
--ALTER TABLE `room_setVocas`
--  ADD KEY `FK37ksjwekep66xs34d4r164cq2` (`roomId`),
--  ADD KEY `FK67f17pord2gogx0tjfvnc5xfs` (`setVocaId`),
--  ADD CONSTRAINT `FK37ksjwekep66xs34d4r164cq2` FOREIGN KEY (`roomId`) REFERENCES `room` (`id`),
--  ADD CONSTRAINT `FK67f17pord2gogx0tjfvnc5xfs` FOREIGN KEY (`setVocaId`) REFERENCES `set_voca` (`id`);
--ALTER TABLE `room_teachers`
--  ADD KEY `FK6jfcv2sbvc68quwyixfhqh5ui` (`teacherId`),
--  ADD KEY `FKj09vp3p966n900awuoapwpbwg` (`roomId`),
--  ADD CONSTRAINT `FK6jfcv2sbvc68quwyixfhqh5ui` FOREIGN KEY (`teacherId`) REFERENCES `user` (`id`),
--  ADD CONSTRAINT `FKj09vp3p966n900awuoapwpbwg` FOREIGN KEY (`roomId`) REFERENCES `room` (`id`);
ALTER TABLE `set_voca`
  ADD KEY `FK10scacxjgygoob9mmktt3ab8c` (`authorId`),
  ADD CONSTRAINT `FK10scacxjgygoob9mmktt3ab8c` FOREIGN KEY (`authorId`) REFERENCES `user` (`id`);
ALTER TABLE `user`
  ADD KEY `FK8yhl7wdo39n3ee04f8rpajces` (`roleId`),
  ADD KEY `FKpgeenperawt68vqx3g4kgb798` (`centerId`),
  ADD CONSTRAINT `FK8yhl7wdo39n3ee04f8rpajces` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`),
  ADD CONSTRAINT `FKpgeenperawt68vqx3g4kgb798` FOREIGN KEY (`centerId`) REFERENCES `center` (`id`);  
ALTER TABLE `voca`
  ADD KEY `FKlbh3k9qrxbjb119nhgy38705g` (`setId`),
  ADD CONSTRAINT `FKlbh3k9qrxbjb119nhgy38705g` FOREIGN KEY (`setId`) REFERENCES `set_voca` (`id`);


