/* Ionel Catruc 343C3, Veaceslav Cazanov 343C3 | IDP IO-SERVICE | (C) 2024 */
package ro.idp.upb.ioservice.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.idp.upb.ioservice.data.dto.request.PostTokensDto;
import ro.idp.upb.ioservice.data.dto.response.TokenDto;
import ro.idp.upb.ioservice.data.entity.Token;
import ro.idp.upb.ioservice.data.entity.User;
import ro.idp.upb.ioservice.data.enums.TokenType;
import ro.idp.upb.ioservice.exception.NotRefreshTokenException;
import ro.idp.upb.ioservice.exception.TokenNotFoundException;
import ro.idp.upb.ioservice.exception.UsernameNotFoundException;
import ro.idp.upb.ioservice.repository.TokenRepository;
import ro.idp.upb.ioservice.utils.StringUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {
	private final TokenRepository tokenRepository;
	private final UserService userService;

	public TokenDto findToken(String token, TokenType tokenType) {
		log.info("Find token {} with token type {}...", StringUtils.truncateString(token), tokenType);
		Optional<Token> tokenOptional = tokenRepository.findByTokenAndTokenType(token, tokenType);
		if (tokenOptional.isEmpty()) {
			log.error(
					"Unable to find token {} with token type {}!",
					StringUtils.truncateString(token),
					tokenType);
			throw new TokenNotFoundException(token, tokenType);
		} else {
			Token tokenEntity = tokenOptional.get();
			Token associatedTokenEntity = tokenEntity.getAssociatedToken();
			TokenDto tokenDto =
					TokenDto.builder()
							.id(tokenEntity.getId())
							.token(tokenEntity.getToken())
							.tokenType(tokenEntity.getTokenType())
							.revoked(tokenEntity.getRevoked())
							.expired(tokenEntity.getExpired())
							.userId(tokenEntity.getUser().getId())
							.build();
			TokenDto associatedTokenDto =
					TokenDto.builder()
							.id(associatedTokenEntity.getId())
							.token(associatedTokenEntity.getToken())
							.tokenType(associatedTokenEntity.getTokenType())
							.revoked(associatedTokenEntity.getRevoked())
							.expired(associatedTokenEntity.getExpired())
							.userId(associatedTokenEntity.getUser().getId())
							.build();

			tokenDto.setAssociatedToken(associatedTokenDto);
			log.info(
					"Found token {} and token type {}, returning!",
					StringUtils.truncateString(token),
					tokenType);

			return tokenDto;
		}
	}

	public void logoutToken(String token) {
		log.info("Trying to revoke(logout) token {}...", StringUtils.truncateString(token));
		Token storedToken =
				tokenRepository.findByTokenAndTokenType(token, TokenType.ACCESS_TOKEN).orElse(null);
		if (storedToken != null) {
			log.info(
					"Token {} found, revoking it and associated token...", StringUtils.truncateString(token));
			storedToken.setExpired(true);
			storedToken.setRevoked(true);
			tokenRepository.save(storedToken);
			storedToken.getAssociatedToken().setExpired(true);
			storedToken.getAssociatedToken().setRevoked(true);
			tokenRepository.save(storedToken.getAssociatedToken());
		}
	}

	public void saveUserTokens(PostTokensDto tokens) {
		log.info(
				"Trying to save access token {} and refresh token {} for user {}...",
				StringUtils.truncateString(tokens.getAccessToken()),
				StringUtils.truncateString(tokens.getRefreshToken()),
				tokens.getUserId());
		User user =
				userService
						.findUserById(tokens.getUserId())
						.orElseThrow(
								() -> {
									log.error("Username with id {} not found", tokens.getUserId());
									return new UsernameNotFoundException(tokens.getUserId());
								});
		var accessTokenEntity =
				Token.builder()
						.user(user)
						.token(tokens.getAccessToken())
						.tokenType(TokenType.ACCESS_TOKEN)
						.expired(false)
						.revoked(false)
						.build();
		var refreshTokenEntity =
				Token.builder()
						.user(user)
						.token(tokens.getRefreshToken())
						.associatedToken(accessTokenEntity)
						.tokenType(TokenType.REFRESH_TOKEN)
						.expired(false)
						.revoked(false)
						.build();
		accessTokenEntity.setAssociatedToken(refreshTokenEntity);
		tokenRepository.save(accessTokenEntity);
		log.info(
				"Saved access token {} and refresh token {} for user {}!",
				StringUtils.truncateString(tokens.getAccessToken()),
				StringUtils.truncateString(tokens.getRefreshToken()),
				tokens.getUserId());
	}

	public void revokeToken(String token) {
		log.info("Revoking token {}...", StringUtils.truncateString(token));
		List<Token> tokens = tokenRepository.findByToken(token);
		tokens.forEach(
				dbToken -> {
					dbToken.setExpired(true);
					dbToken.setRevoked(true);
					tokenRepository.save(dbToken);
					dbToken.getAssociatedToken().setExpired(true);
					dbToken.getAssociatedToken().setRevoked(true);
					tokenRepository.save(dbToken.getAssociatedToken());
				});
	}

	public Boolean isRefreshToken(String token) {
		log.info(
				"Verifying if token {} is actually refresh token...", StringUtils.truncateString(token));
		Token storedToken =
				tokenRepository.findByTokenAndTokenType(token, TokenType.REFRESH_TOKEN).orElse(null);
		if (storedToken == null) {
			log.error("Provided token {} is not a refresh token!", StringUtils.truncateString(token));
			throw new NotRefreshTokenException(token);
		} else {
			log.info(
					"Token {} is actually refresh token! Returning true!", StringUtils.truncateString(token));
			return Boolean.TRUE;
		}
	}
}
