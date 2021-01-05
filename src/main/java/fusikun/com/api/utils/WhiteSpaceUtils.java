package fusikun.com.api.utils;

public class WhiteSpaceUtils {
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

	public static final boolean isContainSpace(String str) {
		return str.indexOf(" ") > 0;
	}
}
