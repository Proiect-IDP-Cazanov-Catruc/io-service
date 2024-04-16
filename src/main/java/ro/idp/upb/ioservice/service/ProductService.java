package ro.idp.upb.ioservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ro.idp.upb.ioservice.data.dto.request.AddProductPost;
import ro.idp.upb.ioservice.data.dto.response.CategoryGetDto;
import ro.idp.upb.ioservice.data.dto.response.ProductGetDto;
import ro.idp.upb.ioservice.data.entity.Category;
import ro.idp.upb.ioservice.data.entity.Product;
import ro.idp.upb.ioservice.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public ResponseEntity<?> addProduct(AddProductPost dto) {
        final Category category = categoryService.findCategoryByCategoryId(dto.getCategoryId());
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        final Product product = Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .quantity(dto.getQuantity())
                .price(dto.getPrice())
                .category(category)
                .build();
        final Product savedProduct = productRepository.save(product);
        final ProductGetDto productGetDto = productToProductGetDto(savedProduct);
        return ResponseEntity.ok(productGetDto);
    }

    private ProductGetDto productToProductGetDto(final Product product) {
        CategoryGetDto categoryGetDto = categoryService.categoryToCategoryGetDto(product.getCategory());
        return ProductGetDto.builder()
                .category(categoryGetDto)
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .build();
    }
}
