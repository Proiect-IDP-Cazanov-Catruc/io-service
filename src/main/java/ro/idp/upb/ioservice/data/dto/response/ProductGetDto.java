package ro.idp.upb.ioservice.data.dto.response;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import ro.idp.upb.ioservice.data.entity.Category;

import java.util.UUID;


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
