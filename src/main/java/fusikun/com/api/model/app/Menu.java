package fusikun.com.api.model.app;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "menu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Menu {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    private Boolean isActive;

    private String url;

    private String regex;

    private String name;

    private String method;

    private Date createdDate;

    private Date updatedDate;

    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Auth> auths;

    @OneToMany(mappedBy = "parentMenu", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Menu> menus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parentId")
    private Menu parentMenu;

    public Menu(String url, String name, String regex, String method) {
        super();
        this.url = url;
        this.name = name;
        this.regex = regex;
        this.method = method;
    }

    @Override
    public String toString() {
        return "Menu [id=" + id + ", isActive=" + isActive + ", url=" + url + ", regex=" + regex + ", name=" + name
                + ", method=" + method + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", auths="
                + auths + ", menus=" + menus + ", parentMenu=" + parentMenu + "]";
    }
}
