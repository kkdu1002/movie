package movie.movie.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    // ユーザーID
    private String username;

    // パスワード
    private String passwd;

    // Eメール
    private String email;

    // 役割
    private String role;

    //　役割初期設定
    @PrePersist
    public void persistRole() {
        this.role = "1";
    }

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Reservation> reservationsList = new ArrayList<>();

    // パスワード変更
    public void updPasswd(String passwd) {
        if(StringUtils.hasText(passwd)) {
            this.passwd = passwd;
        }
    }

    // Eメール変更
    public void updEmail(String email) {
        if(StringUtils.hasText(email)) {
            this.email = email;
        }
    }

    // 役割変更
    public void updRole(String role) {
        if(StringUtils.hasText(role)) {
            this.role = role;
        }
    }
}
