package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @OneToMany(mappedBy = "MEMBER_ID")
    private List<Order> orders = new ArrayList<>();

    @Column(length = 10)
    private String name;
    private String city;
    private String street;
    private String zeipcode;
}
