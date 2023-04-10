package jpabook.jpashop.domain.order;

import jakarta.persistence.*;
import jpabook.jpashop.domain.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Order(Member member, Delivery delivery, OrderStatus orderStatus, LocalDateTime now) {
        this.member = member;
        this.delivery = delivery;
        this.status = orderStatus;
        this.orderDate = now;
    }

    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItemWithConstructor(OrderItem orderItem) {
        orderItems.add(new OrderItem(this, orderItem.getItem(), orderItem.getOrderPrice(), orderItem.getCount()));
    }

    public void addOrderItemWithSetter(OrderItem orderItem) {
        orderItem = new OrderItem(this, orderItem.getItem(), orderItem.getOrderPrice(), orderItem.getCount());
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void addOrderItemWithBuilder(OrderItem orderItem) {
        orderItem = OrderItem.builder().order(this).build();
        orderItems.add(orderItem);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = new Delivery(this, delivery.getAddress(), delivery.getStatus());
    }

    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order(member, delivery, OrderStatus.ORDER, LocalDateTime.now());
        for (OrderItem orderItem : orderItems) {
            order.addOrderItemWithConstructor(orderItem);
        }

        return order;
    }

    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem: orderItems) {
            orderItem.cancel();
        }
    }

    public int getTotalPrice() {
        return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
    }

    private void setStatus(OrderStatus status) {
        this.status = status;
    }

}
