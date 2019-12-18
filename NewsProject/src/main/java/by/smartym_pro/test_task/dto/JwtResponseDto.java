package by.smartym_pro.test_task.dto;

/**
 * DTO class for authentication (username) response.
 *
 * @author Semizhon Roman
 * @version 1.0
 */
public class JwtResponseDto {
    private final String jwtToken;

    public JwtResponseDto(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getToken() {
        return this.jwtToken;
    }
}
