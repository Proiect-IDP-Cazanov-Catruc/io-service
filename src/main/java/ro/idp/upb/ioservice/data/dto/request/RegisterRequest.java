package ro.idp.upb.ioservice.data.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "First name should be provided")
    private String firstName;
    @NotBlank(message = "Last name should be provided")
    private String lastName;
    @NotBlank(message = "Email should be provided")
    private String email;
    @NotBlank(message = "Password should be provided")
    private String password;
}
