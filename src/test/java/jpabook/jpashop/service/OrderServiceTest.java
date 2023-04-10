package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.order.Order;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderRepository orderRepository;


    @Autowired
    private OrderService orderService;

    private static Book createBook(String name, int price, int quantity) {
        return new Book(name, price, quantity);
    }

    private static Member createMember(String name, Address address) {
        return new Member(name, address);
    }

    @Test
    void 상품주문() {

        Member member = createMember("회원 1", new Address("서울", "경기", "123-123"));

        memberRepository.save(member);

        Book book = createBook("시골 JPA", 10000, 10);

        itemRepository.save(book);

        int orderCount = 10;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        Order getOrder = orderRepository.findOne(orderId);

        assertEquals( OrderStatus.ORDER, getOrder.getStatus());
        assertEquals(1, getOrder.getOrderItems().size());
        assertEquals(10000 * orderCount, getOrder.getTotalPrice());
        assertEquals(0, book.getStockQuantity());

    }



    @Test
    void 상품주문_재고수량초과() {

        Member member = createMember("evan", new Address("sunchang", "gwang-am", "1232323"));

        memberRepository.save(member);

        Item item = createBook("gogo", 10, 10);

        itemRepository.save(item);

        orderService.order(member.getId(), item.getId(), 11);

    }

    @Test
    void 주문취소() {
        Member member = createMember("evan", new Address("sunchang", "dsafasd", "123123"));

        memberRepository.save(member);

        Book item = createBook("시로", 10000, 10);

        itemRepository.save(item);


        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        System.out.println(item.getStockQuantity() + "item.getStockQuantity()");


        // when
        orderService.cancelOrder(orderId);

        Order order = orderRepository.findOne(orderId);

        assertEquals(order.getStatus(), OrderStatus.CANCEL);
        assertEquals(10, item.getStockQuantity());
    }

}