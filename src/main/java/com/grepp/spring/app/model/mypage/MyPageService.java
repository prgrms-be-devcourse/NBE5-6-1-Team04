package com.grepp.spring.app.model.mypage;

import com.grepp.spring.app.model.mypage.dto.MyPageOrderDto;
import com.grepp.spring.app.model.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final MyPageOrderRepository myPageOrderRepository;
    private final UserRepository userRepository;

    public List<MyPageOrderDto> getMyOrders(String userId, String sessionUserId) {
        // 본인 소유 데이터(주문 내역)만 조회 가능하도록 소유자 검증
        if (!userId.equals(sessionUserId)) {
            throw new SecurityException("잘못된 접근입니다. (권한 없음)");
        }

        // 회원 존재 여부 검증: userId가 실제로 존재하는 회원인지 확인
        if (!userRepository.existsUserId(userId)) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
        return myPageOrderRepository.findOrdersByUserId(userId);
    }
}
