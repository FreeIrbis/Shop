package com.shop.service.api;

import com.shop.controller.dto.UserRegistrationDto;
import com.shop.repository.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);
    User findByConfirmationToken(String confirmationToken);

    User save(UserRegistrationDto registration);

    void updatePassword(String password, Long userId);
}