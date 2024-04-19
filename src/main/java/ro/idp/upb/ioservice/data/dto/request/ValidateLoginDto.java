/* Ionel Catruc 343C3, Veaceslav Cazanov 343C3 | IDP IO-SERVICE | (C) 2024 */
package ro.idp.upb.ioservice.data.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidateLoginDto {
	@NotBlank(message = "Email should not be blank")
	@Email(message = "Provided string is not email")
	private String email;

	@NotBlank(message = "Password should not be blank")
	private String password;
}
