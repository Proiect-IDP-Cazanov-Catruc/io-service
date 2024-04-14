package ro.idp.upb.ioservice.data.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostTokensDto {
    @NotNull(message = "User id should be provided")
    private UUID userId;
    @NotBlank(message = "Access token should be provided")
    private String accessToken;
    @NotBlank(message = "Refresh token should be provided")
    private String refreshToken;
}
