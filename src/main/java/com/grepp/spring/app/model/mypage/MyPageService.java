package com.grepp.spring.app.model.mypage;

import com.grepp.spring.app.model.mypage.dto.MyPageDto;
import com.grepp.spring.app.model.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final MyPageRepository myPageRepository;
    private final UserRepository userRepository;

    public List<MyPageDto> getMyOrders(String userId, String sessionUserId) {
        // TODO: 로그인 세션 검증은 로그인 쪽 문제 해결 후 다시 활성화할 것
        // if (!userId.equals(sessionUserId)) {
        //     throw new SecurityException("잘못된 접근입니다. (권한 없음)");
        // }
        if (!userRepository.existsUserId(userId)) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
        return myPageRepository.findOrdersByUserId(userId);
    }
}
