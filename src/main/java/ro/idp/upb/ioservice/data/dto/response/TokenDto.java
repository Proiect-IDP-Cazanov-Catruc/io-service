/* Ionel Catruc 343C3, Veaceslav Cazanov 343C3 | IDP IO-SERVICE | (C) 2024 */
package ro.idp.upb.ioservice.data.dto.response;

import java.util.UUID;
import lombok.*;
import ro.idp.upb.ioservice.data.enums.TokenType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDto {
	public UUID id;
	public String token;
	public TokenType tokenType;
	public boolean revoked;
	public boolean expired;
	public TokenDto associatedToken;
	public UUID userId;
}
