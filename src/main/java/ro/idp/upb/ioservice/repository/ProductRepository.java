/* Ionel Catruc 343C3, Veaceslav Cazanov 343C3 | IDP IO-SERVICE | (C) 2024 */
package ro.idp.upb.ioservice.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.idp.upb.ioservice.data.entity.Product;

public interface ProductRepository extends JpaRepository<Product, UUID> {}
