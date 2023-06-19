package shop.coding.bank.domain.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shop.coding.bank.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    // jpa query method
    // select * from account where num = :number;
    // checkpoint :  user도 같이 떙기는거 리팩토링 해야함.
    // (계좌 소유자 확인시에 쿼리가 2번 나가기 때문에 join fetch) - account.getUser().getId();
    // join fetch를 하면 조인해서 객체에 값을 미리 가져올 수 있다.
    // @Query("select ac FROM Account ac JOIN FETCH ac.user u WHERE ac.number = :number")
    Optional<Account> findByNumber(Long number);

    // jpa query method
    //  select * from account where user_id = :id;
    List<Account> findByUser_id(Long id);
}
