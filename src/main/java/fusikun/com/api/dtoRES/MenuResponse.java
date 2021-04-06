package fusikun.com.api.dtoRES;

import java.util.UUID;

import fusikun.com.api.model.app.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuResponse {
    private UUID id;
    private String url;
    private String name;
    private String regex;
    private String method;
    private UUID parentId;

    public MenuResponse(Menu menu) {
        id = menu.getId();
        url = menu.getUrl();
        name = menu.getName();
        regex = menu.getRegex();
        method = menu.getMethod();
        if (menu.getParentMenu() != null) {
            parentId = menu.getParentMenu().getId();
        }
    }
}
