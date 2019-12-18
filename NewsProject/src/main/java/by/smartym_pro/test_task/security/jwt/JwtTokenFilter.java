package by.smartym_pro.test_task.security.jwt;

import by.smartym_pro.test_task.security.JwtUserDetailsService;
import by.smartym_pro.test_task.service.UserServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT token filter that handles all HTTP requests to application.
 *
 * @author Semizhon Roman
 * @version 1.0
 */
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    private final Logger LOGGER = LogManager.getLogger(JwtTokenFilter.class);

    /**
     * Checks if request has a token. If yes then extract the token.
     *
     * @param req         - http request
     * @param res         - http response
     * @param filterChain - chain of filters
     * @throws IOException      - exception connected with files
     * @throws ServletException - exception connected with servlet
     */
    @Override
    public void doFilterInternal(HttpServletRequest req,
                                 HttpServletResponse res,
                                 FilterChain filterChain)
            throws IOException, ServletException {

        String token = jwtTokenUtil.resolveToken(req);
        String username = null;

        try {
            if (token != null) {
                username = jwtTokenUtil.getUsernameFromToken(token);
            }

            if (username != null
                    && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.jwtUserDetailsService
                        .loadUserByUsername(username);
                if (jwtTokenUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken auth
                            = jwtTokenUtil.getAuthentication(token);
                    if (auth != null) {
                        auth.setDetails(new WebAuthenticationDetailsSource());
                        SecurityContextHolder
                                .getContext()
                                .setAuthentication(auth);
                    }
                }
            }
        } catch (ExpiredJwtException e) {
            LOGGER.error("Jwt was expired because of the time.");
        }

        filterChain.doFilter(req, res);
    }
}
