package fusikun.com.api.model.study;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import fusikun.com.api.model.app.User;

@Entity
@Table(name = "center")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Center {

    public Center(UUID id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "center")
    private List<User> members;

    private String centerName;

    private Boolean isActive;

    private Date createdDate;

    private Date updatedDate;
}
