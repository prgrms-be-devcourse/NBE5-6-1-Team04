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
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
            throw new CommonException(ResponseCode.BAD_REQUEST);

        }

        LoginUser loginUser = optional.get();

        if (!passwordEncoder.matches(password, loginUser.getPassword())) {
            throw new CommonException(ResponseCode.BAD_REQUEST);
        }
        if (loginUser.getUserId().equals("admin")) {
            return new Principal(List.of(Role.ROLE_ADMIN), LocalDateTime.now());
        } else {
            return new Principal(List.of(Role.ROLE_CUSTOMER), LocalDateTime.now());
        }
    }


    public User findById(String userId) {
        return userRepository.selectById(userId)
                .orElseThrow(() -> new CommonException(ResponseCode.BAD_REQUEST));
    }

//    public GuestUser GuestSignin(String email) {
//        log.info("GuestSignin 호출됨. 이메일: {}", email);
//        User user = userRepository.selectByEmail(email);
//        log.info("selectByEmail 결과: {}", user);
//
//        log.info("조회된 사용자 역할: {}", user.getRole());
//        if (user.getRole() != Role.ROLE_CUSTOMER) {
//            log.info("게스트 역할이 아님.");
//            throw new CommonException(ResponseCode.BAD_REQUEST);
//        }
//
//        GuestUser guestUser = new GuestUser();
//        guestUser.setEmail(user.getEmail());
//        guestUser.setRole(user.getRole());
//        guestUser.setAddress(user.getAddress());
//        log.info("GuestUser 생성됨: {}", guestUser);
//        return guestUser;
//
//
//    }


    @Transactional
    public GuestUser GuestSignin(String email) {
        log.info("GuestSignin 호출됨. 이메일: {}", email);

        User user = userRepository.selectByEmail(email);
        log.info("selectByEmail 결과: {}", user);

        if (user == null) {
            // 없는 경우 새로 생성
            User newGuest = new User();
            newGuest.setUserId("guest_" + UUID.randomUUID().toString().substring(0, 8));
            newGuest.setEmail(email);
            newGuest.setRole(Role.ROLE_GUEST);
            newGuest.setPassword("");
            newGuest.setName("");
            newGuest.setAddress("");
            newGuest.setCreatedAt(LocalDateTime.now());

            userRepository.insert(newGuest);
            log.info("새 비회원 생성됨: {}", newGuest);

            user = newGuest;
        } else {
            if (user.getRole() == Role.ROLE_CUSTOMER) {
                throw new CommonException(ResponseCode.BAD_REQUEST);
            }
        }

        // 로그인 성공 시 객체 리턴
        GuestUser guestUser = new GuestUser();
        guestUser.setEmail(user.getEmail());
        guestUser.setRole(user.getRole());
        guestUser.setAddress(user.getAddress());
        return guestUser;
    }

    public User findByEmail(String email) {
        return userRepository.selectByEmail(email);
    }
}
