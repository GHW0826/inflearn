package ex1hello.hello.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("a")
public class Album extends Item {
    private String artist;
}
