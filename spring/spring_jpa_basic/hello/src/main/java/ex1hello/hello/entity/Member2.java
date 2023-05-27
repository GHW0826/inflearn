package ex1hello.hello.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Member2")
@Getter @Setter
@NoArgsConstructor
public class Member2 {
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    // Member가 Many, Team이 one
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;



//    @Column(name = "TEAM_ID")
//    private Long teamId;
}