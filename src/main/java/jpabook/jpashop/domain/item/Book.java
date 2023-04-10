package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("Book")
@Getter
public class Book extends Item {

    private String author;
    private String isbn;

    public Book() {
        super();
    }

    public Book(String name, int price, int quantity) {
        super(name, price, quantity);

    }
}
