package com.shop.service;

import com.shop.controller.dto.UserRegistrationDto;
import com.shop.pojo.Mail;
import com.shop.repository.entity.EmailConfirmationToken;
import com.shop.repository.entity.PasswordResetToken;
import com.shop.repository.entity.Role;
import com.shop.repository.entity.User;
import com.shop.repository.jpa.EmailConfirmationTokenRepository;
import com.shop.repository.jpa.PasswordResetTokenRepository;
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

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final static int EXPIRY_PERIOD = 7; //days
    private final static int RESET_PASSWORD_PERIOD = 30; //minutes

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailConfirmationTokenRepository emailConfirmationTokenRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

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
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void updatePassword(String password, Long userId) {
        userRepository.updatePassword(password, userId);
    }


    @Override
    public void updateEnabled(Boolean enabled, Long userId) {
        userRepository.updateEnabled(enabled, userId);
    }

    @Override
    public EmailConfirmationToken getEmailConfirmationToken(String token) {
        EmailConfirmationToken et = emailConfirmationTokenRepository.findByToken(token);
        logger.info("EmailConfirmationToken is null = " + (et == null));
        return et;
    }

    @Override
    public User confirmEmail(EmailConfirmationToken emailConfirmationToken) {
        User user = emailConfirmationToken.getUser();
        if(user != null) {
            userRepository.updateEmailVerified(true, user.getId());
            emailConfirmationTokenRepository.updateUsed(true, emailConfirmationToken.getId());
        }
        return user;
    }

    @Override
    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(UserRegistrationDto registration, HttpServletRequest request) {
        User user = convertUserRegistrationDtoToUser(registration);
        User userSave = userRepository.save(user);
        String token = UUID.randomUUID().toString();
        EmailConfirmationToken emailConfirmationToken = new EmailConfirmationToken(token, userSave);
        emailConfirmationToken.setExpiryDate(EXPIRY_PERIOD);
        emailConfirmationTokenRepository.save(emailConfirmationToken);
        try {
            emailService.sendEmail(createRegistermail(userSave, token, request));
            logger.info("email was sended");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return userSave;
    }

    @Override
    public User resetPassword(User user, HttpServletRequest request) {
        PasswordResetToken token = new PasswordResetToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setExpiryDate(RESET_PASSWORD_PERIOD);
        tokenRepository.save(token);


        try {
            emailService.sendEmail(createResetPasswordMail(user, token.getToken(), request));
            logger.info("email was sended");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return user;
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
        //user.setConfirmationToken(UUID.randomUUID().toString());
        user.setRoles(Arrays.asList(getRole("ROLE_USER")));
        return user;
    }

    private Mail createRegistermail(User user, String token, HttpServletRequest request) {
        Mail mail = new Mail();
        mail.setFrom("shop@gmail.com");
        mail.setTo(user.getEmail());
        mail.setSubject("Shop registration");
        mail.setPathToTamplate("email/email-confirm-registration");

        Map model = new HashMap();
        model.put("firstName", user.getFirstName());
        model.put("lastName", user.getLastName());
        model.put("signature", "www.shop...");
        String url = getBaseUrl(request);
        model.put("confirmUrl", url + "/registration/confirm?token=" + token);
        mail.setModel(model);

        return mail;
    }

    private Mail createResetPasswordMail(User user, String token, HttpServletRequest request) {
        Mail mail = new Mail();
        mail.setFrom("shop@gmail.com");
        mail.setTo(user.getEmail());
        mail.setSubject("Reset Password");
        mail.setPathToTamplate("email/email-reset-password");

        Map model = new HashMap();
        model.put("firstName", user.getFirstName());
        model.put("lastName", user.getLastName());
        model.put("signature", "www.shop...");

        String baseUrl = getBaseUrl(request);
        model.put("resetUrl", baseUrl + "/reset-password?token=" + token);
        mail.setModel(model);

        return mail;
    }

    private String getBaseUrl(HttpServletRequest request) {
       return  request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    }
}
