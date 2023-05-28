package kpo.kdz2.repos;

import kpo.kdz2.domain.Order;
import kpo.kdz2.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Integer> {

    Order findFirstByUser(User user);

}
