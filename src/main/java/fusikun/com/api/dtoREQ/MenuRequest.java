package fusikun.com.api.dtoREQ;

import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import fusikun.com.api.model.app.Menu;
import fusikun.com.api.utils.ConstantErrorCodes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuRequest {
    private UUID id;

    @NotNull(message = ConstantErrorCodes.NOT_NULL)
    @NotEmpty(message = ConstantErrorCodes.NOT_EMPTY)
    @Size(max = 50, message = ConstantErrorCodes.NOT_OVER_50_LETTER)
    private String url;

    @NotNull(message = ConstantErrorCodes.NOT_NULL)
    @NotEmpty(message = ConstantErrorCodes.NOT_EMPTY)
    @Size(max = 50, message = ConstantErrorCodes.NOT_OVER_50_LETTER)
    private String name;

    @NotNull(message = ConstantErrorCodes.NOT_NULL)
    @NotEmpty(message = ConstantErrorCodes.NOT_EMPTY)
    @Size(max = 50, message = ConstantErrorCodes.NOT_OVER_50_LETTER)
    private String regex;

    @NotNull(message = ConstantErrorCodes.NOT_NULL)
    @Size(max = 8, message = ConstantErrorCodes.NOT_OVER_8_LETTER)
    private String method;

    private UUID parentId;

    public Menu getMenu() {
        Menu menu = new Menu();
        menu.setId(this.id);
        menu.setUrl(this.url);
        menu.setName(this.name);
        menu.setRegex(this.regex);
        menu.setMethod(this.method);
        if (this.parentId != null) {
            Menu parentMenu = new Menu();
            parentMenu.setId(this.parentId);
            menu.setParentMenu(parentMenu);
        }
        return menu;
    }
}
