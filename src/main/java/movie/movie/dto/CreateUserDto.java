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
@NoArgsConstructor
public class CreateUserDto {

    @NotBlank(message = "ユーザーIDは必須")
    private String username;

    @NotBlank(message = "パスワードは必須")
    @Length(min = 8 , max = 20)
    @Pattern(regexp = ".*[!@].*", message = "パスワードの入力に誤りがあります。")
    private String passwd;

    @NotBlank(message = "Eメール必須")
    @Email(message = "入力されたEメールの形式に誤りがあります。")
    private String email;

    private String role = "1";
}
