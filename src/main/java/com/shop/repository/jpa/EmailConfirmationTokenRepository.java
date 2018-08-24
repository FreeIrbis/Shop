package com.shop.repository.jpa;

import com.shop.repository.entity.EmailConfirmationToken;
import com.shop.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailConfirmationTokenRepository extends JpaRepository<EmailConfirmationToken, Long> {

    EmailConfirmationToken findByToken(String token);

    EmailConfirmationToken findByUser(User user);

    void deleteByToken(String token);

}
