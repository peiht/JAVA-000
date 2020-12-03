CREATE TABLE `user` (
                        `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                        `password` varchar(100) NOT NULL DEFAULT '' COMMENT '密码',
                        `phone` varchar(15) NOT NULL DEFAULT '' COMMENT '手机号',
                        `c_time` datetime NOT NULL DEFAULT now() COMMENT '创建时间',
                        `u_time` datetime NOT NULL DEFAULT now() COMMENT '修改时间',
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `phone` (`phone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;