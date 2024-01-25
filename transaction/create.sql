CREATE TABLE `goods` (
                         `id` int NOT NULL AUTO_INCREMENT COMMENT '商品id',
                         `name` varchar(10) COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品名称',
                         `price` decimal(10,2) NOT NULL COMMENT '商品价格',
                         `remain_quantity` int NOT NULL DEFAULT '0' COMMENT '商品剩余数量',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='商品表';

CREATE TABLE `orders` (
                          `id` int NOT NULL AUTO_INCREMENT COMMENT '订单id',
                          `uname` varchar(10) COLLATE utf8mb4_general_ci NOT NULL COMMENT '下单人',
                          `quantity` int DEFAULT NULL,
                          `create_time` datetime(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '购买数量',
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='订单表';