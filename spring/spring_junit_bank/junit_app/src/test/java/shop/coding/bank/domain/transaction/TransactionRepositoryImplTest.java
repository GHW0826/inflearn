package shop.coding.bank.domain.transaction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import shop.coding.bank.config.dummy.DummyObject;
import shop.coding.bank.domain.account.Account;
import shop.coding.bank.domain.account.AccountRepository;
import shop.coding.bank.domain.user.User;
import shop.coding.bank.domain.user.UserRepository;

import javax.persistence.EntityManager;
import java.util.List;

// Repository 테스트라서
// spring boot test, mokito 필요 없음
@ActiveProfiles("test")
@DataJpaTest // DB 관련 bean이 다 올라옴
//@Sql("classpath:db/teardown.sql")
//@Transactional
public class TransactionRepositoryImplTest extends DummyObject {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EntityManager em;

    public void findTransactionList_fetch_test() throws Exception {
        // given
        Long accountId = 1L;

        // when
        List<Transaction> transactionListPS =
                transactionRepository.findTransactonList(accountId, "DEPOSIT", 0);

        transactionListPS.forEach((t)-> {
            System.out.println("t.getId() = " + t.getId());
            System.out.println("t.getAmount() = " + t.getAmount());
            System.out.println("t.getSender() = " + t.getSender());
            System.out.println("t.getReceiver() = " + t.getReceiver());
            System.out.println("t.getDepositAccountBalance() = " + t.getDepositAccountBalance());
            System.out.println("t.getWithdrawAccountBalance() = " + t.getWithdrawAccountBalance());

            // account 의 값이라 추가 쿼리 없음
            System.out.println("t.getWithdrawAccountBalance() = " + t.getWithdrawAccount().getBalance());

            // lazy (추가 쿼리 발생
            System.out.println("t.getWithdrawAccountBalance() = " + t.getWithdrawAccount().getUser().getFullname());
            System.out.println("================================================");
        });
    }


    @Test
    public void findTransactionList_deposit_test() throws Exception {
        // given
        Long accountId = 1L;

        // when
        List<Transaction> transactionListPS =
                transactionRepository.findTransactonList(accountId, "DEPOSIT", 0);

        transactionListPS.forEach((t)-> {
            System.out.println("t.getId() = " + t.getId());
            System.out.println("t.getAmount() = " + t.getAmount());
            System.out.println("t.getSender() = " + t.getSender());
            System.out.println("t.getReceiver() = " + t.getReceiver());
            System.out.println("t.getDepositAccountBalance() = " + t.getDepositAccountBalance());
            System.out.println("t.getWithdrawAccountBalance() = " + t.getWithdrawAccountBalance());
            System.out.println("================================================");
        });
    }

    @Test
    public void findTransactionList_withdraw_test() throws Exception {
        // given
        Long accountId = 1L;

        // when
        List<Transaction> transactionListPS =
                transactionRepository.findTransactonList(accountId, "WITHDRAW", 0);

        transactionListPS.forEach((t)-> {
            System.out.println("t.getId() = " + t.getId());
            System.out.println("t.getAmount() = " + t.getAmount());
            System.out.println("t.getSender() = " + t.getSender());
            System.out.println("t.getReceiver() = " + t.getReceiver());
            System.out.println("t.getDepositAccountBalance() = " + t.getDepositAccountBalance());
            System.out.println("t.getWithdrawAccountBalance() = " + t.getWithdrawAccountBalance());
            System.out.println("================================================");
        });
    }

    @Test
    public void findTransactionList_all_test() throws Exception {
        // given
        Long accountId = 1L;

        // when
        List<Transaction> transactionListPS =
                transactionRepository.findTransactonList(accountId, "ALL", 0);

        transactionListPS.forEach((t)-> {
            System.out.println("t.getId() = " + t.getId());
            System.out.println("t.getAmount() = " + t.getAmount());
            System.out.println("t.getSender() = " + t.getSender());
            System.out.println("t.getReceiver() = " + t.getReceiver());
            System.out.println("t.getDepositAccountBalance() = " + t.getDepositAccountBalance());
            System.out.println("t.getWithdrawAccountBalance() = " + t.getWithdrawAccountBalance());
            System.out.println("================================================");
        });

        // then
    }


    @BeforeEach
    public void setUp() {
        autoincrementReset();
        dataSetting();
        em.clear();
    }

    private void autoincrementReset() {
        em.createNamedQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNamedQuery("ALTER TABLE account_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNamedQuery("ALTER TABLE transaction_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
    }
    private void dataSetting() {
        User ssar = userRepository.save(newUser("ssar", "쌀"));
        User cos = userRepository.save(newUser("cos", "코스,"));
        User love = userRepository.save(newUser("love", "러브"));
        User admin = userRepository.save(newUser("admin", "관리자"));

        Account ssarAccount1 = accountRepository.save(newAccount(1111L, ssar));
        Account cosAccount = accountRepository.save(newAccount(2222L, cos));
        Account loveAccount = accountRepository.save(newAccount(3333L, love));
        Account ssarAccount2 = accountRepository.save(newAccount(4444L, ssar));

        Transaction withdrawTransaction1 = transactionRepository
                .save(newWithdrawTransaction(ssarAccount1, accountRepository));
        Transaction depositTransaction1 = transactionRepository
                .save(newDepositTransaction(cosAccount, accountRepository));
        Transaction transferTransaction1 = transactionRepository
                .save(newTransferTransaction(ssarAccount1, cosAccount, accountRepository));
        Transaction transferTransaction2 = transactionRepository
                .save(newTransferTransaction(ssarAccount1, loveAccount, accountRepository));
        Transaction transferTransaction3 = transactionRepository
                .save(newTransferTransaction(cosAccount, ssarAccount1, accountRepository));
    }

    @Test
    public void dataJpa_test1() {
        List<Transaction> transactionList = transactionRepository.findAll();
        transactionList.forEach((transaction)-> {
            System.out.println("transaction.getId() = " + transaction.getId());
            System.out.println("transaction.getSender = " + transaction.getSender());
            System.out.println("transaction.getReceiver() = " + transaction.getReceiver());
            System.out.println("============================" +
                    "");
        });
    }

    @Test
    public void dataJpa_test2() {
        List<Transaction> transactionList = transactionRepository.findAll();
        transactionList.forEach((transaction)-> {
            System.out.println("transaction.getId() = " + transaction.getId());
            System.out.println("transaction.getSender = " + transaction.getSender());
            System.out.println("transaction.getReceiver() = " + transaction.getReceiver());
            System.out.println("============================" +
                    "");
        });
    }
}
