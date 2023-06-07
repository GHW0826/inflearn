package ex1hello.hello.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;

@Entity
@Table(name = "Member2")
@Getter @Setter
@NoArgsConstructor
public class Member2 extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    // Member가 Many, Team이 one
    @ManyToOne(fetch = FetchType.LAZY) //**
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

    // ManyToMany -> ManyToOne
    @OneToMany(mappedBy = "member")
    private List<MemberProduct> memberProducts = new ArrayList<>();
//    @ManyToMany
//    @JoinTable(name = "MEMBER_PRODUCT")
//    private List<Product> products = new ArrayList<>();


    // 연관관계 편의 메소드
    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }

//    @Column(name = "TEAM_ID")
//    private Long teamId;

    @Override
    public String toString() {
        return "Member2{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", team=" + team +
                '}';
    }

    // Embedded, Embeddable 둘중 하나만 써도 되지만 가시성을 위해 둘다 넣는거 권장
    @Embedded
    private Period period;

    // 같은 임베디드 타입을 여러번 사용시 컬럼 중복이 되서 오버라이드
    @Embedded
    @AttributeOverrides({ @AttributeOverride(name="startDate", column=@Column(name="work_start")),
                         @AttributeOverride(name="endDate", column=@Column(name="work_end")) })
    private Period workdPeriod;

    //->Period
    //    private LocalDateTime startDate;
    //    private LocalDateTime endDate;

    // Embedded, Embeddable 둘중 하나만 써도 되지만 가시성을 위해 둘다 넣는거 권장
    @Embedded
    private Address homeAddress;
    //-> Address
    //    private String city;
    //    private String street;
    //    private String zipcode;


    // 값 타입 컬렉션 (기본적으로 값 타입 컬렉션은 지연 로딩)
    @Embedded
    private Address homeAddress2;

    // 지연로딩 설정
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="FAVORITE_FOOD",
            joinColumns = @JoinColumn(name="MEMBER_ID")
    )
    @Column(name="FOOD_NAME")
    private Set<String> favoriteFoods = new HashSet<>();

    @ElementCollection
    @CollectionTable(name="ADDRESS",
        joinColumns = @JoinColumn(name="MEMBER_ID")
    )
    private List<Address> addressHistory = new ArrayList<>();

}