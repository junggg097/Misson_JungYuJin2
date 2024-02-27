package com.example.Shoppingmall.service;

import com.example.Shoppingmall.dto.UserDto;
import com.example.Shoppingmall.entity.UserEntity;
import com.example.Shoppingmall.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public void signUp(UserEntity userEntity) {
        userRepository.save(userEntity);
    }
}
