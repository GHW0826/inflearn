package shop.coding.bank.domain.account;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.coding.bank.domain.user.User;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
