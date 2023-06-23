package shop.coding.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.coding.bank.domain.account.Account;
import shop.coding.bank.domain.account.AccountRepository;
import shop.coding.bank.domain.transaction.Transaction;
import shop.coding.bank.domain.transaction.TransactionEnum;
import shop.coding.bank.domain.transaction.TransactionRepository;
import shop.coding.bank.domain.user.User;
import shop.coding.bank.domain.user.UserRepository;
import shop.coding.bank.dto.account.AccountReqDto;
import shop.coding.bank.dto.account.AccountReqDto.AccountDepositReqDto;
import shop.coding.bank.dto.account.AccountReqDto.AccountSaveReqDto;
import shop.coding.bank.dto.account.AccountReqDto.AccountTransferReqDto;
import shop.coding.bank.dto.account.AccountReqDto.AccountWithdrawReqDto;
import shop.coding.bank.dto.account.AccountRespDto;
import shop.coding.bank.dto.account.AccountRespDto.AccountDepositRespDto;
import shop.coding.bank.dto.account.AccountRespDto.AccountSaveRespDto;
import shop.coding.bank.dto.account.AccountRespDto.AccountTransferRespDto;
import shop.coding.bank.dto.account.AccountRespDto.AccountWithdrawRespDto;
import shop.coding.bank.dto.user.UserRespDto;
import shop.coding.bank.dto.user.UserRespDto.AccountListRespDto;
import shop.coding.bank.handler.ex.CustomApiException;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
public class AccountService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public AccountService(UserRepository userRepository, AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public AccountTransferRespDto 계좌이체(AccountTransferReqDto accountTransferReqDto, Long userId) {

        // 출금계좌와 입금계좌 동일계좌 확인
        if (accountTransferReqDto.getWithdrawNumber().longValue() ==
                accountTransferReqDto.getDepositNumber().longValue()) {
            throw new CustomApiException("입출금계좌가 동일할 수 없습니다");
        }

        // 0원 체크 (validation으로 해도 됨)
        if (accountTransferReqDto.getAmount() <= 0L) {
            throw new CustomApiException("0원 이하의 금액을 입금할 수 없습니다.");
        }

        // 출금계좌 확인
        Account withdrawAccountPS = accountRepository.findByNumber(accountTransferReqDto.getWithdrawNumber()).orElseThrow(
                ()-> new CustomApiException("출금계좌를 찾을 수 없습니다")
        );
        // 입금계좌 확인
        Account depositAccountPS = accountRepository.findByNumber(accountTransferReqDto.getDepositNumber()).orElseThrow(
                ()-> new CustomApiException("입금계좌를 찾을 수 없습니다")
        );

        // 출금 소유자 확인.(로그인한 사람과 동일한지)
        withdrawAccountPS.checkOwner(userId);

        // 출금계좌 비밀번호 확인
        withdrawAccountPS.checkSamePassword(accountTransferReqDto.getWithdrawPassword());

        // 출금계좌 잔액 확인
        withdrawAccountPS.checkBalance(accountTransferReqDto.getAmount());

        // 이체하기
        withdrawAccountPS.withdraw(accountTransferReqDto.getAmount());
        depositAccountPS.deposit(accountTransferReqDto.getAmount());

        // 거래내역 남기기 (내 계좌에서 ATM으로 출금
        Transaction transaction = Transaction.builder()
                .withdrawAccount(withdrawAccountPS)
                .depositAccount(depositAccountPS)
                .withdrawAccountBalance(withdrawAccountPS.getBalance())
                .depositAccountBalance(depositAccountPS.getBalance())
                .amount(accountTransferReqDto.getAmount())
                .gubun(TransactionEnum.TRANSFER)
                .sender(accountTransferReqDto.getWithdrawNumber()+"")
                .receiver(accountTransferReqDto.getDepositNumber()+"")
                .build();
        Transaction transactionPS = transactionRepository.save(transaction);

        // DTO 응답.
        return new AccountTransferRespDto(withdrawAccountPS, transactionPS);
    }


    @Transactional
    public AccountWithdrawRespDto 계좌출금(AccountWithdrawReqDto accountWithdrawReqDto, Long userId) {

        // 0원 체크 (validation으로 해도 됨)
        if (accountWithdrawReqDto.getAmount() <= 0L) {
            throw new CustomApiException("0원 이하의 금액을 입금할 수 없습니다.");
        }
        
        // 출금계좌 확인
        Account withdrawAccountPS = accountRepository.findByNumber(accountWithdrawReqDto.getNumber()).orElseThrow(
                ()-> new CustomApiException("계좌를 찾을 수 없습니다")
        );

        // 출금 소유자 확인.(로그인한 사람과 동일한지)
        withdrawAccountPS.checkOwner(userId);

        // 출금계좌 비밀번호 확인
        withdrawAccountPS.checkSamePassword(accountWithdrawReqDto.getPassword());

        // 출금계좌 잔액 확인
        withdrawAccountPS.checkBalance(accountWithdrawReqDto.getAmount());

        // 출금하기
        withdrawAccountPS.withdraw(accountWithdrawReqDto.getAmount());

        // 거래내역 남기기 (내 계좌에서 ATM으로 출금
        Transaction transaction = Transaction.builder()
                .withdrawAccount(withdrawAccountPS)
                .depositAccount(null)
                .withdrawAccountBalance(withdrawAccountPS.getBalance())
                .depositAccountBalance(null)
                .amount(accountWithdrawReqDto.getAmount())
                .gubun(TransactionEnum.WITHDRAW)
                .sender(accountWithdrawReqDto.getNumber()+"")
                .receiver("ATM")
                .build();
        Transaction transactionPS = transactionRepository.save(transaction);

        // DTO 응답.
        return new AccountWithdrawRespDto(withdrawAccountPS, transactionPS);
    }


    // 인증이 필요 없다.
    @Transactional
    public AccountDepositRespDto 계좌입금(AccountDepositReqDto accountDepositReqDto) {
        // ATM -> 누군가의 계좌

        // 0원 체크 (validation으로 해도 됨)
        if (accountDepositReqDto.getAmount() <= 0L) {
            throw new CustomApiException("0원 이하의 금액을 입금할 수 없습니다.");
        }

        // 입금계좌 확인
        Account depositAccountPS = accountRepository.findByNumber(accountDepositReqDto.getNumber()).orElseThrow(
                ()-> new CustomApiException("계좌를 찾을 수 없습니다")
        );

        // 입금 (해당 계좌 balacn 조정 - update문 - 더티체킹)
        depositAccountPS.deposit(accountDepositReqDto.getAmount());

        // 거래내역 남기기
        Transaction transaction = Transaction.builder()
                .depositAccount(depositAccountPS)
                .withdrawAccount(null)
                .depositAccountBalance(depositAccountPS.getBalance())
                .withdrawAccountBalance(null)
                .amount(accountDepositReqDto.getAmount())
                .gubun(TransactionEnum.DEPOSIT)
                .sender("ATM")
                .receiver(depositAccountPS.getNumber() + "")
                .tel(accountDepositReqDto.getTel())
                .build();
        Transaction transactionPS = transactionRepository.save(transaction);
        return new AccountDepositRespDto(depositAccountPS, transactionPS);
    }

    @Transactional
    public void 계좌삭제(Long number, Long userId) {
        // 1. 계좌 확인
        Account accountPS = accountRepository.findByNumber(number).orElseThrow(
                () -> new CustomApiException("계좌를 찾을 수 없습니다")
        );

        // 2. 계좌 소유자 확인
        accountPS.checkOwner(userId);

        // 3. 계좌 삭제
        accountRepository.deleteById(accountPS.getId());
    }



    public AccountListRespDto 계좌목록보기_유저별(Long userId) {
        // User거 DB에 있는지 검증 겸 유저 엔티티 가져오기
        User userPS = userRepository.findById(userId).orElseThrow(
                () -> new CustomApiException("유저를 찾을 수 없습니다.")
        );

        // 유저의 모든 계좌 목록.
        List<Account> accountListPS = accountRepository.findByUser_id(userId);
        return new AccountListRespDto(userPS, accountListPS);
    };

    // 체크는 서비스의 역할이 아님, 컨트롤러가 함.
    @Transactional
    public AccountSaveRespDto 계좌등록(AccountSaveReqDto accountSaveReqDto, Long userId) {
        // User거 DB에 있는지 검증 겸 유저 엔티티 가져오기
        User userPS = userRepository.findById(userId).orElseThrow(
                () -> new CustomApiException("유저를 찾을 수 없습니다.")
        );

        // 해당 계좌가 DB에 있는 중복 여부 체크.

        Optional<Account> accountOP = accountRepository.findByNumber(accountSaveReqDto.getNumber());
        if (accountOP.isPresent()) {
            throw new CustomApiException("해당 계좌가 이미 존재합니다.");
        }

        // 계좌 등록
        Account accountPS = accountRepository.save(accountSaveReqDto.toEntity(userPS));

        // DTO를 응답
        return new AccountSaveRespDto(accountPS);
    }
}
