package com.example.mongo.domain.service.authentication;

import com.example.mongo.domain.model.authentication.Account;
import com.example.mongo.domain.model.authentication.LoggedInUser;
import com.example.mongo.domain.repository.authentication.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class LoggedInUserDetailsService implements UserDetailsService {

    @Autowired
    AccountSharedService accountSharedService;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Account account = accountSharedService.findOne(username);
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();

            if (!account.getRoles().isEmpty()) {
                for (String roleLabel : account.getRoles()) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + roleLabel));
                }
            }

            return new LoggedInUser(account,
                    accountSharedService.isLocked(username),
                    accountSharedService.getLastLoginDate(username),
                    authorities);
        } catch (ResourceNotFoundException e) {
            throw new UsernameNotFoundException("user not found", e);
        }
    }

}
