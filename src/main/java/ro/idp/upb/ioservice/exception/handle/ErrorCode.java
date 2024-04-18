/* Ionel Catruc 343C3, Veaceslav Cazanov 343C3 | IDP IO-SERVICE | (C) 2024 */
package ro.idp.upb.ioservice.exception.handle;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorCode {
	E_001("ERROR_GLOBAL"),
	E_002("ERROR_INVALID_CREDENTIALS"),

	E_100("ERROR_USER_NOT_FOUND"),
	E_101("ERROR_USERNAME_ALREADY_EXISTS"),
	E_102("ERROR_CATEGORY_NOT_FOUND"),
	E_103("ERROR_PRODUCT_NOT_FOUND"),
	E_104("ERROR_NOT_REFRESH_TOKEN"),
	E_201("ERROR_FIELDS_VALIDATION");

	public final String error;
}
