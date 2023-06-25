package shop.coding.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.coding.bank.domain.account.Account;
import shop.coding.bank.domain.account.AccountRepository;
import shop.coding.bank.domain.transaction.Transaction;
import shop.coding.bank.domain.transaction.TransactionRepository;
import shop.coding.bank.dto.transaction.TransactionRespDto;
import shop.coding.bank.dto.transaction.TransactionRespDto.TransactionListRespDto;
import shop.coding.bank.handler.ex.CustomApiException;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public TransactionListRespDto 입출금목록보기(
            Long userId,
            Long accountNumber,
            String gubun,
            int page
    ) {
        Account accountPS = accountRepository.findByNumber(accountNumber).orElseThrow(
                () -> new CustomApiException("해당 계좌를 찾을 수 없습니다.")
        );

        accountPS.checkOwner(userId);
        List<Transaction> transactonListPS = transactionRepository.findTransactonList(accountPS.getId(), gubun, null);
        return new TransactionListRespDto(transactonListPS, accountPS);
    }
}
