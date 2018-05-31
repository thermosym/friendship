CREATE TABLE `friend_connection` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `user_a` varchar(255) DEFAULT NULL,
  `user_b` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

