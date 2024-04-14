package ro.idp.upb.ioservice.data.dto.response;

import ro.idp.upb.ioservice.data.enums.Role;
import lombok.*;

import java.util.UUID;

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
