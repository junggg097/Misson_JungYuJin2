package com.example.Shoppingmall.controller;


import com.example.Shoppingmall.dto.UserDto;
import com.example.Shoppingmall.entity.UserEntity;
import com.example.Shoppingmall.repo.UserRepository;
import com.example.Shoppingmall.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserDetailsManager manager;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> signUp(
            @RequestBody
            UserDto dto
    ) {
        if (manager.userExists(dto.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 가입된 사용자입니다.");
        }

            UserEntity newUser = UserEntity.builder()
                    .username(dto.getUsername())
                    .password(passwordEncoder.encode(dto.getPassword()))
                    .authorities("ROLE_INERT")
                    .build();

            userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 되었습니다.");
    }
}
