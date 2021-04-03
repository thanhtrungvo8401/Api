//package fusikun.com.api.model.study;
//
//import java.util.Date;
//import java.util.List;
//import java.util.UUID;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.JoinTable;
//import javax.persistence.ManyToMany;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.hibernate.annotations.GenericGenerator;
//
//import fusikun.com.api.model.app.User;
//
//@Entity
//@Table(name = "room")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class Room {
//    @Id
//    @GeneratedValue(generator = "uuid2")
//    @GenericGenerator(name = "uuid2", strategy = "uuid2")
//    @Column(name = "id", columnDefinition = "BINARY(16)")
//    private UUID id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "centerId", nullable = true)
//    private Center center;
//
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "room_teachers", joinColumns = @JoinColumn(name = "roomId"), inverseJoinColumns = @JoinColumn(name = "teacherId"))
//    private List<User> teachers;
//
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "room_members", joinColumns = @JoinColumn(name = "roomId"), inverseJoinColumns = @JoinColumn(name = "memberId"))
//    private List<User> members;
//
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "room_setVocas", joinColumns = @JoinColumn(name = "roomId"), inverseJoinColumns = @JoinColumn(name = "setVocaId"))
//    private List<SetVoca> setVocas;
//
//    private Boolean isActive;
//    private Date createdDate;
//    private Date updatedDate;
//
//    private String roomName;
//}
