package fusikun.com.api.dtoRES;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ObjectsManagementList<T> {
    List<T> list;
    Long total;
}
