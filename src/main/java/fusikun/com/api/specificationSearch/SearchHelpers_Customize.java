package fusikun.com.api.specificationSearch;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fusikun.com.api.exceptionHandlers.Ex_InvalidSearch;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchHelpers_Customize<T extends Specification_Customize> {
    Map<String, String> filters;
    T specification;

    public SearchHelpers_Customize(T specification, String _filters) {
        Map<String, String> filterMap;
        try {
            filterMap = new Gson().fromJson(
                    _filters,
                    new TypeToken<Map<String, String>>() {
                    }.getType());
        } catch (Exception e) {
            throw new Ex_InvalidSearch("Filters = [" + _filters + "] is " +
                    "invalid " + "filter!");
        }
        this.filters = filterMap != null ? filterMap : new HashMap<>();
        this.specification = specification;
    }

    public T getSpecification(List<String> fields) {
        fields.forEach(field -> {
            String filter = filters.get(field);
            if (StringUtils.hasText(filter)) {
                _SearchCriteria searchCriteria = new SearchHelpers(field, filter)
                        .getSearchCriteria();
                specification.add(searchCriteria);
            }
        });
        return specification;
    }
}
