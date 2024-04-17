/* Ionel Catruc 343C3, Veaceslav Cazanov 343C3 | IDP IO-SERVICE | (C) 2024 */
package ro.idp.upb.ioservice.repository;

import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.idp.upb.ioservice.data.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

	@Query("SELECT o FROM Order o WHERE (:userId IS NULL OR o.user.id = :userId)")
	Set<Order> getOrderByOptionalUserId(UUID userId);
}
