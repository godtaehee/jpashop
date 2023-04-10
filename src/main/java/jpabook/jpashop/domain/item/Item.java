package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    public Item(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = quantity;
    }

    protected Item() {}

    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int remainQuantity = this.stockQuantity - quantity;
        System.out.println("remainQuawntity" + remainQuantity);
        if (remainQuantity < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = remainQuantity;

    }

}
