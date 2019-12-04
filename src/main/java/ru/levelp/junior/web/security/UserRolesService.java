package ru.levelp.junior.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.levelp.junior.dao.AccountsRepository;
import ru.levelp.junior.entities.Account;

import java.util.ArrayList;

@Component
public class UserRolesService implements UserDetailsService {
    @Autowired
    private AccountsRepository accounts;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account found = accounts.findByLogin(username);
        if (found == null) {
            throw new UsernameNotFoundException("Account " + username + " not found.");
        }

        ArrayList<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));

        if (found.getLogin().equals("test")) {
            roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return new User(username, found.getEncryptedPassword(), roles);
    }
}
