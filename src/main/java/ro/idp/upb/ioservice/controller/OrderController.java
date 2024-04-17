/* Ionel Catruc 343C3, Veaceslav Cazanov 343C3 | IDP IO-SERVICE | (C) 2024 */
package ro.idp.upb.ioservice.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.idp.upb.ioservice.data.dto.request.OrderPostDto;
import ro.idp.upb.ioservice.data.dto.response.GetOrderDto;
import ro.idp.upb.ioservice.service.OrderService;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping
	public GetOrderDto placeOrder(@RequestBody @Valid OrderPostDto dto) {
		return orderService.placeOrder(dto);
	}

	@GetMapping
	public List<GetOrderDto> getOrders(@RequestParam(required = false) UUID byUserId) {
		return orderService.getOrders(byUserId);
	}
}
