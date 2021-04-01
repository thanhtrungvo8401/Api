package fusikun.com.api.model.study;

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

import fusikun.com.api.model.app.User;

@Entity
@Table(name = "set_voca")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SetVoca {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    private String setName;
    private Integer maxVoca;
    private Integer totalVocas;
    private Boolean isActive;
    private Date createdDate;
    private Date updatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authorId", nullable = false)
    private User author;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "setVoca", cascade = CascadeType.REMOVE)
    private List<Voca> vocas;

//    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "setVocas")
//    private List<Room> rooms;

    public void decreaseVoca() {
        this.totalVocas--;
    }

    public void increaseVoca() {
        this.totalVocas++;
    }
}
