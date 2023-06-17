package shop.coding.bank.domain.account;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import shop.coding.bank.domain.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name ="account_tb")
@Entity
@Getter
public class Account {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    private Long number; // 계좌 번호
    @Column(nullable = false, length = 4)
    private Long password; // 계좌 비번
    @Column(nullable = false)
    private Long balance; // 잔액 (기본값 1000원)

    // 항상 ORM에서 fk의 주인은 Many Entity 쪽.
    // account.getUser(). 필드 호출시 == Lazy 발동
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user; // user_id

    @CreatedDate // insert
    @Column(nullable = false)
    private LocalDateTime createAt;

    @LastModifiedDate // insert, update
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public Account(Long id, Long number, Long password, Long balance, User user, LocalDateTime createAt, LocalDateTime updatedAt) {
        this.id = id;
        this.number = number;
        this.password = password;
        this.balance = balance;
        this.user = user;
        this.createAt = createAt;
        this.updatedAt = updatedAt;
    }
}
