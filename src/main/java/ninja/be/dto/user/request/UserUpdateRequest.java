package ninja.be.dto.user.request;

import lombok.Data;
import ninja.be.entity.embeddables.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Data
public class UserUpdateRequest {
    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;
    @NotBlank(message = "이름을 입력하세요.")
    private String username;
    @NotNull(message = "주소를 입력하세요.")
    private Location location;
}
