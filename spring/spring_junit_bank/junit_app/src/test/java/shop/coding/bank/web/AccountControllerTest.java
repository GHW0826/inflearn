package shop.coding.bank.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import shop.coding.bank.config.dummy.DummyObject;
import shop.coding.bank.domain.account.Account;
import shop.coding.bank.domain.account.AccountRepository;
import shop.coding.bank.domain.user.User;
import shop.coding.bank.domain.user.UserRepository;
import shop.coding.bank.dto.account.AccountReqDto;
import shop.coding.bank.dto.account.AccountReqDto.AccountSaveReqDto;
import shop.coding.bank.dto.account.AccountReqDto.AccountTransferReqDto;
import shop.coding.bank.dto.account.AccountReqDto.AccountWithdrawReqDto;
import shop.coding.bank.handler.ex.CustomApiException;

import javax.persistence.EntityManager;
import javax.xml.transform.Result;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("classpath:db/teardown.sql")
// @Transactional
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class AccountControllerTest extends DummyObject {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void setUp() {
        User ssar = userRepository.save(newUser("ssar", "ssar"));
        User cos = userRepository.save(newUser("cos", "cos"));
        Account ssarAccount1 = accountRepository.save(newAccount(1111L, ssar));
        Account cosAccount1 = accountRepository.save(newAccount(2222L, cos));
        em.flush();
    }

    @WithUserDetails(value = "cos", setupBefore = TestExecutionEvent.TEST_EXECUTION) // 디비에서 username=ssar 조회를 해서 세션에 담아주는 어노테이션
    @Test
    public void transferAccount_test() throws Exception {
        // given
        AccountTransferReqDto accountTransferReqDto = new AccountTransferReqDto();
        accountTransferReqDto.setWithdrawNumber(1111L);
        accountTransferReqDto.setDepositNumber(2222L);
        accountTransferReqDto.setWithdrawPassword(1234L);
        accountTransferReqDto.setAmount(100L);
        accountTransferReqDto.setGubun("TRANSFER");

        String requestBody = om.writeValueAsString(accountTransferReqDto);
        System.out.println("requestBody = " + requestBody);
        // when
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/api/s/account/transfer")
                .content(requestBody).contentType(MediaType.APPLICATION_JSON));
        String responseBody1 = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody = " + responseBody1);

        //then
        resultActions.andExpect(status().isCreated());
    }

    @WithUserDetails(value = "cos", setupBefore = TestExecutionEvent.TEST_EXECUTION) // 디비에서 username=ssar 조회를 해서 세션에 담아주는 어노테이션
    @Test
    public void withdrawAccount_test() throws Exception {
        // given
        AccountWithdrawReqDto accountWithdrawReqDto = new AccountWithdrawReqDto();
        accountWithdrawReqDto.setNumber(1111L);
        accountWithdrawReqDto.setPassword(1234L);
        accountWithdrawReqDto.setAmount(100L);
        accountWithdrawReqDto.setGubun("WITHDRAW");

        String requestBody = om.writeValueAsString(accountWithdrawReqDto);
        System.out.println("requestBody = " + requestBody);
        // when
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/api/s/account/withdraw")
                .content(requestBody).contentType(MediaType.APPLICATION_JSON));
        String responseBody1 = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody = " + responseBody1);

        //then
        resultActions.andExpect(status().isCreated());
    }

    @Test
    public void depositAccount_test() throws Exception {
        // given
        AccountReqDto.AccountDepositReqDto accountDepositReqDto = new AccountReqDto.AccountDepositReqDto();
        accountDepositReqDto.setNumber(1111L);
        accountDepositReqDto.setAmount(100L);
        accountDepositReqDto.setGubun("DEPOSIT");
        accountDepositReqDto.setTel("01088887777");

        String requestBody = om.writeValueAsString(accountDepositReqDto);
        System.out.println("requestBody = " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/api/deposit")
                .content(requestBody).contentType(MediaType.APPLICATION_JSON));
        String responseBody1 = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody = " + responseBody1);

        //then
        // dto 확인후 상태 코드 확인이 적당
        resultActions.andExpect(status().isCreated());
    }

    /*
        테스트시에는 insert 한것들이 전부 PC에 올라감(영속화)
        영속화 된것들을 초기화 해주는 것이 개발 모드와 동일한 환경으로 테스트를 할 수 있게 해준다.
        최초 select는 쿼리가 발생하지만, - PC에 있으면 1차 캐시를 함.
        Lazy 로딩은 쿼리도 발생안함 (PC에 있으면)
        Lazy 로딩할때 PC에 없다면 쿼리가 발생
     */
    @WithUserDetails(value = "cos", setupBefore = TestExecutionEvent.TEST_EXECUTION) // 디비에서 username=ssar 조회를 해서 세션에 담아주는 어노테이션
    @Test
    public void deleteAccount_test() throws Exception {
        // given
        Long number = 1111L;
        Long number2 = 2222L;

        // when
        ResultActions resultActions = mvc.perform(delete("/api/s/account/" + number2));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody = " + responseBody);
        
        // then
        // junit 테스트에서 delete 쿼리는 DB관련 (DML)으로 가장 마지막에 실행되면 발동 안함.
        // delete 쿼리를 보기 위해 추가
        assertThrows(CustomApiException.class, ()-> accountRepository.findByNumber(number).orElseThrow(
                ()-> new CustomApiException("계좌를 찾을 수 없습니다")
        ));
    }

    // jwt token -> 인증필터 -> 시큐리티 세션 생성
    // setupBefore=TEST_METHOD (setUp 메서드 실행전에 수행)
    // setupBefore = TestExecutionEvent.TEST_EXECUTION (saveAccount_test 메서드 실행전에 수행)
    @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION) // 디비에서 username=ssar 조회를 해서 세션에 담아주는 어노테이션
    @Test
    public void saveAccount_test() throws Exception {
        // given
        AccountSaveReqDto accountSaveReqDto = new AccountSaveReqDto();
        accountSaveReqDto.setNumber(9999L);
        accountSaveReqDto.setPassword(1234L);
        String requestBody = om.writeValueAsString(accountSaveReqDto);
        System.out.println("requestBody = " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/api/s/account")
                .content(requestBody).contentType(MediaType.APPLICATION_JSON));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody = " + responseBody);

        //then
        resultActions.andExpect(status().isCreated());
    }
}