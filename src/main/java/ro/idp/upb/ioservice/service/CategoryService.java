/* Ionel Catruc 343C3, Veaceslav Cazanov 343C3 | IDP IO-SERVICE | (C) 2024 */
package ro.idp.upb.ioservice.service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.idp.upb.ioservice.data.dto.response.CategoryGetDto;
import ro.idp.upb.ioservice.data.entity.Category;
import ro.idp.upb.ioservice.exception.CategoryNotFoundException;
import ro.idp.upb.ioservice.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
	private final CategoryRepository categoryRepository;

	public Category findCategoryByCategoryId(UUID categoryId) {
		log.info("Find category by ID {}!", categoryId);
		return categoryRepository
				.findById(categoryId)
				.orElseThrow(
						() -> {
							log.error("Category {} not found!", categoryId);
							return new CategoryNotFoundException(categoryId);
						});
	}

	public CategoryGetDto categoryToCategoryGetDto(final Category category) {
		return CategoryGetDto.builder().id(category.getId()).name(category.getName()).build();
	}

	public List<CategoryGetDto> getAllCategories() {
		log.info("Get all categories!");
		return categoryRepository.findAll().stream().map(this::categoryToCategoryGetDto).toList();
	}
}
