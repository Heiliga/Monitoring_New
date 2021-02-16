package ru.Senkova.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.Senkova.app.api.UserAppService;
import ru.Senkova.domain.UserApp;

@Service
public class UserAppBasedUserDetailService implements UserDetailsService {

    @Autowired
    private UserAppService userAppService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) {
        try {
            UserApp user = userAppService.findByLogin(login);
            return UserAppPrinciple.build(user);
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("User not found", e);
        }
    }

}
