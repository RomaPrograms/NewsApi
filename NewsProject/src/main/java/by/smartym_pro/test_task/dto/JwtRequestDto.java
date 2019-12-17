package by.smartym_pro.test_task.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * DTO class for authentication (username) request.
 *
 * @author Semizhon Roman
 * @version 1.0
 */

public class JwtRequestDto {

    @Autowired
    private PasswordEncoder bcryptEncoder;

    private String username;
    private String password;

    public JwtRequestDto(){}

    public JwtRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "JwtRequestDto{" +
                "bcryptEncoder=" + bcryptEncoder +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
