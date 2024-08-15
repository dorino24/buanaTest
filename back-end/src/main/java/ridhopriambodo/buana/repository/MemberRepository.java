package ridhopriambodo.buana.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ridhopriambodo.buana.entity.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Integer> {

}
