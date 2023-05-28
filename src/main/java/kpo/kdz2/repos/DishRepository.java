package kpo.kdz2.repos;

import kpo.kdz2.domain.Dish;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DishRepository extends JpaRepository<Dish, Integer> {
}
