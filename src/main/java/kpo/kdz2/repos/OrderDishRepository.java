package kpo.kdz2.repos;

import kpo.kdz2.domain.Dish;
import kpo.kdz2.domain.Order;
import kpo.kdz2.domain.OrderDish;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderDishRepository extends JpaRepository<OrderDish, Integer> {

    OrderDish findFirstByDish(Dish dish);

    OrderDish findFirstByOrder(Order order);

}
