/* Ionel Catruc 343C3, Veaceslav Cazanov 343C3 | IDP IO-SERVICE | (C) 2024 */
package ro.idp.upb.ioservice.exception.handle;

import java.util.Optional;
import java.util.UUID;

public class ProductNotFoundException extends RuntimeException {
	private final Optional<UUID> productId;

	public ProductNotFoundException(UUID productId) {
		super();
		this.productId = Optional.ofNullable(productId);
	}

	public ProductNotFoundException() {
		super();
		this.productId = Optional.empty();
	}

	@Override
	public String getMessage() {
		if (productId.isPresent()) {
			return String.format("Product with id %s not found", productId.get());
		}
		return "Product not found";
	}
}
