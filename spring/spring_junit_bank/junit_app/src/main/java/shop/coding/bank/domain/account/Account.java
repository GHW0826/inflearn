package shop.coding.bank.domain.account;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.parameters.P;
import shop.coding.bank.domain.user.User;
import shop.coding.bank.handler.ex.CustomApiException;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name ="account_tb", indexes = {
        @Index(name = "idx_account_number", columnList = "number")
})
@Entity
@Getter
public class Account {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 4)
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

    // Lazy 로딩이어도 id를 조회할때는 select 쿼리가 날아가지 않는다.
    public void checkOwner(Long userId) {
        if (user.getId() != userId) {
            throw new CustomApiException("계좌 소유자가 아닙니다");
        }
    }

    public void deposit(Long amount) {
        this.balance = balance + amount;
    }

    public void checkSamePassword(Long password) {
        if (this.password.longValue() != password.longValue()) {
            throw new CustomApiException("계좌 비밀번호 검증에 실패했습니다");
        }
    }

    public void checkBalance(Long amount) {
        if (this.balance < amount) {
            throw new CustomApiException("계좌 잔액이 부족합니다");
        }
    }

    public void withdraw(Long amount) {
        checkBalance(amount);
        balance = balance - amount;
    }
}
