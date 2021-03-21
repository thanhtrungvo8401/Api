package fusikun.com.api.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.StringUtils;

public class SortHelper {
    public static Pageable getSort(String _limit, String _page, String _sortBy,
                                   String _order) {
        Integer limit = StringUtils.hasText(_limit) ? Integer.parseInt(_limit) : 10;
        Integer page = StringUtils.hasText(_page) ? Integer.parseInt(_page) : 0;
        String sortBy = StringUtils.hasText(_sortBy) ? _sortBy : "createdDate";
        Direction direction =
                StringUtils.hasText(_order) && _order.equals(Direction.ASC.toString())
                        ? Direction.ASC
                        : Direction.DESC;
        return PageRequest.of(page, limit, direction, sortBy);
    }
}