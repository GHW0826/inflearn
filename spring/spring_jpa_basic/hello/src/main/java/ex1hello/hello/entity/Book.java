package ex1hello.hello.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("b")
public class Book extends Item {
    private String author;
    private String isbn;
}
