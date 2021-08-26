/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80021
Source Host           : localhost:3306
Source Database       : local-t

Target Server Type    : MYSQL
Target Server Version : 80021
File Encoding         : 65001

Date: 2021-08-26 15:05:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userName` varchar(32) NOT NULL,
  `passWord` varchar(50) NOT NULL,
  `realName` varchar(32) DEFAULT NULL,
  `amount` decimal(10,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'chen', '123456', 'chenxy', '800.00');
INSERT INTO `user` VALUES ('3', 'name123', '123', 'real123', '1000.00');
INSERT INTO `user` VALUES ('4', 'name1', '1', 'real1', '1000.00');
INSERT INTO `user` VALUES ('5', 'chen', '222', 'chen', '1000.00');
