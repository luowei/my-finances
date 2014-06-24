CREATE database db_finance
use db_finance

#日消费表
DROP TABLE IF EXISTS `daytip`;
CREATE TABLE `daytip` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id自增主键',
  `tipDate` date DEFAULT NULL COMMENT '日期',
  `tipDateStr` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '日期原字符串',
  `week` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '星期',
  `money` float DEFAULT NULL COMMENT '使用的钱的数量',
  `desc` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '描述',
  `type` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_daytip_tipDateStr` (`tipDateStr`)
) ENGINE=InnoDB AUTO_INCREMENT=381 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

#ALTER TABLE daytip ADD CONSTRAINT uk_daytip_tipDateStr UNIQUE (tipDateStr);

#日消费详细表
DROP TABLE IF EXISTS `daytipDetail`;
CREATE TABLE `daytipDetail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id自增主键',
  `tipDate` date DEFAULT NULL COMMENT '日期',
  `tipDateStr` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '日期原字符串',
  `week` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '星期',
  `money` float DEFAULT NULL COMMENT '使用的钱的数量',
  `desc` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '描述',
  `type` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=337 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

#帐号表
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键id',
  `siteName` varchar(200) COLLATE utf8_unicode_ci NOT NULL COMMENT '所属网站名称',
  `keyName` varchar(200) COLLATE utf8_unicode_ci NOT NULL COMMENT '帐号名称',
  `keySecret` varchar(256) COLLATE utf8_unicode_ci NOT NULL COMMENT '帐号密码',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_account_siteNameAndKey` (`siteName`,`keyName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

#用户表
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键Id',
  `username` varchar(200) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(200) COLLATE utf8_unicode_ci NOT NULL COMMENT '密码',
  `token` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '授权令牌',
  `email` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `secretKey` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户数据加密密钥',
  `role` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户角色',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_username` (`username`),
  UNIQUE KEY `uk_user_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

insert  into `user`(`id`,`username`,`password`,`token`,`email`,`secretKey`,`role`)
values (1,'admin','MDk0OTMwOTFoNGR2bnRr',NULL,'admin@rootls.com','luowei','ROLE_ADMIN');

#正则式
DROP TABLE IF EXISTS `regextip`;
CREATE TABLE `regextip` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `name` varchar(200) COLLATE utf8_unicode_ci NOT NULL COMMENT '名称',
  `describe` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '描述',
  `regex` varchar(10000) COLLATE utf8_unicode_ci NOT NULL COMMENT 'base64编码后的正则表达式',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_regextip_name_describe` (`name`,`describe`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

#菜单
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `menuName` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '菜单名',
  `menuUrl` varchar(256) COLLATE utf8_unicode_ci NOT NULL COMMENT '菜单链接',
  `describtion` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '菜单描述',
  `parentId` int(11) DEFAULT NULL COMMENT '父节点Id',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_memu_menuName` (`menuName`)
) ENGINE=InnoDB AUTO_INCREMENT=304 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

insert  into `menu`(`id`,`menuName`,`menuUrl`,`describtion`,`parentId`,`sort`)
values (1,'首页','','首页...',NULL,1),
(2,'crud管理','','crud管理...',NULL,2),(3,'消费列表','/finance/list','消费列表...',2,1),
(4,'消费明细列表','/finance/list?tableName=daytipDetail','消费明细列表.',2,2),
(5,'正则列表','/regex/list','正则列表。',2,4),(6,'帐号列表','/account/list','帐号列表。',2,3),
(7,'其他','','其他。',NULL,3),(8,'字符串处理','/other/toTools','字符串处理。',7,1),
(13,'菜单列表','/menu/list','菜单列表..',2,6),(14,'日记列表','/diary/list','日记列表。。',2,7);

#日记表
DROP TABLE IF EXISTS `diary`;
CREATE TABLE `diary` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `diaryDate` date NOT NULL COMMENT '日期',
  `content` varchar(2000) COLLATE utf8_unicode_ci NOT NULL COMMENT '日记内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

insert  into `diary`(`id`,`diaryDate`,`content`) values (1,'2014-04-30','上班，为这个项目添加了以spring security验证方式，及添加了日记功能。');