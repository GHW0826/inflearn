package ex1hello.hello.entity;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public class Item {
    @Id @GeneratedValue
    private Long id;

    private String name;
    private int price;

	/*
		@DiscriminatorColumn(name="DTYPE") 추가시
		private String dtype; 추가 된다고 보면됨.
		@DiscriminatorValue("a")는 저장될 타입의 네이밍
		-> dtype = a;
	*/
}
