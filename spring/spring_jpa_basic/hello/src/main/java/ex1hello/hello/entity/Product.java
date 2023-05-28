package ex1hello.hello.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Product {

    @Id @GeneratedValue
    private Long id;

    private String name;

    // ManyToMany -> OneToMany
    @OneToMany(mappedBy = "product")
    private List<MemberProduct> memberProducts = new ArrayList<>();
//    @ManyToMany(mappedBy = "products")
//    private List<Member2> members = new ArrayList<>();
}
