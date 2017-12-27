/*
Navicat MySQL Data Transfer

Source Server         : 本机mysql
Source Server Version : 50626
Source Host           : localhost:3306
Source Database       : medicine

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2017-08-01 10:36:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for medicinekad
-- ----------------------------
DROP TABLE IF EXISTS `medicinekad`;
CREATE TABLE `medicinekad` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键 自增',
  `website` varchar(255) DEFAULT NULL COMMENT '来源站点',
  `products` longtext COMMENT '商品介绍',
  `instructions` longtext COMMENT '说明书',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8178 DEFAULT CHARSET=utf8;
