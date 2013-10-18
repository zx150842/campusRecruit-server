drop database myjob;
create database myjob;
use myjob;

CREATE TABLE `BBS_REPLY` (
  `id` int(31) NOT NULL,
  `content` text,
  `create_time` datetime DEFAULT NULL,
  `input_time` datetime DEFAULT NULL,
  `last_modified` datetime DEFAULT NULL,
  `ref_reply_id` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `topic_id` int(31) DEFAULT NULL,
  `author_id` varchar(32) DEFAULT NULL,
  `be_replied` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `BBS_SECTION` (
  `id` int(31) NOT NULL,
  `clicks` int(31) DEFAULT NULL,
  `company_id` int(31) DEFAULT '0',
  `input_time` datetime DEFAULT NULL,
  `joins` int(31) DEFAULT NULL,
  `section_name` varchar(255) DEFAULT NULL,
  `topics` int(31) DEFAULT NULL,
  `admin_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `BBS_TOPIC` (
  `id` int(31) NOT NULL,
  `topic_body` text,
  `topic_category` int(31) DEFAULT NULL,
  `clicks` int(31) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `delete_flag` int(11) DEFAULT NULL,
  `input_time` datetime DEFAULT NULL,
  `last_reply_time` datetime DEFAULT NULL,
  `replies` int(31) DEFAULT NULL,
  `topic_title` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `section_id` int(31) DEFAULT NULL,
  `author_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `CAREER_TALK` (
  `id` int(31) NOT NULL,
  `clicks` int(31) DEFAULT '0',
  `company_name` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `org_id` int(11) DEFAULT '0',
  `db` int(11) DEFAULT '0',
  `career_talk_date` varchar(255) DEFAULT NULL,
  `input_time` datetime DEFAULT NULL,
  `joins` int(31) DEFAULT '0',
  `place` varchar(255) DEFAULT NULL,
  `replies` int(31) DEFAULT '0',
  `school_name` varchar(255) DEFAULT NULL,
  `topic_id` int(31) DEFAULT '0',
  `company_id` int(31) DEFAULT NULL,
  `career_time` varchar(255) DEFAULT NULL,
  `source_from` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `delete_flag` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `COMPANY` (
  `id` int(31) NOT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `company_rating` int(31) DEFAULT '0',
  `industry` varchar(255) DEFAULT NULL,
  `input_time` datetime DEFAULT NULL,
  `introduction` text,
  `province` varchar(255) DEFAULT '0',
  `company_scale` varchar(255) DEFAULT NULL,
  `section_id` int(31) DEFAULT '0',
  `company_type` int(11) DEFAULT '0',
  `state` varchar(255) DEFAULT NULL,
  `state_time` varchar(255) DEFAULT NULL,
  `famous_company` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `JOB` (
  `id` int(31) NOT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `org_id` int(11) DEFAULT '0',
  `db` int(11) DEFAULT '0',
  `created_time` varchar(255) DEFAULT NULL,
  `description` text,
  `contact` text,
  `industry` varchar(255) DEFAULT NULL,
  `input_time` datetime DEFAULT NULL,
  `joins` int(31) DEFAULT '0',
  `place` varchar(255) DEFAULT NULL,
  `job_name` varchar(255) DEFAULT NULL,
  `replies` int(31) DEFAULT '0',
  `topic_id` int(31) DEFAULT '0',
  `company_id` int(31) DEFAULT NULL,
  `company_type` int(11) DEFAULT '0',
  `state` varchar(255) DEFAULT NULL,
  `state_time` varchar(255) DEFAULT NULL,
  `statetime` varchar(255) DEFAULT NULL,
  `clicks` int(31) DEFAULT '0',
  `source_from` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `table_form` text,
  `img_url` text DEFAULT NULL,
  `delete_flag` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `MAJOR` (
  `id` int(31) NOT NULL,
  `input_time` datetime DEFAULT NULL,
  `major_name` varchar(255) DEFAULT NULL,
  `major_type` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `PREFERENCE` (
  `id` int(31) NOT NULL,
  `company_type` text,
  `input_time` datetime DEFAULT NULL,
  `notify_type` varchar(255) DEFAULT NULL,
  `province` text,
  `industry` text,
  `source_from` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `PRIVATE_LETTER` (
  `id` int(31) NOT NULL,
  `from_user_id` varchar(32) DEFAULT NULL,
  `to_user_id` varchar(32) DEFAULT NULL,
  `from_user_name` varchar(255) DEFAULT NULL,
  `to_user_name` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `content` text,
  `created_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `USER` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `user_email` varchar(255) DEFAULT NULL,
  `user_gender` int(11) DEFAULT '0',
  `input_time` datetime DEFAULT NULL,
  `introduction` text,
  `nickname` varchar(255) DEFAULT NULL,
  `school_name` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `user_password` varchar(255) DEFAULT NULL,
  `last_read_campus_recruit` varchar(255) DEFAULT NULL,
  `last_read_career_talk` varchar(255) DEFAULT NULL,
  `major_id` int(31) DEFAULT NULL,
  `preference_id` int(31) DEFAULT NULL,
  `last_read_topic` int(31) DEFAULT NULL,
  `img_type` varchar(255) DEFAULT NULL,
  `last_read_reply` int(31) DEFAULT NULL,
  `last_read_letter` int(31) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `USER_BBS_SECTION` (
  `user_id` varchar(32) NOT NULL,
  `section_id` int(31) NOT NULL,
  PRIMARY KEY (`user_id`,`section_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `byrlist` (
  `pid` int(11) NOT NULL DEFAULT '0',
  `url` varchar(100) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `releaseDate` varchar(20) DEFAULT NULL,
  `jobdetails` text,
  `insert_flag` int(11) DEFAULT '0',
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `dajielist` (
  `pid` int(11) NOT NULL DEFAULT '0',
  `type` varchar(50) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `CompanyProperty` varchar(50) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `industry` varchar(20) DEFAULT NULL,
  `releaseDate` varchar(20) DEFAULT NULL,
  `state` varchar(50) DEFAULT NULL,
  `statetime` varchar(50) DEFAULT NULL,
  `jobdetails` text,
  `form` text,
  `imageurl` varchar(500) DEFAULT NULL,
  `isfame` tinyint(1) DEFAULT NULL,
  `isfresh` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `dajiexj` (
  `id` int(11) NOT NULL DEFAULT '0',
  `url` varchar(100) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `date` varchar(20) DEFAULT NULL,
  `time` varchar(100) DEFAULT NULL,
  `school` varchar(50) DEFAULT NULL,
  `local` varchar(50) DEFAULT NULL,
  `insert_flag` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `qinghualist` (
  `pid` int(11) NOT NULL DEFAULT '0',
  `url` varchar(100) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `releaseDate` varchar(20) DEFAULT NULL,
  `jobdetails` text,
  `insert_flag` int(11) DEFAULT '0',
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;