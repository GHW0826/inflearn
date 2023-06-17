package shop.coding.bank.domain.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.coding.bank.domain.user.User;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
