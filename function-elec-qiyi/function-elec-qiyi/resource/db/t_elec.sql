/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50045
Source Host           : localhost:3306
Source Database       : nbc_lib_shijia

Target Server Type    : MYSQL
Target Server Version : 50045
File Encoding         : 65001

Date: 2013-05-13 12:20:11
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `t_elec_classhour`
-- ----------------------------
DROP TABLE IF EXISTS `t_elec_classhour`;
CREATE TABLE `t_elec_classhour` (
  `id` varchar(32) NOT NULL,
  `weekIndex` int(11) default NULL,
  `week_startTime` time default NULL,
  `week_endTime` time default NULL,
  `course_id` varchar(32) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK8E89BE4D14F1902A` (`course_id`),
  CONSTRAINT `FK8E89BE4D14F1902A` FOREIGN KEY (`course_id`) REFERENCES `t_elec_course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `t_elec_course`
-- ----------------------------
DROP TABLE IF EXISTS `t_elec_course`;
CREATE TABLE `t_elec_course` (
  `id` varchar(32) NOT NULL,
  `term_id` varchar(32) default NULL,
  `type_id` varchar(32) default NULL,
  `gradeIds` varchar(255) default NULL,
  `gradeNames` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  `maxStudentNum` int(11) default NULL,
  `teacherIds` text,
  `teacherNames` text,
  `sign_startDate` datetime default NULL,
  `sign_endDate` datetime default NULL,
  `class_startDate` date default NULL,
  `class_endDate` date default NULL,
  `classhourNum` int(11) default NULL,
  `course_comment` varchar(255) default NULL,
  `club_state` int(11) default NULL,
  `courseContent` text,
  `courseRequire` text,
  `courseNote` text,
  `courseAttr` int(11) default NULL,
  `operatorDate` datetime default NULL,
  `classhourRequire` int(11) default NULL,
  `end_place` varchar(32) default NULL,
  `start_palce` varchar(32) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FKB74892CA51B22B5A` (`end_place`),
  KEY `FKB74892CA82878ED7` (`start_palce`),
  CONSTRAINT `FKB74892CA51B22B5A` FOREIGN KEY (`end_place`) REFERENCES `t_elec_place` (`id`),
  CONSTRAINT `FKB74892CA82878ED7` FOREIGN KEY (`start_palce`) REFERENCES `t_elec_place` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `t_elec_place`
-- ----------------------------
DROP TABLE IF EXISTS `t_elec_place`;
CREATE TABLE `t_elec_place` (
  `id` varchar(32) NOT NULL,
  `name` varchar(50) NOT NULL,
  `typename` varchar(255) NOT NULL,
  `delstate` int(11) NOT NULL,
  `createtime` datetime NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `t_elec_sign`
-- ----------------------------
DROP TABLE IF EXISTS `t_elec_sign`;
CREATE TABLE `t_elec_sign` (
  `id` varchar(32) NOT NULL,
  `course_id` varchar(32) NOT NULL,
  `classhour_id` varchar(9999) default NULL,
  `stu_id` varchar(32) NOT NULL,
  `create_date` datetime default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `t_elec_term`
-- ----------------------------
DROP TABLE IF EXISTS `t_elec_term`;
CREATE TABLE `t_elec_term` (
  `id` varchar(32) NOT NULL,
  `name` varchar(50) default NULL,
  `open_date_start` datetime default NULL,
  `open_date_end` datetime default NULL,
  `sign_date_start` datetime default NULL,
  `sign_date_end` datetime default NULL,
  `lession_date_start` datetime default NULL,
  `lesson_date_end` datetime default NULL,
  `class_time_start` time default NULL,
  `class_time_end` time default NULL,
  `comments` varchar(255) default NULL,
  `del_state` int(11) NOT NULL,
  `current_term` bit(1) NOT NULL,
  `max_course` int(11) NOT NULL,
  `create_date` datetime NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `t_elec_userprivilege`
-- ----------------------------
DROP TABLE IF EXISTS `t_elec_userprivilege`;
CREATE TABLE `t_elec_userprivilege` (
  `id` varchar(32) NOT NULL,
  `uid` varchar(32) NOT NULL,
  `pid` varchar(32) NOT NULL,
  `userName` varchar(20) NOT NULL,
  `privileges` varchar(255) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

