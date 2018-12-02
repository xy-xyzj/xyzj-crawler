/*
Navicat MySQL Data Transfer

Source Server         : 192.168.73.21
Source Server Version : 50719
Source Host           : 192.168.73.21:3306
Source Database       : crawler

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2018-01-29 19:10:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` text COMMENT '类型',
  `name` text COMMENT '名称',
  `webUrl` text COMMENT '来源网站',
  `provide` text COMMENT '提供方',
  `orderNum` text COMMENT '排序列',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=744 DEFAULT CHARSET=utf8;
