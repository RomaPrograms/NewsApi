package by.smartym_pro.test_task.dto;

import by.smartym_pro.test_task.entity.Role;
import by.smartym_pro.test_task.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * DTO class for user requests by ROLE_USER
 *
 * @author Semizhon Roman
 * @version 1.0
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private String name;
    private String username;
    private String password;
    private List<Role> roles;

    public User toUser(){
        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setPassword(password);
        user.setRoles(roles);

        return user;
    }

    //TODO change name to fiirst name.
    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setRoles(user.getRoles());

        return userDto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
