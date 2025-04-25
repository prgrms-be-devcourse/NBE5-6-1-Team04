-- 테스트용 사용자 생성
INSERT INTO user (user_id, password, name, email, phone, address)
VALUES ('user101', 'password101', '테스트사용자', 'test101@test.com', '010-1234-5678', '서울시 강남구');

-- user101의 장바구니 생성
INSERT INTO cart (cart_id, user_id)
VALUES (101, 'user101');

-- 테스트용 상품 데이터 (기존 상품이 없는 경우를 대비)
INSERT INTO product (product_id, product_name, price, description, stock_quantity)
VALUES
    (101, '아메리카노', 4500, '깊고 진한 맛의 아메리카노', 100),
    (102, '카페라떼', 5000, '부드러운 우유와 에스프레소의 조화', 100),
    (103, '카푸치노', 5500, '풍성한 우유 거품의 카푸치노', 100);

-- 테스트용 상품 이미지
INSERT INTO product_image (image_id, product_id, image_url, is_thumbnail)
VALUES
    (101, 101, '/images/americano.jpg', true),
    (102, 102, '/images/latte.jpg', true),
    (103, 103, '/images/cappuccino.jpg', true);

-- 테스트 데이터 확인용 쿼리들
/*
-- 사용자 확인
SELECT * FROM user WHERE user_id = 'user101';

-- 장바구니 확인
SELECT * FROM cart WHERE user_id = 'user101';

-- 상품 확인
SELECT * FROM product WHERE product_id IN (101, 102, 103);

-- 장바구니 상품 확인
SELECT
    c.user_id,
    ci.order_item_id,
    p.product_name,
    ci.product_count,
    p.price,
    (p.price * ci.product_count) as total_price
FROM cart c
JOIN cart_item ci ON c.cart_id = ci.cart_id
JOIN product p ON ci.product_id = p.product_id
WHERE c.user_id = 'user101';
*/