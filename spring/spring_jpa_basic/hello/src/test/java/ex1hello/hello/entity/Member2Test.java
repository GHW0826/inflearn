package ex1hello.hello.entity;

import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.*;

class Member2Test {
    @Test
    public void relative1() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member2 member2 = new Member2();
            member2.setUsername("member1");
//            member2.setTeamId(team.getId());
            member2.setTeam(team);
            em.persist(member2);

            //조회
            Member findMember = em.find(Member.class, member2.getId());

            //연관관계가 없음
            Team findTeam = em.find(Team.class, team.getId());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}