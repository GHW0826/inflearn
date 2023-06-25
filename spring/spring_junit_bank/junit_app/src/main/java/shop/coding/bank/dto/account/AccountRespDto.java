package shop.coding.bank.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import shop.coding.bank.domain.account.Account;
import shop.coding.bank.domain.transaction.Transaction;
import shop.coding.bank.dto.account.AccountRespDto.AccountDepositRespDto.TransactionDto;
import shop.coding.bank.util.CustomDateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AccountRespDto {

    @Getter @Setter
    public static class AccountDetailRespDto {
        private Long id;
        private Long number;
        private Long balance;
        private List<TransactionDto> transactions = new ArrayList<>();

        public AccountDetailRespDto(Account account, List<Transaction> transactions) {
            this.id = account.getId();
            this.number = account.getNumber();
            this.balance = account.getBalance();
            this.transactions = transactions.stream()
                    .map((transaction -> new TransactionDto(transaction, account.getNumber())))
                    .collect(Collectors.toList());
        }

        @Getter @Setter
        public class TransactionDto {
            private Long id;
            private String gubun;
            private String sender;
            private String reciver;
            private Long amount;
            @JsonIgnore
            private Long depositAccountBalance; // 클라이언트에게 전달X -> 서비스단에서 테스트 용도
            private String tel;
            private String createdAt;
            private Long balance;


            public TransactionDto(Transaction transaction, Long accountNumber) {
                this.id = transaction.getId();
                this.gubun = transaction.getGubun().getValue();
                this.sender = transaction.getSender();
                this.reciver = transaction.getReceiver();
                this.amount = transaction.getAmount();
                this.tel = transaction.getTel() == null ? "없음" : transaction.getTel();
                this.createdAt = CustomDateUtil.toStringFormat(transaction.getCreateAt());

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


    @Getter @Setter
    public static class AccountTransferRespDto {
        private Long id;        // 계좌 ID
        private Long number;    // 계좌번호
        private Long balance;   // 잔액
        private TransactionDto transaction;
        public AccountTransferRespDto(Account account, Transaction transaction) {
            this.id = account.getId();
            this.number = account.getNumber();
            this.balance = account.getBalance();
            this.transaction = new TransactionDto(transaction);
        }
        @Getter @Setter
        public class TransactionDto {
            private Long id;
            private String gubun;
            private String sender;
            private String reciver;
            private Long amount;
            @JsonIgnore
            private Long depositAccountBalacne;
            private String createdAt;

            public TransactionDto(Transaction transaction) {
                this.id = transaction.getId();
                this.gubun = transaction.getGubun().getValue();
                this.sender = transaction.getSender();
                this.reciver = transaction.getReceiver();
                this.amount = transaction.getAmount();
                this.depositAccountBalacne = transaction.getDepositAccountBalance();
                this.createdAt = CustomDateUtil.toStringFormat(transaction.getCreateAt());
            }
        }
    }



    // DTO 가 똑같아도 재사용하지 말자 (나중에 달라질 수 있음)
    @Getter @Setter
    public static class AccountWithdrawRespDto {
        private Long id;        // 계좌 ID
        private Long number;    // 계좌번호
        private Long balance;   // 잔액
        private TransactionDto transaction;
        public AccountWithdrawRespDto(Account account, Transaction transaction) {
            this.id = account.getId();
            this.number = account.getNumber();
            this.balance = account.getBalance();
            this.transaction = new TransactionDto(transaction);
        }
        @Getter @Setter
        public class TransactionDto {
            private Long id;
            private String gubun;
            private String sender;
            private String reciver;
            private Long amount;
            private String createdAt;

            public TransactionDto(Transaction transaction) {
                this.id = transaction.getId();
                this.gubun = transaction.getGubun().getValue();
                this.sender = transaction.getSender();
                this.reciver = transaction.getReceiver();
                this.amount = transaction.getAmount();
                this.createdAt = CustomDateUtil.toStringFormat(transaction.getCreateAt());
            }
        }
    }


    @Getter @Setter
    public static class AccountDepositRespDto {
        private Long id;        // 계좌 ID
        private Long number;    // 계좌번호
        private TransactionDto transaction;
        public AccountDepositRespDto(Account account, Transaction transaction) {
            this.id = account.getId();
            this.number = account.getNumber();
            this.transaction = new TransactionDto(transaction);
        }
        @Getter @Setter
        public class TransactionDto {
            private Long id;
            private String gubun;
            private String sender;
            private String reciver;
            private Long amount;
            @JsonIgnore
            private Long depositAccountBalance; // 클라이언트에게 전달X -> 서비스단에서 테스트 용도
            private String tel;
            private String createdAt;

            public TransactionDto(Transaction transaction) {
                this.id = transaction.getId();
                this.gubun = transaction.getGubun().getValue();
                this.sender = transaction.getSender();
                this.reciver = transaction.getReceiver();
                this.amount = transaction.getAmount();
                this.depositAccountBalance = transaction.getDepositAccountBalance();
                this.tel = transaction.getTel();
                this.createdAt = CustomDateUtil.toStringFormat(transaction.getCreateAt());
            }
        }
    }

    @Getter
    @Setter
    public static class AccountSaveRespDto {
        private Long id;
        private Long number;
        private Long balance;

        public AccountSaveRespDto(Account account) {
            this.id = account.getId();
            this.number = account.getNumber();
            this.balance = account.getBalance();
        }
    }
}
