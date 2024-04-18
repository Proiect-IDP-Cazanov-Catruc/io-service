/* Ionel Catruc 343C3, Veaceslav Cazanov 343C3 | IDP IO-SERVICE | (C) 2024 */
package ro.idp.upb.ioservice.exception;

import java.util.Optional;
import java.util.UUID;

public class CategoryNotFoundException extends RuntimeException {
	private final Optional<UUID> categoryId;

	public CategoryNotFoundException(UUID categoryId) {
		super();
		this.categoryId = Optional.ofNullable(categoryId);
	}

	public CategoryNotFoundException() {
		super();
		this.categoryId = Optional.empty();
	}

	@Override
	public String getMessage() {
		if (categoryId.isPresent()) {
			return String.format("Category with id %s not found", categoryId.get());
		}
		return "Category not found";
	}
}
