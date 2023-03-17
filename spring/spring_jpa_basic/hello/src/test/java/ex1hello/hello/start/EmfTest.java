package ex1hello.hello.start;

import ex1hello.hello.entity.Member;
import org.junit.jupiter.api.Test;

import javax.persistence.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmfTest {

    @Test
    public void emf_insert() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Member m = new Member(1L, "hello");
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
    public void emf_select_findById() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            Member member = em.find(Member.class, 1L);
            System.out.println("member.getId() = " + member.getId());
            System.out.println("member.getName() = " + member.getName());
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
    public void emf_delete() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            Member member = em.find(Member.class, 1L);
            em.remove(member);
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
    public void emf_update() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            Member member = em.find(Member.class, 1L);
            member.setName("hello_update");
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            // em이 db 커넥션을 물고 동작.
            em.close();
        }
        emf.close();
    }

    @Test
    public void emf_select_JPQL() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            List<Member> result = em.createQuery("select m from Member m", Member.class)
                    .getResultList();

            for (Member member : result) {
                System.out.println("member.getId() = " + member.getId());
                System.out.println("member.getName() = " + member.getName());
            }
            
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            // em이 db 커넥션을 물고 동작.
            em.close();
        }
        emf.close();
    }
}