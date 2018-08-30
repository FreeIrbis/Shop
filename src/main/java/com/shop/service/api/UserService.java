package com.shop.service.api;

import com.shop.controller.dto.UserRegistrationDto;
import com.shop.repository.entity.EmailConfirmationToken;
import com.shop.repository.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> getAll();

    User findByEmail(String email);

    User save(UserRegistrationDto registration, HttpServletRequest request);

    User resetPassword(User user, HttpServletRequest request);

    User updateUser(User user);

    void updatePassword(String password, Long userId);

    void updateEnabled(Boolean enabled, Long userId);

    EmailConfirmationToken getEmailConfirmationToken(String token);

    User confirmEmail(EmailConfirmationToken emailConfirmationToken);

}