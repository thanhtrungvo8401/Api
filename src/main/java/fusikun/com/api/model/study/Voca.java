package fusikun.com.api.model.study;

import java.util.Date;
import java.util.UUID;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "voca")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Voca {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    private String voca;
    private String meaning;
    private String note;
    private String sentence;
    private Long code;

    private Boolean isActive;
    private Date createdDate;
    private Date updatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "setId", nullable = false)
    private SetVoca setVoca;
}
