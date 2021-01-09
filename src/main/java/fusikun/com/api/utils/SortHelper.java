package fusikun.com.api.utils;

import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.StringUtils;

public class SortHelper {
	public static Pageable getSort(Map<String, String> sorts) {
		Integer limit = (StringUtils.hasText(sorts.get("limit"))) ? Integer.parseInt(sorts.get("limit")) : 10;
		Integer page = (StringUtils.hasText(sorts.get("page"))) ? Integer.parseInt(sorts.get("page")) : 0;
		String sortBy = (StringUtils.hasText(sorts.get("sortBy"))) ? sorts.get("sortBy") : "id";
		Direction direction = (sorts.get("order") == "DESC") ? Direction.DESC : Direction.ASC;
		return PageRequest.of(page, limit, direction, sortBy);
	}
}
