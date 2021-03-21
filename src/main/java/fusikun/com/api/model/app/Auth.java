package fusikun.com.api.model.app;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import fusikun.com.api.utils.ConstantErrorCodes;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "auth")
public class Auth {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    private Boolean isActive;

    private Date createdDate;

    private Date updatedDate;

    public Auth(Role role, Menu menu, Boolean isActive) {
        this.role = role;
        this.menu = menu;
        this.isActive = isActive;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull(message = ConstantErrorCodes.NOT_NULL)
    @JoinColumn(name = "roleId")
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @NotNull(message = ConstantErrorCodes.NOT_NULL)
    @JoinColumn(name = "menuId")
    private Menu menu;

}
