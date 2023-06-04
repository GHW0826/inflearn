package ex1hello.hello.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Child {
    @Id
    @GeneratedValue
    private Long id;

    private String username;

    @ManyToOne
    @JoinColumn(name ="parent_id")
    private Parent parent;
}
