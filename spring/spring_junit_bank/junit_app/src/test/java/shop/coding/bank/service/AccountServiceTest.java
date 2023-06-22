package shop.coding.bank.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.coding.bank.config.dummy.DummyObject;
import shop.coding.bank.domain.account.Account;
import shop.coding.bank.domain.account.AccountRepository;
import shop.coding.bank.domain.transaction.Transaction;
import shop.coding.bank.domain.transaction.TransactionRepository;
import shop.coding.bank.domain.user.User;
import shop.coding.bank.domain.user.UserRepository;
import shop.coding.bank.dto.account.AccountReqDto;
import shop.coding.bank.dto.account.AccountReqDto.AccountDepositReqDto;
import shop.coding.bank.dto.account.AccountReqDto.AccountSaveReqDto;
import shop.coding.bank.dto.account.AccountRespDto;
import shop.coding.bank.dto.account.AccountRespDto.AccountDepositRespDto;
import shop.coding.bank.dto.account.AccountRespDto.AccountSaveRespDto;
import shop.coding.bank.handler.ex.CustomApiException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest extends DummyObject {

    @InjectMocks // 모든 Mock들이 InjectMocks로 주입됨.
    private AccountService accountService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Spy // 진짜 객체를 InjectMocks에 주입한다.
    private ObjectMapper om;

    @Test
    public void 계좌입금_test2() throws Exception {
        // dto와 특정 값이 잘맞는지만 보면 된다.
        // dto가 잘 만들어졌는지는 controller에서도 볼 수 있다.
        // 구지 service테스트에서 해야 할까?

        // TODO
        // 계좌 출급 테스트
        // 계좌 이체 테스트
        // 계좌목록보기 테스트
        // 계좌 상세보기

        // given
        AccountDepositReqDto accountDepositReqDto = new AccountDepositReqDto();
        accountDepositReqDto.setNumber(1111L);
        accountDepositReqDto.setAmount(100L);
        accountDepositReqDto.setGubun("DEPOSIT");
        accountDepositReqDto.setTel("01088887777");

        User ssar1 = newMockUser(1L, "ssar", "ssar");
        Account ssarAccount1 = newMockAccount(1L, 1111L, 1000L, ssar1); // 실행됨- 계좌 1000원 ssarAccount1 -> 1000
        when(accountRepository.findByNumber(any())).thenReturn(Optional.of(ssarAccount1));

        User ssar2 = newMockUser(1L, "ssar", "ssar");
        Account ssarAccount2 = newMockAccount(1L, 1111L, 1000L, ssar2); // 실행됨- 계좌 1000원 ssarAccount1 -> 1000
        Transaction transaction = newMockDepositTransaction(1L, ssarAccount2); //실행됨 - 1100원 ssarAccount1 -> 1100 (ssarAccount1시)
        when(transactionRepository.save(any())).thenReturn(transaction);

        // when
        AccountDepositRespDto accountDepositRespDto = accountService.계좌입금(accountDepositReqDto);
        String responseBody = om.writeValueAsString(accountDepositRespDto);
        System.out.println("responseBody = " + responseBody);

        // then
        assertThat(ssarAccount1.getBalance()).isEqualTo(1100L);
    }

    // 서비스 테스트. 기술적인 테크닉을 보여주기 위해서 함.
    // 진짜 서비스를 하고 싶으면, 내가 지금 무엇을 테스트해야할지 명확히 구분해야함(책임 분리)
    // DTO를 만드는 책임 -> 서비스에 있지만, (서비스에서 DTO검증 안하고 - controller 테스트에서 할수 있음)
    // DB관련된 것도 -> 서비스 책임이 아니라 안봐도됨 사실.
    // DB 관련된 것을 조회했을 때, 그 값을 통해서 어떤 비즈니스 로직이 흘러가는 것이 있으면
    // -> stub으로 정의해서 테스트 해보면 된다.

    // DB 스텁, DB 스텁(가짜로 DB만들어서 deposit 검증... 0원 검증 .. 등)
    @Test
    public void 계좌입금_test3() throws Exception {

        // given
        Account account = newMockAccount(1L, 1111L, 1000L, null);
        Long amount = 100L;

        // when
        if (amount <= 0L) {
            throw new CustomApiException("0원 이하의 금액을 입금할 수 없습니다.");
        }
        account.deposit(100L);

        // then
        assertThat(account.getBalance()).isEqualTo(1100L);
    }


    // Account -> balance 변경됐는지
    // Transaction -> balance 잘 기록했는지
    @Test
    public void 계좌입금_test() throws Exception {
        // given
        AccountDepositReqDto accountDepositReqDto = new AccountDepositReqDto();
        accountDepositReqDto.setNumber(1111L);
        accountDepositReqDto.setAmount(100L);
        accountDepositReqDto.setGubun("DEPOSIT");
        accountDepositReqDto.setTel("01088887777");

        // stub 1 (첫 2줄은 실행될때 when절은 바로 실행 안됨.) when 절은 실제 서비스가 실행되야 실행됨
        User ssar = newMockUser(1L, "ssar", "ssar");
        Account ssarAccount1 = newMockAccount(1L, 1111L, 1000L, ssar); // 실행됨- 계좌 1000원 ssarAccount1 -> 1000
        when(accountRepository.findByNumber(any())).thenReturn(Optional.of(ssarAccount1));

        // stub 2 (첫 1줄은 실행될때 when절은 바로 실행 안됨.) when 절은 실제 서비스가 실행되야 실행됨
        // stub이 진생될 때마다 연관된 객체는 새로 만들어야 굿
        Account ssarAccount2 = newMockAccount(1L, 1111L, 1000L, ssar); // 실행됨- 계좌 1000원 ssarAccount1 -> 1000
        Transaction transaction = newMockDepositTransaction(1L, ssarAccount2); //실행됨 - 1100원 ssarAccount1 -> 1100 (ssarAccount1시)
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        // when (when 절 다 실행) mock 객체는 when절마다 생성하지 않으면 싱크 오류가 날 수 있음
        AccountDepositRespDto accountDepositRespDto = accountService.계좌입금(accountDepositReqDto);
        System.out.println("트랜잭션 입금계좌 잔액 = " + accountDepositRespDto.getTransaction().getDepositAccountBalance());
        System.out.println("계좌 잔액 = " + ssarAccount1.getBalance());

        // then
        assertThat(ssarAccount1.getBalance()).isEqualTo(1100L);
        assertThat(ssarAccount2.getBalance()).isEqualTo(1100L);
        assertThat(accountDepositRespDto.getTransaction().getDepositAccountBalance()).isEqualTo(1100L);
    }

    @Test
    public void 계좌삭제_test() throws Exception {
        // given
        Long number = 1111L;
        Long userId = 2L;


        // stub (리턴 값이 있는 경우 주로 사용)
        User ssar = newMockUser(1L, "ssar", "ssar");
        Account ssarAccount = newMockAccount(1L, 1111L, 1000L, ssar);

        when(accountRepository.findByNumber(any())).thenReturn(Optional.of(ssarAccount));

        // when
        assertThrows(CustomApiException.class, ()-> accountService.계좌삭제(number, userId));

        // then

        /*
        try {
            accountService.계좌삭제(number, userId);
        } catch (Exception e) {
            return;
        }
        fail("예외 발생 안함");
        */
    }

    @Test
    public void 계좌등록_test() throws Exception {
        // given
        Long userId = 1L;

        AccountSaveReqDto accountSaveReqDto = new AccountSaveReqDto();
        accountSaveReqDto.setNumber(1111L);
        accountSaveReqDto.setPassword(1234L);

        // stub - 1
        User ssar = newMockUser(userId, "ssar", "ssar");
        when(userRepository.findById(any())).thenReturn(Optional.of(ssar));

        // stub - 2
        when(accountRepository.findByNumber(any())).thenReturn(Optional.empty());

        // stub - 3
        Account ssarAccount = newMockAccount(1L, 1111L, 1000L, ssar);
        when(accountRepository.save(any())).thenReturn(ssarAccount);

        // when
        AccountSaveRespDto accountSaveRespDto = accountService.계좌등록(accountSaveReqDto, userId);
        String s = om.writeValueAsString(accountSaveRespDto);
        System.out.println("s = " + s);

        // then
        assertThat(accountSaveRespDto.getNumber()).isEqualTo(1111L);
    }
}