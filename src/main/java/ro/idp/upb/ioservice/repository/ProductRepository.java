/* Ionel Catruc 343C3, Veaceslav Cazanov 343C3 | IDP IO-SERVICE | (C) 2024 */
package ro.idp.upb.ioservice.repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.idp.upb.ioservice.data.entity.Product;

public interface ProductRepository extends JpaRepository<Product, UUID> {

	@Query("SELECT p FROM Product p WHERE (:categoryId IS NULL OR p.category.id = :categoryId)")
	List<Product> findByOptionalCategoryId(UUID categoryId);

	@Query("SELECT p FROM Product p WHERE p.id in :productsIds")
	Set<Product> getProductsByIdsInIdsList(List<UUID> productsIds);
}
