package ro.idp.upb.ioservice.data.dto.response;

import ro.idp.upb.ioservice.data.enums.TokenType;
import lombok.*;

import java.util.UUID;

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
