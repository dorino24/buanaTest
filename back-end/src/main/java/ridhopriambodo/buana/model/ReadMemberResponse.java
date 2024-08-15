package ridhopriambodo.buana.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReadMemberResponse
{

    private Integer id;

    private String name;

    private String position;

    private String reportTo;

    private String picture;

}
