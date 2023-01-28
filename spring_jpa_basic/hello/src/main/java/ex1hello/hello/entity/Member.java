package ex1hello.hello.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Member {
    @Id
    private Long id;
    private String name;
    //Getter, Setter …
    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}