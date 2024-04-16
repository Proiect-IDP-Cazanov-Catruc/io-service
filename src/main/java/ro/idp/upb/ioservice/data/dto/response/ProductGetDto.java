/* Ionel Catruc 343C3, Veaceslav Cazanov 343C3 | IDP IO-SERVICE | (C) 2024 */
package ro.idp.upb.ioservice.data.dto.response;

import java.util.UUID;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductGetDto {
	private UUID id;
	private String name;
	private String description;
	private Double price;
	private Integer quantity;
	private CategoryGetDto category;
}
