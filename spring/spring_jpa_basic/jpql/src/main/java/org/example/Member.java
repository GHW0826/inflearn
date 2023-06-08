package org.example;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    private Long id;

    private String username;
    private int age;

    @OneToMany
    @JoinColumn(name="TEAM_ID")
    private Team team;
}
