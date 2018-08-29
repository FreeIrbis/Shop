package com.shop.repository.jpa;

import com.shop.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Modifying
    @Query("update User u set u.password = :password where u.id = :id")
    void updatePassword(@Param("password") String password, @Param("id") Long id);

    @Modifying
    @Query("update User u set u.enabled = :enabled where u.id = :id")
    void updateEnabled(@Param("enabled") Boolean enabled, @Param("id") Long id);

    @Modifying
    @Query("update User u set u.emailVerified = :emailVerified where u.id = :id")
    void updateEmailVerified(@Param("emailVerified") Boolean emailVerified, @Param("id") Long id);

//    @Modifying
//    @Transactional
//    @Query(value = "DELETE FROM CARD_INFO WHERE EXPIRY_LIVE_DATE < timestampadd(minute, -3, now());", nativeQuery = true)
//    Integer deleteOldCardInfo();


}