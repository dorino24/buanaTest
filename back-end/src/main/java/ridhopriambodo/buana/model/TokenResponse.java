package ridhopriambodo.buana.model;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenResponse {

    private String token;

    private Long expiredAt;

}
