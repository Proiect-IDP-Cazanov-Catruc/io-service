/* Ionel Catruc 343C3, Veaceslav Cazanov 343C3 | IDP IO-SERVICE | (C) 2024 */
package ro.idp.upb.ioservice.data.dto.response;

import java.util.UUID;
import lombok.*;
import ro.idp.upb.ioservice.data.enums.Role;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUserDto {
	private UUID id;
	private String firstName;
	private String lastName;
	private String email;
	private Role role;
}
