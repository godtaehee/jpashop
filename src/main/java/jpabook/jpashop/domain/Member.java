package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.order.Order;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public Member(String name) {
        this.name = name;
    }

    public Member() {
    }

    public Member(String name, Address address) {
        this.name = name;
        this.address = address;
    }
}
