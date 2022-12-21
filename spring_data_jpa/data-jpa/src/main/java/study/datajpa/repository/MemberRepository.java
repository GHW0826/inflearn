package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findTop3HelloBy();
    
    // 주석처리해도 동작
    // NamedQuery에서 Member.메서드명을 먼저 찾음.
    // 없으면 메서드 이름으로 쿼리 생성.
    //@Query(name = "Member.findByUsername")
    List<Member> findByUsername(@Param("username") String username);
}