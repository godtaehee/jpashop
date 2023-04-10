package jpabook.jpashop.domain.order;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.OrderItem;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@Rollback(value = false)
//@Rollback
class OrderTest {

    @PersistenceContext
    private EntityManager em;

    @Test
    void methodTest() {
        Member member = new Member("Member A");
        Order order = new Order();
        OrderItem orderItem = new OrderItem();

        order
                .setMember(member);
        order.addOrderItem(orderItem);

        em.persist(member);
        em.persist(order);

        System.out.println("여기?");
        em.flush();
        em.clear();

        System.out.println("ㄴㄴ?");

        Member foundMember = em.find(Member.class, member.getId());

        System.out.println("foundMember.getOrders() = " + foundMember.getOrders());

    }

}