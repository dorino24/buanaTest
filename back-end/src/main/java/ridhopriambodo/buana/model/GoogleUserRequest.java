package ridhopriambodo.buana.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoogleUserRequest {
    @NotBlank
    @Size(max = 100)
    @Email
    private String email;

    @NotBlank
    @Size(max = 100)
    private String name;

}
