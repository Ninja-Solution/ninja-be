package ninja.be.dto.user.request;

import lombok.Builder;
import lombok.Data;
import ninja.be.entity.User;
import ninja.be.entity.embeddables.Location;
import ninja.be.entity.enums.Authority;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserSignupRequest {
    @NotBlank(message = "이메일을 입력하세요.")
    private String email;
    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;
    @NotBlank(message = "이름을 입력하세요.")
    private String username;
    @NotNull(message = "주소를 입력하세요.")
    private Location location;

    @Builder
    public UserSignupRequest(String email, String password, String username, Location location) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.location = location;
    }

    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .username(username)
                .authority(Authority.ROLE_USER)
                .location(location).build();
    }
}
