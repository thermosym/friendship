CREATE TABLE `friend_connection` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `user_a` varchar(255) DEFAULT NULL,
  `user_b` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `friendship_filter` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `filter_type` varchar(255) DEFAULT NULL,
  `filter_object` varchar(255) DEFAULT NULL,
  `filter_subject` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;