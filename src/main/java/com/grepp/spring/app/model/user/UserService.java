package com.grepp.spring.app.model.user;


import com.grepp.spring.app.model.auth.Code.Role;
import com.grepp.spring.app.model.user.dto.GuestUser;
import com.grepp.spring.app.model.user.dto.LoginUser;
import com.grepp.spring.app.model.user.dto.Principal;
import com.grepp.spring.app.model.user.dto.User;
import com.grepp.spring.infra.error.exceptions.CommonException;
import com.grepp.spring.infra.response.ResponseCode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void signup(User dto, Role role) {
        if (userRepository.existsUserId(dto.getUserId())) {
            throw new CommonException(ResponseCode.BAD_REQUEST);
        }

        dto.setRole(role);
        dto.setPassword(dto.getPassword());
        dto.setCreatedAt(LocalDateTime.now());
        userRepository.insert(dto);


    }

    public Principal signin(String userId, String password) {

        Optional<LoginUser> optional = userRepository.selectLoginUserById(userId);

        if (optional.isEmpty()) {
            return Principal.GUEST;
        }

        LoginUser loginUser = optional.get();

        if (!(loginUser.getPassword()).equals(password)) {
            return Principal.GUEST;
        }

        return new Principal(List.of(Role.ROLE_CUSTOMER), LocalDateTime.now());
    }


    public User findById(String userId) {
        return userRepository.selectById(userId)
            .orElseThrow(() -> new CommonException(ResponseCode.BAD_REQUEST));
    }

    public GuestUser GuestSignin(String email) {
        log.info("GuestSignin 호출됨. 이메일: {}", email);
        User user = userRepository.selectByEmail(email);
        log.info("selectByEmail 결과: {}", user);

        if (user == null) {
            log.info("사용자를 찾을 수 없음.");
            return null;
        }

        log.info("조회된 사용자 역할: {}", user.getRole());
        if (user.getRole() != Role.ROLE_GUEST) {
            log.info("게스트 역할이 아님.");
            return null;
        }

        GuestUser guestUser = new GuestUser();
        guestUser.setEmail(user.getEmail());
        guestUser.setRole(user.getRole());
        guestUser.setAddress(user.getAddress());
        log.info("GuestUser 생성됨: {}", guestUser);
        return guestUser;


    }

}
