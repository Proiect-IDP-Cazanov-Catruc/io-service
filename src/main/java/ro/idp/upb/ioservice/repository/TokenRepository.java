/* Ionel Catruc 343C3, Veaceslav Cazanov 343C3 | IDP IO-SERVICE | (C) 2024 */
package ro.idp.upb.ioservice.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.idp.upb.ioservice.data.entity.Token;
import ro.idp.upb.ioservice.data.enums.TokenType;

public interface TokenRepository extends JpaRepository<Token, UUID> {
	@Query("SELECT t FROM Token t where t.user.id = :id and (t.expired = false or t.revoked = false)")
	List<Token> findAllValidTokenByUser(UUID id);

	Optional<Token> findByTokenAndTokenType(String token, TokenType tokenType);

	List<Token> findByToken(String token);
}
