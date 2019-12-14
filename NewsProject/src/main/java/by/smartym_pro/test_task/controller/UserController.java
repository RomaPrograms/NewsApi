package by.smartym_pro.test_task.controller;

import by.smartym_pro.test_task.entity.User;
import by.smartym_pro.test_task.entity.UserType;
import by.smartym_pro.test_task.repository.UserRepository;
import jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@RequestMapping("/users")
@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/sign_up")
    public ResponseEntity<User> addUser(
            @RequestBody User user, HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        HttpSession session = request.getSession();

        if(user == null) {
            request.setAttribute("ERROR_MESSAGE",
                    "Not enough data for saving user.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(session.getAttribute("ROLE").equals(UserType.ADMIN)) {
            user.setType(UserType.ADMIN);
        }
        else if(session.getAttribute("ROLE").equals(UserType.USER)) {
            request.setAttribute("ERROR_MESSAGE",
                    "You can not do this because you signed up already.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            user.setType(UserType.USER);
        }

        String hashedPassword
                = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        user.setPassword(hashedPassword);
        this.userRepository.save(user);
        user.setPassword("");

        return new ResponseEntity<>(user, headers, HttpStatus.OK);
    }

    @PostMapping("/log_in")
    public ResponseEntity<User> findByPasswordAndLogin(
            @RequestBody User user, HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        HttpSession session = request.getSession();

         if(session.getAttribute("ROLE").equals(UserType.USER)) {
             request.setAttribute("ERROR_MESSAGE",
                     "You can not do this because you logged in already.");
             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
         }

        Optional<User> optionalUser
                = this.userRepository.findByLogin(user.getLogin());
        if(optionalUser.isPresent()
                && BCrypt.checkpw(user.getPassword(), optionalUser.get().getPassword())) {
            optionalUser.get().setPassword("");
            session.setAttribute("ROLE", user.getType());
            return new ResponseEntity<>(optionalUser.get(),
                    headers, HttpStatus.OK);
        }

        request.setAttribute("ERROR_MESSAGE",
                "Incorrect password or login.");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
