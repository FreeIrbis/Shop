package com.shop.service;

import com.shop.controller.dto.UserRegistrationDto;
import com.shop.pojo.Mail;
import com.shop.repository.entity.Role;
import com.shop.repository.entity.User;
import com.shop.repository.jpa.RoleRepository;
import com.shop.repository.jpa.UserRepository;
import com.shop.service.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    @Override
    public void updatePassword(String password, Long userId) {
        userRepository.updatePassword(password, userId);
    }

    @Override
    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByConfirmationToken(String confirmationToken){
        return userRepository.findByConfirmationToken(confirmationToken);
    };

    @Override
    public User save(UserRegistrationDto registration){
        User user = convertUserRegistrationDtoToUser(registration);
        User userSave = userRepository.save(user);
        try {
            emailService.sendEmail(createRegistermail(userSave));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return userSave;
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    private Role getRole(String name) {
        return roleRepository.findByName(name);
    }

    private User convertUserRegistrationDtoToUser(UserRegistrationDto registration) {
        User user = new User();
        user.setFirstName(registration.getFirstName());
        user.setLastName(registration.getLastName());
        user.setEmail(registration.getEmail());
        user.setPassword(registration.getPassword());
        user.setEncryptedPassword(passwordEncoder.encode(registration.getPassword()));
        user.setConfirmationToken(UUID.randomUUID().toString());
        user.setRoles(Arrays.asList(getRole("ROLE_USER")));
        return user;
    }

    private Mail createRegistermail(User user) {
        Mail mail = new Mail();
        mail.setFrom("shop@gmail.com");
        mail.setTo(user.getEmail());
        mail.setSubject("Shop registration");
        mail.setPathToTamplate("email/email-confirm-registration");

        Map model = new HashMap();
        model.put("firstName", user.getFirstName());
        model.put("lastName", user.getLastName());
        model.put("signature", "www.shop...");
        model.put("confirmUrl", "http://localhost:8080/confirm?token=" + user.getConfirmationToken());
        mail.setModel(model);

        return mail;
    }
}
