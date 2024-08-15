package ridhopriambodo.buana.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    private String email;

    private String name;

    private String password;

    private String token;

    @Column(name = "token_expired_at")
    private Long tokenExpiredAt;

    public boolean isExpired() {
        return (this.tokenExpiredAt > System.currentTimeMillis());
    }

}
