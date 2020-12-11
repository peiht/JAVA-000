**（必做）**基于电商交易场景（用户、商品、订单），设计一套简单的表结构，提交 DDL 的 SQL 文件到 Github（后面 2 周的作业依然要是用到这个表结构）



用户表

```sql
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(30) NOT NULL DEFAULT '' COMMENT '用户名称',
  `password` varchar(100) NOT NULL DEFAULT '' COMMENT '密码',
  `balance` decimal(10,0) NOT NULL COMMENT '用户余额',
  `freeze_amount` decimal(10,0) NOT NULL COMMENT '冻结金额，扣款暂存余额',
  `phone` varchar(15) NOT NULL DEFAULT '' COMMENT '手机号',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone` (`phone`) USING BTREE,
  KEY `IDXC_TIME` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
```



商品表：

```sql
CREATE TABLE `goods` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `uid` int(11) DEFAULT NULL COMMENT '创建者',
  `title` varchar(50) DEFAULT NULL COMMENT '商品名称',
  `tags` varchar(150) DEFAULT NULL COMMENT '商品标签',
  `image_s` varchar(255) DEFAULT NULL COMMENT '图片 小',
  `image_m` varchar(255) DEFAULT NULL COMMENT '图片 中',
  `image_b` varchar(100) DEFAULT NULL COMMENT '图片 大',
  `images` varchar(500) DEFAULT NULL COMMENT '商品轮播图片，json数组',
  `video` varchar(150) DEFAULT NULL COMMENT '商品视频地址',
  `check_status` tinyint(4) DEFAULT NULL COMMENT '1待审核 2通过 3驳回',
  `status` tinyint(4) DEFAULT NULL COMMENT '商品状态  1待上架 2上架 3下架',
  `c_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `u_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_status` (`status`),
  KEY `p_type` (`p_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
```

商品明细表：

```sql
CREATE TABLE `stock` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `goods_id` int(10) NOT NULL COMMENT '商品id',
  `stock_all` int(10) NOT NULL COMMENT '当前品类总库存',
  `stock_pre` int(10) NOT NULL COMMENT '当前品类已占用库存',
  `create_time` datetime DEFAULT now() COMMENT '创建时间',
  `update_time` datetime DEFAULT now() COMMENT '修改时间',
   PRIMARY KEY (`id`),
   KEY `goods_idx` (`goods_id`) USING BTREE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
```

订单表：

```sql
CREATE TABLE `goods_order` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `order_name` varchar(100) DEFAULT NULL COMMENT '订单名称',
  `user_id` int(10) DEFAULT NULL COMMENT '用户标识',
  `trade_amount` varchar(100) DEFAULT NULL COMMENT '订单金额',
  `order_status` int(10) DEFAULT NULL COMMENT '订单状态 0:等待支付  1：支付中  2：成功  3：失败 4: 已退款 ',
  `create_time` datetime DEFAULT now() COMMENT '创建时间',
  `update_time` datetime DEFAULT now() COMMENT '更新时间',
  PRIMARY KEY (`order_id`),
  KEY `userId_idx` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

