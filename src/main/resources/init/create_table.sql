-- USER
CREATE TABLE `user` (
    `user_id` varchar(50) NOT NULL,
    `password` varchar(100),
    `name` varchar(50),
    `address` varchar(300),
    `email` varchar(100) NOT NULL,
    `role` varchar(50) NOT NULL COMMENT 'GIN IDX',
    `created_at` timestamp NOT NULL,
    PRIMARY KEY (`user_id`)
);

-- PRODUCT
CREATE TABLE `product` (
    `product_id` bigint NOT NULL,
    `product_name` varchar(100) NOT NULL COMMENT 'GIN IDX (TRIGRAM)',
    `price` int NOT NULL,
    `description` varchar(100),
    `stock` int NOT NULL COMMENT 'GIN IDX',
    `created_at` timestamp NOT NULL,
    PRIMARY KEY (`product_id`)
);

-- PRODUCT_IMAGE
CREATE TABLE `product_image` (
    `product_image_id` bigint NOT NULL,
    `product_id` bigint NOT NULL,
    `type` varchar(50),
    `name` varchar(50),
    PRIMARY KEY (`product_image_id`, `product_id`),
    FOREIGN KEY (`product_id`) REFERENCES `product`(`product_id`)
);

-- CART
CREATE TABLE `cart` (
    `cart_id` bigint NOT NULL,
    `user_id` varchar(50) NOT NULL,
    PRIMARY KEY (`cart_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`)
);

-- CART_ITEM
CREATE TABLE `cart_item` (
    `order_item_id` bigint NOT NULL,
    `cart_id` bigint NOT NULL,
    `product_id` bigint NOT NULL,
    `product_count` int,
    PRIMARY KEY (`order_item_id`, `cart_id`),
    FOREIGN KEY (`cart_id`) REFERENCES `cart`(`cart_id`),
    FOREIGN KEY (`product_id`) REFERENCES `product`(`product_id`)
);

-- ORDER
CREATE TABLE `order` (
    `order_id` bigint NOT NULL,
    `user_id` varchar(50) NOT NULL,
    `order_count` int NOT NULL,
    `total_price` int NOT NULL,
    `created_at` timestamp NOT NULL,
    `order_status` varchar(50),
    `order_address` varchar(50),
    PRIMARY KEY (`order_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`)
);

-- ORDER_ITEM
CREATE TABLE `order_item` (
    `order_item_id` bigint NOT NULL,
    `order_id` bigint NOT NULL,
    `product_id` bigint NOT NULL,
    `order_count` int NOT NULL,
    PRIMARY KEY (`order_item_id`),
    FOREIGN KEY (`order_id`) REFERENCES `order`(`order_id`),
    FOREIGN KEY (`product_id`) REFERENCES `product`(`product_id`)
);

-- PAYMENT
CREATE TABLE `payment` (
    `payment_id` bigint NOT NULL,
    `order_id` bigint NOT NULL,
    `payment_price` int,
    `create_at` timestamp,
    PRIMARY KEY (`payment_id`),
    FOREIGN KEY (`order_id`) REFERENCES `order`(`order_id`)
);

-- DELIVERY
CREATE TABLE `delivery` (
    `delivery_id` bigint NOT NULL,
    `order_id` bigint NOT NULL,
    `delivery_status` varchar(50),
    `delivery_expected_date` timestamp,
    PRIMARY KEY (`delivery_id`),
    FOREIGN KEY (`order_id`) REFERENCES `order`(`order_id`)
);
