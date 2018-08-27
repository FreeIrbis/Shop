package com.shop.repository.entity;

import com.shop.repository.entity.base.DomainObject;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity
public class EmailConfirmationToken extends DomainObject {

    private static final int EXPIRATION = 60 * 24;

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
    private Boolean used = false;
    private Date expiryDate;

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public EmailConfirmationToken() {
    }

    public EmailConfirmationToken(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public EmailConfirmationToken(String token, User user, Date expiryDate) {
        this.token = token;
        this.user = user;
        this.expiryDate = expiryDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(int days){
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_YEAR, days);
        this.expiryDate = now.getTime();
    }
}

