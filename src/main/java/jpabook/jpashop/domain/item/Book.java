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
}
