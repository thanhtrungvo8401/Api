package fusikun.com.api.model.study;

import fusikun.com.api.model.app.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "test_group")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestGroup {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    private Integer number;

    private String myVoca;
    private String n5;
    private String n4;
    private String n3;
    private String n2;
    private String n1;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ownerId", nullable = false)
    private User owner;

    private Boolean isActive;
    private Date createdDate;
    private Date updatedDate;
}
