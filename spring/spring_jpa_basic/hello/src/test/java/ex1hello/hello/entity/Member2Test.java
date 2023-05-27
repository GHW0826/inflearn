package ex1hello.hello.entity;

import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import java.util.List;

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
            Member2 findMember = em.find(Member2.class, member2.getId());

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

    @Test
    public void relative2() {
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
//          member2.setTeamId(team.getId());
            member2.setTeam(team);
            em.persist(member2);

            em.flush();
            em.clear();

            //조회
            Member2 findMember = em.find(Member2.class, member2.getId());
            List<Member2> members = findMember.getTeam().getMembers();
            for (Member2 member : members) {
                System.out.println("member.getUsername() = " + member.getUsername());
            }

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

    @Test
    public void relative_owner1() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member2 member2 = new Member2();
            member2.setUsername("member1");
            em.persist(member2);

            Team team = new Team();
            team.setName("TeamA");
            team.getMembers().add(member2);
            em.persist(team);

            em.flush();
            em.clear();

            //조회
            Member2 findMember = em.find(Member2.class, member2.getId());
            List<Member2> members = findMember.getTeam().getMembers();
            for (Member2 member : members) {
                System.out.println("member.getUsername() = " + member.getUsername());
            }

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

    @Test
    public void relative_owner2() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team team = new Team();
            team.setName("TeamA");
//            team.getMembers().add(member2);
            em.persist(team);

            Member2 member2 = new Member2();
            member2.setUsername("member1");
            member2.setTeam(team);
            em.persist(member2);


            em.flush();
            em.clear();

            //조회
            Member2 findMember = em.find(Member2.class, member2.getId());
            List<Member2> members = findMember.getTeam().getMembers();
            for (Member2 member : members) {
                System.out.println("member.getUsername() = " + member.getUsername());
            }

            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@");

            //연관관계가 없음
            Team findTeam = em.find(Team.class, team.getId());
            List<Member2> members2 = findTeam.getMembers();
            for (Member2 member21 : members2) {
                System.out.println("member21.getUsername() = " + member21.getUsername());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    @Test
    public void relative_owner3() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team team = new Team();
            team.setName("TeamA");
//            team.getMembers().add(member2);
            em.persist(team);

            Member2 member2 = new Member2();
            member2.setUsername("member1");
            member2.setTeam(team);
            em.persist(member2);
//            em.flush();
//            em.clear();

            // 1차 캐시에서 불러오는데 콜렉션에 값이 없기때문에 비어있다.
            //조회
            Member2 findMember = em.find(Member2.class, member2.getId());
            List<Member2> members = findMember.getTeam().getMembers();
            for (Member2 member : members) {
                System.out.println("member.getUsername() = " + member.getUsername());
            }

            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@");

            //연관관계가 없음
            Team findTeam = em.find(Team.class, team.getId());
            List<Member2> members2 = findTeam.getMembers();
            for (Member2 member21 : members2) {
                System.out.println("member21.getUsername() = " + member21.getUsername());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    @Test
    public void relative_owner4() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team team = new Team();
            team.setName("TeamA");
//            team.getMembers().add(member2);
            em.persist(team);

            Member2 member2 = new Member2();
            member2.setUsername("member1");
            member2.setTeam(team);          //**
            em.persist(member2);
            team.getMembers().add(member2); //**
//            em.flush();
//            em.clear();

            // 1차 캐시에서 불러오는데 콜렉션에 값이 없기때문에 비어있다.
            //조회
            Member2 findMember = em.find(Member2.class, member2.getId());
            List<Member2> members = findMember.getTeam().getMembers();
            for (Member2 member : members) {
                System.out.println("member.getUsername() = " + member.getUsername());
            }

            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@");

            //연관관계가 없음
            Team findTeam = em.find(Team.class, team.getId());
            List<Member2> members2 = findTeam.getMembers();
            for (Member2 member21 : members2) {
                System.out.println("member21.getUsername() = " + member21.getUsername());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}