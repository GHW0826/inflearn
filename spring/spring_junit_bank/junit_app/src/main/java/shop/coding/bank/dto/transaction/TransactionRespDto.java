package shop.coding.bank.dto.transaction;

import lombok.Getter;
import lombok.Setter;
import shop.coding.bank.domain.account.Account;
import shop.coding.bank.domain.transaction.Transaction;
import shop.coding.bank.util.CustomDateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionRespDto {

    @Getter @Setter
    public static class TransactionListRespDto {
        private List<TransactionDto> transactions = new ArrayList<>();

        public TransactionListRespDto(List<Transaction> transactions, Account account) {
            this.transactions = transactions.stream()
                    .map((transaction -> new TransactionDto(transaction, account.getNumber())))
                    .collect(Collectors.toList());
        }

        @Getter @Setter
        public class TransactionDto {
            private Long id;
            private String gubun;
            private Long amount;
            private String sender;
            private String reciver;
            private String tel;
            private String createdAt;
            private Long balance;

            public TransactionDto(Transaction transaction, Long accountNumber) {
                this.id = transaction.getId();
                this.gubun = transaction.getGubun().getValue();
                this.amount = transaction.getAmount();
                this.sender = transaction.getSender();
                this.reciver = transaction.getReceiver();
                this.createdAt = CustomDateUtil.toStringFormat(transaction.getCreateAt());
                this.tel = transaction.getTel() == null ? "없음" : transaction.getTel();

                // withdraw
                if (transaction.getDepositAccount() == null) {
                    this.balance = transaction.getWithdrawAccountBalance();
                }
                else if (transaction.getWithdrawAccount() == null) {
                    this.balance = transaction.getDepositAccountBalance();
                }
                else {
                    // 1111 계좌의 입출금 내역 (출금계좌 = null, 입금계좌 = 값)
                    // 출금계좌 = 값, 입금계좌 = null)
                    if (accountNumber.longValue() == transaction.getDepositAccount().getNumber()) {
                        this.balance = transaction.getDepositAccountBalance();
                    }
                    else {
                        this.balance = transaction.getWithdrawAccountBalance();
                    }
                }
            }
        }
    }
}
