package ex1hello.hello.start;

import ex1hello.hello.entity.Member;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class PersistenceContextTest {

    @Test
    public void pc_new() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Member m = new Member(1L, "hello");

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            // em이 db 커넥션을 물고 동작.
            em.close();
        }
        emf.close();
    }

    @Test
    public void pc_managed() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Member m = new Member(2L, "hello");
            em.persist(m);

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            // em이 db 커넥션을 물고 동작.
            em.close();
        }
        emf.close();
    }

    @Test
    public void pc_removed() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Member m = new Member(3L, "hello");
            em.persist(m);
            em.remove(m);
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            // em이 db 커넥션을 물고 동작.
            em.close();
        }
        emf.close();
    }
}
i