/* Ionel Catruc 343C3, Veaceslav Cazanov 343C3 | IDP IO-SERVICE | (C) 2024 */
package ro.idp.upb.ioservice.data.entity;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;
import ro.idp.upb.ioservice.data.enums.TokenType;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tokens")
public class Token {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(unique = true, length = 1024, nullable = false)
	private String token;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TokenType tokenType;

	@Column(nullable = false)
	private Boolean revoked = false;

	@Column(nullable = false)
	private Boolean expired = false;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "associated_token_id", referencedColumnName = "id")
	private Token associatedToken;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}
