package com.example.mongo.domain.model.authentication;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.List;

public class LoggedInUser extends User {

    private final Account account;

    private final LocalDateTime lastLoginDate;

    public LoggedInUser(Account account, boolean isLocked,
                        LocalDateTime lastLoginDate,
                        List<SimpleGrantedAuthority> authorities) {
        super(account.getUsername(), account.getPassword(),
                true, true, true,
                !isLocked, authorities);
        this.account = account;
        this.lastLoginDate = lastLoginDate;
    }

    public Account getAccount() {
        return account;
    }

    public LocalDateTime getLastLoginDate() {
        return lastLoginDate;
    }

}
