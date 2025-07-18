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

    @NotBlank(message = "ユーザーIDは必須")
    private String username;

    @NotBlank(message = "パスワードは必須")
    @Length(min = 8 , max = 20)
    @Pattern(regexp = ".*[!@].*", message = "パスワードに特殊文字を含めてください")
    private String passwd;

    @NotBlank(message = "Eメール必須")
    @Email(message = "Eメールの形式に合わないです。")
    private String email;

    private String role = "1";
}
