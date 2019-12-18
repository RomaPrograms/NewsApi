package by.smartym_pro.test_task.controller;

import by.smartym_pro.test_task.dto.JwtRequestDto;
import by.smartym_pro.test_task.dto.JwtResponseDto;
import by.smartym_pro.test_task.dto.UserDto;
import by.smartym_pro.test_task.entity.Role;
import by.smartym_pro.test_task.entity.User;
import by.smartym_pro.test_task.exception.IncorrectDataException;
import by.smartym_pro.test_task.security.jwt.JwtTokenUtil;
import by.smartym_pro.test_task.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
 * REST controller for authentication requests (login, logout, register, etc.).
 *
 * @author Semizhon Roman
 * @version 1.0
 */
@RequestMapping("/users")
@RestController
@CrossOrigin
@Api(value = "User controller.")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Logger LOGGER = LogManager.getLogger(UserController.class);


    @ApiOperation(value = "Gets user from the database by id.")
    @GetMapping
    public ResponseEntity getUser(@RequestParam("id") long id) {

        HttpHeaders headers = new HttpHeaders();
        User user = this.userService.findById(id);

        return new ResponseEntity<>(user, headers, HttpStatus.OK);
    }

    @ApiOperation(value = "Lets user login to his private cabinet.")
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

            return ResponseEntity.ok(new JwtResponseDto(token));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }


    @ApiOperation(value = "Lets user sign up on the site.")
    @PostMapping("/sign_up")
    public ResponseEntity addUser(@RequestBody UserDto userDto) {
        HttpHeaders headers = new HttpHeaders();
        User user = null;
        try {
            if (userDto == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            String hashedPassword
                    = passwordEncoder.encode(userDto.getPassword());
            List<Role> roles = new ArrayList<>();
            userDto.setPassword(hashedPassword);
            userDto.setRoles(roles);
            user = userDto.toUser();
            this.userService.addUser(user);
        } catch (IncorrectDataException e) {
            LOGGER.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), headers,
                    HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(user, headers, HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            LOGGER.error(e.getMessage());
        } catch (BadCredentialsException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
