package by.smartym_pro.test_task.controller;

import by.smartym_pro.test_task.entity.News;
import by.smartym_pro.test_task.entity.User;
import by.smartym_pro.test_task.repository.UserRepository;
import com.sun.tools.internal.jxc.SchemaGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/users/sign_up")
    public ResponseEntity<User> addUser(
            @RequestBody User user) {
        HttpHeaders headers = new HttpHeaders();

        if(user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

//        String
        return new ResponseEntity<>(user, headers, HttpStatus.OK);
    }
}
