package ro.idp.upb.ioservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.idp.upb.ioservice.data.dto.response.CategoryGetDto;
import ro.idp.upb.ioservice.data.entity.Category;
import ro.idp.upb.ioservice.repository.CategoryRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category findCategoryByCategoryId(UUID categoryId) {
        return categoryRepository.findById(categoryId).orElseGet(() -> {
            log.error("Category {} not found!", categoryId);
            return null;
        });
    }

    public CategoryGetDto categoryToCategoryGetDto(final Category category) {
        return CategoryGetDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
