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

    // login id
    private String username;

    // password
    private String passwd;

    // email
    private String email;

    // role
    private String role;

    @PrePersist
    public void persistRole() {
        this.role = "1";
    }

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Reservation> reservationsList = new ArrayList<>();

    // 패스워드 변경
    public void updPasswd(String passwd) {
        if(StringUtils.hasText(passwd)) {
            this.passwd = passwd;
        }
    }

    // 이메일 변경
    public void updEmail(String email) {
        if(StringUtils.hasText(email)) {
            this.email = email;
        }
    }

    // 직위 변경
    public void updRole(String role) {
        if(StringUtils.hasText(role)) {
            this.role = role;
        }
    }
}
