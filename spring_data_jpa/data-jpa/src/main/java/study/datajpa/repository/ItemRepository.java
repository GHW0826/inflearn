package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.entity.Item;
import study.datajpa.entity.Member;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
