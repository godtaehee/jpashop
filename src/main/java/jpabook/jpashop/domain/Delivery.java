package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.order.Order;
import lombok.Getter;

@Entity
@Getter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    public Delivery(Order order, Address address, DeliveryStatus status) {
        this.order = order;
        this.address = address;
        this.status = status;
    }

    public Delivery(Address address) {
        this.address = address;
    }
}
