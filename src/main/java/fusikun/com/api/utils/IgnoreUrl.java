package fusikun.com.api.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class IgnoreUrl {
	private static final String[] list = { "/authenticate/login<!>POST", "/students<!>POST", "/initial-project<!>GET" };

	public static final List<String> listUrl(Boolean hasMethod) {
		List<String> listUrlWithMethod = Arrays.asList(list);
		if (hasMethod)
			return listUrlWithMethod;
		else {
			return listUrlWithMethod.stream().map(el -> {
				String url = el.split(Constant.FILTER_DIVICE)[0];
				return url;
			}).collect(Collectors.toList());
		}
	}

	public static final List<String> listUrl = listUrl(false);
}
