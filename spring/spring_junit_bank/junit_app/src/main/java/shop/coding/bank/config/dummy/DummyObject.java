package shop.coding.bank.config.dummy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shop.coding.bank.domain.account.Account;
import shop.coding.bank.domain.account.AccountRepository;
import shop.coding.bank.domain.transaction.Transaction;
import shop.coding.bank.domain.transaction.TransactionEnum;
import shop.coding.bank.domain.user.User;
import shop.coding.bank.domain.user.UserEnum;

import java.time.LocalDateTime;

public class DummyObject {

    protected static Transaction newTransferTransaction(
            Account withdrawAccount,
            Account depositAccount,
            AccountRepository accountRepository
    ) {
        depositAccount.deposit(100L);
        withdrawAccount.withdraw(100L);

        // repository에서는 더티체킹 됨
        // controller에서는 더티체킹 안됨
        // 더티체킹이 안되서 해줘야함
        if (accountRepository != null) {
            accountRepository.save(depositAccount);
            accountRepository.save(withdrawAccount);
        }

        Transaction transaction = Transaction.builder()
                .withdrawAccount(withdrawAccount)
                .depositAccount(depositAccount)
                .withdrawAccountBalance(withdrawAccount.getBalance())
                .depositAccountBalance(depositAccount.getBalance())
                .amount(100L)
                .gubun(TransactionEnum.WITHDRAW)
                .sender(withdrawAccount.getNumber()+"")
                .receiver(depositAccount.getNumber()+"")
                .build();

        return transaction;
    }

    protected static Transaction newWithdrawTransaction(Account account, AccountRepository accountRepository) {
        account.withdraw(100L);

        // 더티체킹이 안되서 해줘야함
        if (accountRepository != null) {
            accountRepository.save(account);
        }

        Transaction transaction = Transaction.builder()
                .withdrawAccount(account)
                .depositAccount(null)
                .withdrawAccountBalance(account.getBalance())
                .depositAccountBalance(null)
                .amount(100L)
                .gubun(TransactionEnum.WITHDRAW)
                .sender(account.getNumber()+"")
                .receiver("ATM")
                .build();

        return transaction;
    }


    protected static Transaction newDepositTransaction(Account account, AccountRepository accountRepository) {
        account.deposit(100L);  // 1000이었으면 -> 900

        // 더티체킹이 안되서 해줘야함
        if (accountRepository != null) {
            accountRepository.save(account);
        }

        Transaction transaction = Transaction.builder()
                .withdrawAccount(null)
                .depositAccount(account)
                .withdrawAccountBalance(null)
                .depositAccountBalance(account.getBalance())
                .amount(100L)
                .gubun(TransactionEnum.DEPOSIT)
                .sender("ATM")
                .receiver(account.getNumber()+"")
                .tel("01022227777")
                .build();
        
        return transaction;
    }


    // 계좌 1111L 1000원
    // 입금 트랜잭션 -> 계좌 1100원 변경 -> 입금 트랜잭션 히스토리가 생성되어야 함.
    protected static Transaction newMockDepositTransaction(Long id, Account account) {
        account.deposit(100L);
        Transaction transaction = Transaction.builder()
                .id(id)
                .withdrawAccount(null)
                .depositAccount(account)
                .withdrawAccountBalance(null)
                .depositAccountBalance(account.getBalance())
                .amount(100L)
                .gubun(TransactionEnum.DEPOSIT)
                .sender("ATM")
                .receiver(account.getNumber() + "")
                .tel("01088887777")
                .createAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return transaction;
    }
    protected static User newUser(String username, String fullname) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encPassword = passwordEncoder.encode("1234");
        return User.builder()
                .username(username)
                .password(encPassword)
                .email(username+"@gmail.com")
                .fullname(fullname)
                .role(UserEnum.CUSTOMER)
                .build();
    }

    protected static User newMockUser(Long id, String username, String fullname) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encPassword = passwordEncoder.encode("1234");
        return User.builder()
                .id(id)
                .username(username)
                .password(encPassword)
                .email(username+"@gmail.com")
                .fullname(fullname)
                .role(UserEnum.CUSTOMER)
                .createAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    protected static Account newAccount(Long number, User user) {
        return Account.builder()
                .number(number)
                .password(1234L)
                .balance(1000L)
                .user(user)
                .build();
    }

    protected static Account newMockAccount(Long id, Long number, Long balance, User user) {
        return Account.builder()
                .id(id)
                .number(number)
                .password(1234L)
                .balance(balance)
                .user(user)
                .createAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }


}
