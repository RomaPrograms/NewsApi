package by.smartym_pro.test_task.security.jwt;

import by.smartym_pro.test_task.entity.Role;
import by.smartym_pro.test_task.entity.User;
import by.smartym_pro.test_task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//@Component
public class JwtAuthenticationProvider implements AuthenticationManager {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user = userService.findByUsername(authentication.getName());
        if(user == null) {
            throw new BadCredentialsException("User not found");
        }
//        String password = passwordEncoder.encode((String) authentication.getCredentials());
        String password = (String) authentication.getCredentials();
        System.out.println("Auth provider: " + authentication.getCredentials());
        System.out.println("Auth provider: " + user.getPassword());
        System.out.println("Auth provider: " + passwordEncoder.encode((String) authentication.getCredentials()));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }

        List<GrantedAuthority> roles = new ArrayList<>();
        for(Role role : user.getRoles()) {
            roles.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), roles);
    }
}
