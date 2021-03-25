package fusikun.com.api.utils;

import java.util.regex.Pattern;

public class IsValidRegex {
    public static boolean vocaCodes(String data) {
        String patternStr = "^[1-9]{1}[0-9,]{1,}[0-9]{1}$";
        Pattern pattern = Pattern.compile(patternStr);
        boolean isValidRegex = pattern.matcher(data).matches();
        return isValidRegex && !data.contains(",,");
    }
}
