package by.smartym_pro.test_task.security;

import by.smartym_pro.test_task.entity.User;
import by.smartym_pro.test_task.security.jwt.JwtUserFactory;
import by.smartym_pro.test_task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link UserDetailsService} interface for {@link JwtUser}.
 *
 * @author Semizhon Roman
 * @version 1.0
 */

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userService.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }

        return JwtUserFactory.create(user);
    }
}
