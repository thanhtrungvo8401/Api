package fusikun.com.api.utils;

public class TrymWhiteSpace {
	public static final String trymWhiteSpace(String str) {
		StringBuilder strB = new StringBuilder(str.trim());
		for (int i = 0; i < strB.length() - 1; i++) {
			if (strB.charAt(i) == strB.charAt(i + 1) && strB.charAt(i) == ' ') {
				while (strB.charAt(i) == strB.charAt(i + 1) && strB.charAt(i) == ' ') {
					strB.deleteCharAt(i);
				}
			}
		}
		return strB.toString();
	}
}
