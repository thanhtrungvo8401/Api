package fusikun.com.api.dto;

import java.util.List;

public class ObjectsManagementList<T> {
    List<T> list;
    Long total;

    public ObjectsManagementList() {
    }

    public ObjectsManagementList(List<T> list, Long total) {
        this.list = list;
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
