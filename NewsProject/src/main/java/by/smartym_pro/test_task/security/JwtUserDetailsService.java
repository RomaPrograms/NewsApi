package by.smartym_pro.test_task.security;

import by.smartym_pro.test_task.entity.User;
import by.smartym_pro.test_task.exception.IncorrectDataException;
import by.smartym_pro.test_task.service.UserService;
import by.smartym_pro.test_task.service.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Implementation of {@link UserDetailsService} interface for
 * {@link by.smartym_pro.test_task.security.jwt.JwtUser}.
 *
 * @author Semizhon Roman
 * @version 1.0
 */

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    private final Logger LOGGER = LogManager.getLogger(UserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = null;
        try {
            user = userService.findByUsername(username);
        } catch (IncorrectDataException e) {
            LOGGER.error("User with username: " + username + " not found");
            throw new UsernameNotFoundException(
                    "User with username: " + username + " not found");
        }

        List<GrantedAuthority> grantedAuthorities
                = user.getRoles().stream()
                .map(role ->
                        new SimpleGrantedAuthority(role.getName())
                ).collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
