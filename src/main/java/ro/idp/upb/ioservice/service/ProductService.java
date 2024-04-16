/* Ionel Catruc 343C3, Veaceslav Cazanov 343C3 | IDP IO-SERVICE | (C) 2024 */
package ro.idp.upb.ioservice.service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ProductService {
	private final ProductRepository productRepository;
	private final CategoryService categoryService;

	public ResponseEntity<?> addProduct(AddProductPost dto) {
		log.info(
				"Adding product [Name: {}], [Description: {}], [Price: {}], [Quantity: {}], [CategoryId: {}]...",
				dto.getName(),
				dto.getDescription().substring(0, 15),
				dto.getPrice(),
				dto.getQuantity(),
				dto.getCategoryId());
		final Category category = categoryService.findCategoryByCategoryId(dto.getCategoryId());
		if (category == null) {
			log.error("Add product. CategoryId {} not found!", dto.getCategoryId());
			return ResponseEntity.notFound().build();
		}
		final Product product =
				Product.builder()
						.name(dto.getName())
						.description(dto.getDescription())
						.quantity(dto.getQuantity())
						.price(dto.getPrice())
						.category(category)
						.build();
		final Product savedProduct = productRepository.save(product);
		final ProductGetDto productGetDto = productToProductGetDto(savedProduct);
		log.info(
				"Added product [Name: {}], [Description: {}], [Price: {}], [Quantity: {}], [CategoryId: {}]",
				dto.getName(),
				dto.getDescription().substring(0, 15),
				dto.getPrice(),
				dto.getQuantity(),
				dto.getCategoryId());
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

	public List<ProductGetDto> getProducts(UUID categoryId) {
		log.info("Get products (Optional categoryId: {})...", categoryId);
		List<Product> products = productRepository.findByOptionalCategoryId(categoryId);
		log.info("Got {} products (Optional categoryId: {})!", products.size(), categoryId);
		return products.stream().map(this::productToProductGetDto).toList();
	}
}
