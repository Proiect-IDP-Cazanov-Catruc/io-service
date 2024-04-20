/* Ionel Catruc 343C3, Veaceslav Cazanov 343C3 | IDP IO-SERVICE | (C) 2024 */
package ro.idp.upb.ioservice.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.idp.upb.ioservice.data.dto.request.AddProductPost;
import ro.idp.upb.ioservice.data.dto.response.CategoryGetDto;
import ro.idp.upb.ioservice.data.dto.response.ProductGetDto;
import ro.idp.upb.ioservice.data.entity.Category;
import ro.idp.upb.ioservice.data.entity.Product;
import ro.idp.upb.ioservice.repository.ProductRepository;
import ro.idp.upb.ioservice.utils.StringUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
	private final ProductRepository productRepository;
	private final CategoryService categoryService;

	public ProductGetDto addProduct(AddProductPost dto) {
		log.info(
				"Adding product [Name: {}], [Description: {}], [Price: {}], [Quantity: {}], [CategoryId: {}]...",
				dto.getName(),
				StringUtils.truncateString(dto.getDescription()),
				dto.getPrice(),
				dto.getQuantity(),
				dto.getCategoryId());
		final Category category = categoryService.findCategoryByCategoryId(dto.getCategoryId());
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
				StringUtils.truncateString(dto.getDescription()),
				dto.getPrice(),
				dto.getQuantity(),
				dto.getCategoryId());
		return productGetDto;
	}

	public ProductGetDto productToProductGetDto(final Product product) {
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

	public Set<Product> getProductsByIdsList(List<UUID> productsIds) {
		return productRepository.getProductsByIdsInIdsList(productsIds);
	}
}
