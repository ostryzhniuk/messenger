package andrii.services;

import andrii.dao.UserDAO;
import andrii.dao.UserRoleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserRoleDAO userRoleDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public org.springframework.security.core.userdetails.User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            return new org.springframework.security.core.userdetails.User(authentication.getName(),
                    "", authentication.getAuthorities());
        }
        return (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
    }

}
