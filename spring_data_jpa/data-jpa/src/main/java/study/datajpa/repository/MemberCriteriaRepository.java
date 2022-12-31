package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import study.datajpa.entity.Member;

public interface MemberCriteriaRepository extends JpaRepository<Member, Long>, JpaSpecificationExecutor<Member> {
}
