package kpo.kdz2.repos;

import kpo.kdz2.domain.Session;
import kpo.kdz2.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SessionRepository extends JpaRepository<Session, Long> {

    Session findFirstByUser(User user);

}
