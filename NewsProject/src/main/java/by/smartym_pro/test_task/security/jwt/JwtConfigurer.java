package by.smartym_pro.test_task.security.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * JWT configuration for application that add {@link JwtTokenFilter} for security chain.
 *
 * @author Semizhon Roman
 * @version 1.0
 */

public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
//    private JwtTokenUtil jwtTokenUtil;
//
//    public JwtConfigurer(JwtTokenUtil jwtTokenUtil) {
//        this.jwtTokenUtil = jwtTokenUtil;
//    }
//
//    @Override
//    public void configure(HttpSecurity httpSecurity) throws Exception {
//        JwtTokenFilter jwtTokenFilter
//                = new JwtTokenFilter();
//        //Add a filter to validate the tokens with every request.
//        httpSecurity.addFilterBefore(jwtTokenFilter,
//                UsernamePasswordAuthenticationFilter.class);
//    }
}
