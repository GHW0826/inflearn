package shop.coding.bank.domain.account;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.coding.bank.domain.user.User;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    // jpa query method
    // select * from account where num = :number;
    // checkpoint :  user도 같이 떙기는거 리팩토링 해야함.
    Optional<Account> findByNumber(Long number);
}
