package fusikun.com.api.utils;

import java.util.Random;

public class RandomTokenUtil {
	private static final String sources = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvwxyz"
			+ "!@#$%&";
	private static final Random rnd = new Random();
	public static String generateToken(int len) {
		StringBuilder token = new StringBuilder(len);
		while (len > 0) {
			token.append(sources.charAt(rnd.nextInt(sources.length())));
			len--;
		}
		return token.toString();
	}
}
