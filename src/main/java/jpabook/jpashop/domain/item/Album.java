package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("Album")
@Getter
public class Album extends Item  {

    @Id @GeneratedValue
    @Column(name = "album_id")
    private Long id;

    private String artist;
    private String etc;

}
