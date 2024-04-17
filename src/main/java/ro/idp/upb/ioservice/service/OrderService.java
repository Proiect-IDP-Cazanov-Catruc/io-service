/* Ionel Catruc 343C3, Veaceslav Cazanov 343C3 | IDP IO-SERVICE | (C) 2024 */
package ro.idp.upb.ioservice.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.idp.upb.ioservice.data.dto.request.OrderPostDto;
import ro.idp.upb.ioservice.data.dto.response.GetOrderDto;
import ro.idp.upb.ioservice.data.entity.Order;
import ro.idp.upb.ioservice.data.entity.Product;
import ro.idp.upb.ioservice.data.entity.User;
import ro.idp.upb.ioservice.exception.UsernameNotFoundException;
import ro.idp.upb.ioservice.repository.OrderRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

	private final UserService userService;
	private final OrderRepository orderRepository;
	private final ProductService productService;

	public GetOrderDto placeOrder(OrderPostDto dto) {
		log.info(
				"Placing order [UserId: {}], [Products: {}]...", dto.getUserId(), dto.getProductsIds());
		User user =
				userService
						.findUserById(dto.getUserId())
						.orElseThrow(
								() -> {
									log.error("User with id {} not found", dto.getUserId());
									return new UsernameNotFoundException("User not found");
								});

		Set<Product> productsSet = productService.getProductsByIdsList(dto.getProductsIds());
		log.info(
				"Fetched {} products for placing order for user {}!", productsSet.size(), user.getId());
		final Order order = Order.builder().user(user).products(productsSet).build();
		final Order savedOrder = orderRepository.save(order);
		log.info("Placed order for user {}!", user.getId());
		return buildGetOrderDto(savedOrder);
	}

	private GetOrderDto buildGetOrderDto(Order savedOrder) {
		return GetOrderDto.builder()
				.orderId(savedOrder.getId())
				.user(userService.userToDto(savedOrder.getUser()))
				.products(
						savedOrder.getProducts().stream().map(productService::productToProductGetDto).toList())
				.build();
	}

	public List<GetOrderDto> getOrders(UUID byUserId) {
		log.info("Get orders (Optional byUserId: {})...", byUserId);
		Set<Order> orders = orderRepository.getOrderByOptionalUserId(byUserId);
		log.info("Got {} orders (Optional byUserId: {})!", orders.size(), byUserId);
		return orders.stream().map(this::buildGetOrderDto).toList();
	}
}
