package by.smartym_pro.test_task.controller;

import by.smartym_pro.test_task.dto.JwtRequestDto;
import by.smartym_pro.test_task.dto.UserDto;
import by.smartym_pro.test_task.entity.JwtResponse;
import by.smartym_pro.test_task.entity.Role;
import by.smartym_pro.test_task.entity.User;
//import by.smartym_pro.test_task.security.jwt.JwtAuthenticationProvider;
import by.smartym_pro.test_task.security.jwt.JwtTokenUtil;
import by.smartym_pro.test_task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * REST controller for authentication requests (login, logout, register, etc.)
 *
 * @author Semizhon Roman
 * @version 1.0
 */

@RequestMapping("/users")
@RestController
@CrossOrigin
public class UserController {
//    @Autowired
//    private JwtAuthenticationProvider authenticationManager;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody JwtRequestDto requestDto) throws Exception {
        try {
            authenticate(requestDto.getUsername(), requestDto.getPassword());
            User user = userService.findByUsername(requestDto.getUsername());

            if (user == null) {
                throw new UsernameNotFoundException("User with username: "
                        + requestDto.getUsername() + " not found");
            }

            String token = jwtTokenUtil.generateToken(user.getUsername(), user.getRoles());

            return ResponseEntity.ok(new JwtResponse(token));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("/sign_up")
    public ResponseEntity<User> addUser(@RequestBody UserDto userDto) {
        HttpHeaders headers = new HttpHeaders();

        if (userDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String hashedPassword
                = passwordEncoder.encode((String) userDto.getPassword());

        userDto.setPassword(hashedPassword);
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("USER"));
        userDto.setRoles(roles);
        User user = userDto.toUser();
        this.userService.addUser(user);

        return new ResponseEntity<>(user, headers, HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            System.out.println(e.getMessage());
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            System.out.println(e);
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
