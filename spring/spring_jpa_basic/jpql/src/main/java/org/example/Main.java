package org.example;

import javax.persistence.*;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Member member = new Member();
            member.setUsername("123");
            member.setAge(10);
            em.persist(member);

            TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
            Query query2 = em.createQuery("select m.username, m.age from Member m", Member.class);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}