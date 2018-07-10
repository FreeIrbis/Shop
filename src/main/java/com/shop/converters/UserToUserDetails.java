package com.shop.converters;

import com.shop.config.security.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import com.shop.repository.entity.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class UserToUserDetails implements Converter<User, UserDetails> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public UserDetails convert(User user) {
        UserDetailsImpl userDetails = new UserDetailsImpl();

        if (user != null) {
            userDetails.setUsername(user.getEmail());
            userDetails.setPassword(user.getEncryptedPassword());
            logger.info("user enabled: {}", user.getEnabled());
            userDetails.setEnabled(user.getEnabled());
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> {
                logger.info("user role: {}", role.getName());
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            });
            userDetails.setAuthorities(authorities);
        }

        return userDetails;
    }
}
