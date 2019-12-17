package by.smartym_pro.test_task.security.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

public class JwtUser implements UserDetails {

    private final long id;
    private final String name;
    private final String login;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtUser(long id, String name, String login,
                   String password,
                   Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

//    public Date getLastPasswordResetDate() {
//        return lastPasswordResetDate;
//    }
}
