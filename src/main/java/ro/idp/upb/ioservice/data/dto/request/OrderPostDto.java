/* Ionel Catruc 343C3, Veaceslav Cazanov 343C3 | IDP IO-SERVICE | (C) 2024 */
package ro.idp.upb.ioservice.data.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPostDto {
	@NotNull(message = "User id should be provided") private UUID userId;

	@NotNull(message = "Product ids list should be provided") @NotEmpty(message = "Product ids list should not be empty")
	private List<UUID> productsIds;
}
