package ex1hello.hello.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;
@Embeddable
@NoArgsConstructor
@Getter @Setter
public class Period {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
