package shop.coding.bank.domain.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

interface Dao {
    List<Transaction> findTransactonList(
            @Param("accountId") Long accountId,
            @Param("gubun") String gubun,
            @Param("page") Integer page
    );
}

// Impl을 꼭 붙여줘야 하고, TransactionRepository가 옆에 붙어야 한다.
public class TransactionRepositoryImpl implements Dao {

    private final EntityManager em;

    @Autowired
    public TransactionRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Transaction> findTransactonList(Long accountId, String gubun, Integer page) {
        // 동적 쿼리 (gubun 값을 가지고 동적 쿼리 = DEPOSIT, WITHDRAW, ALL)

        // JPQL
        String sql = "";
        sql += "select t from Transaction t ";

        if (gubun.equals("WITHDRAW")) {
            sql += "join fetch t.withdrawAccount wa ";
            sql += "where t.withdrawAccount.id = :withdrawAccountId";
        }
        else if (gubun.equals("DEPOSIT")) {
            sql += "join fetch t.depositAccount da ";
            sql += "where t.depositAccount.id = :depositAccountId";
        }
        else {
            // gubun = ALL
            sql += "left join fetch t.withdrawAccount wa ";
            sql += "left join fetch t.depositAccount da ";
            sql += "where t.withdrawAccount.id = :withdrawAccountId ";
            sql += "or ";
            sql += "t.depositAccount.id = :depositAccountId";
        }

        TypedQuery<Transaction> query  = em.createQuery(sql, Transaction.class);

        if (gubun.equals("WITHDRAW")) {
            query = query.setParameter("withdrawAccountId", accountId);
        }
        else if (gubun.equals("DEPOSIT")) {
            query = query.setParameter("depositAccountId", accountId);
        }
        else {
            query = query.setParameter("withdrawAccountId", accountId);
            query = query.setParameter("depositAccountId", accountId);
        }

        query.setFirstResult(page * 5);
        query.setMaxResults(5);

        return query.getResultList();
    }
}

