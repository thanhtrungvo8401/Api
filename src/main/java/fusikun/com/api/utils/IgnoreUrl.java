package fusikun.com.api.utils;

import java.util.Arrays;
import java.util.List;

public class IgnoreUrl {
	private static final String[] list = {"/authenticate/login"};
	public static final List<String> listUrl = Arrays.asList(list);
}
