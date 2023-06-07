package jpabook.jpashop.domain;

import javax.persistence.*;

@Entity
public class Delivery extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    @Embedded
    private Address address;
    // -> 값 타입으로 수정
    //private String city;
    //private String street;
    //private String zeipcode;
    
    private DeliveryStatus status;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;
}
