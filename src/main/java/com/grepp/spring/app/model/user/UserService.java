package com.grepp.spring.app.model.user;


import com.grepp.spring.app.model.auth.Code.Role;
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

    public Principal signin(String userId, String password) {

        Optional<LoginUser> optional = userRepository.selectLoginUserById(userId);

        if(optional.isEmpty())
            return Principal.CUSTOMER;

        LoginUser loginUser = optional.get();

        if(!(loginUser.getPassword()).equals(password))
            return Principal.CUSTOMER;

        return new Principal(userId, List.of(Role.ROLE_CUSTOMER), LocalDateTime.now());
    }

    public User findById(String userId) {
        return userRepository.selectById(userId)
            .orElseThrow(() -> new CommonException(ResponseCode.BAD_REQUEST));
    }
}
