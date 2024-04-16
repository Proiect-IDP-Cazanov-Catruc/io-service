/* Ionel Catruc 343C3, Veaceslav Cazanov 343C3 | IDP IO-SERVICE | (C) 2024 */
package ro.idp.upb.ioservice.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.idp.upb.ioservice.data.dto.request.AddProductPost;
import ro.idp.upb.ioservice.data.dto.response.ProductGetDto;
import ro.idp.upb.ioservice.service.ProductService;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@PostMapping
	public ResponseEntity<?> addProduct(@RequestBody @Valid AddProductPost dto) {
		return productService.addProduct(dto);
	}

	@GetMapping
	public List<ProductGetDto> getProducts(@RequestParam(required = false) UUID categoryId) {
		return productService.getProducts(categoryId);
	}
}
