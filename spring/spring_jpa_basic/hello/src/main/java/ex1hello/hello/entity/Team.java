package ex1hello.hello.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Team {

    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    // 양방향 처리
    @OneToMany(mappedBy = "team")
    private List<Member2> members = new ArrayList<>();

    // 양쪽에 만들지 말자 걍 예시
    public void addMember(Member2 member) {
        member.setTeam(this);
        members.add(member);
    }

    // member와 toString 서로 무한 루프
    //    @Override
    //    public String toString() {
    //        return "Team{" +
    //                "id=" + id +
    //                ", name='" + name + '\'' +
    //                ", members=" + members +
    //                '}';
    //    }
}
