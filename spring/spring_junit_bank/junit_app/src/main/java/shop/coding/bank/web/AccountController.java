package shop.coding.bank.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import shop.coding.bank.config.auth.LoginUser;
import shop.coding.bank.dto.ResponseDto;
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
import shop.coding.bank.handler.ex.CustomForbiddenException;
import shop.coding.bank.service.AccountService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/account/s/account/transfer")
    public ResponseEntity<?> transferAccount(
            @RequestBody @Valid AccountTransferReqDto accountTransferReqDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal LoginUser loginUser
    ) {
        AccountTransferRespDto accountTransferRespDto = accountService.계좌이체(accountTransferReqDto, loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌 이체 완료", accountTransferRespDto), HttpStatus.OK);
    }

    @PostMapping("/account/s/account/withdraw")
    public ResponseEntity<?> withdrawAccount(
            @RequestBody @Valid AccountWithdrawReqDto accountWithReqDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal LoginUser loginUser
    ) {
        AccountWithdrawRespDto accountWithdrawRespDto = accountService.계좌출금(accountWithReqDto, loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌 출금 완료", accountWithdrawRespDto), HttpStatus.OK);
    }

    // 인증 필요 없음
    @PostMapping("/account/deposit")
    public ResponseEntity<?> depositAccount(
            @RequestBody @Valid AccountDepositReqDto accountDepositReqDto,
            BindingResult bindingResult
    ) {
        AccountDepositRespDto accountDepositRespDto = accountService.계좌입금(accountDepositReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌 입금 완료", accountDepositRespDto), HttpStatus.OK);
    }

    @PostMapping("/s/account")
    public ResponseEntity<?> saveAccount(
            @RequestBody @Valid AccountSaveReqDto accountSaveReqDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal LoginUser loginUser // jwt 정보
    ) {
        AccountSaveRespDto accountSaveRespDto = accountService.계좌등록(accountSaveReqDto, loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌등록 성공", accountSaveRespDto), HttpStatus.CREATED);
    }

    // "/s/account/{id}"
    // 인증이 필요하고, account 테이블에 1번 row를 주세요
    // cos로 로그인을 했는데 cos의 id가 2번. but 1번을 주면 다른 유저의 계좌를 볼 수 있음.
    // 권한처리로 선호하지 않음.
    
    // {id} 제거시 인증이 필요하고, account 테이블 데이터 다 달라.
    // -> 변경
    // 인증이 필요하고, account 테이블에 login한 유저의 계좌만 주세요
    @GetMapping("/s/account/login-user")
    public ResponseEntity<?> findUserAccount(
            @AuthenticationPrincipal LoginUser loginUser
    ) {
        AccountListRespDto accountListRespDto = accountService.계좌목록보기_유저별(loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌목록보기_유저별 성공", accountListRespDto), HttpStatus.OK);
    }

    @DeleteMapping("s/account/{number}")
    public ResponseEntity<?> deleteAccount(
            @PathVariable Long number,
            @AuthenticationPrincipal LoginUser loginUser
    ) {
        accountService.계좌삭제(number, loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌 삭제 완료", null), HttpStatus.OK);
    }
}
