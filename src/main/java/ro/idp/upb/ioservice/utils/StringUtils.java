/* Ionel Catruc 343C3, Veaceslav Cazanov 343C3 | IDP IO-SERVICE | (C) 2024 */
package ro.idp.upb.ioservice.utils;

public class StringUtils {

	public static String truncateString(String str) {
		return str.substring(0, Math.min(15, str.length()));
	}
}
