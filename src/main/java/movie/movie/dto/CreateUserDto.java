package movie.movie.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor  // 기본 생성자 추가
public class CreateUserDto {
    @NotBlank(message = "유저 아이디는 필수")
    private String username;
    @NotBlank(message = "패스워드는 필수")
    @Length(min = 8 , max = 20)
    @Pattern(regexp = ".*[!@].*", message = "비밀번호에 최소 하나의 ! 또는 @ 특수문자를 포함해야 합니다.")
    private String passwd;
    @NotBlank(message = "이메일 필수")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;
    private String role = "1";
}
