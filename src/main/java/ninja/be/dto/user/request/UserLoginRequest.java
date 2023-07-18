package ninja.be.dto.user.request;

import lombok.Builder;
import lombok.Data;
import ninja.be.entity.User;
import ninja.be.entity.enums.Authority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginRequest {
    @NotBlank(message = "이메일을 입력하세요.")
    private String email;
    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;

    @Builder
    public UserLoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .authority(Authority.ROLE_USER)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
