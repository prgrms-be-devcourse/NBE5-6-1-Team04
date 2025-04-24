package com.grepp.spring.app.model.user;


import com.grepp.spring.app.model.auth.Code.Role;
import com.grepp.spring.app.model.user.dto.User;
import com.grepp.spring.infra.error.exceptions.CommonException;
import com.grepp.spring.infra.response.ResponseCode;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService{

    private final UserRepository userRepository;

@Transactional
    public void signup(User dto, Role role) {
    if(userRepository.existsUserId(dto.getUserId()))
        throw new CommonException(ResponseCode.BAD_REQUEST);

    dto.setRole(role);
    dto.setPassword(dto.getPassword());
    dto.setCreatedAt(LocalDateTime.now());
    userRepository.insert(dto);



    }
}
