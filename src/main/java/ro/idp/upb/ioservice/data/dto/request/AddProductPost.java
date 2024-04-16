package ro.idp.upb.ioservice.data.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddProductPost {

    @NotBlank(message = "Product name should not be blank")
    private String name;
    @NotBlank(message = "Product description should not be blank")
    private String description;
    @NotNull(message = "Product price should be provided")
    private Double price;
    @NotNull(message = "Product quantity should be provided")
    private Integer quantity;
    @NotNull(message = "Product category id should be provided")
    private UUID categoryId;

}
