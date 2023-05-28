package kpo.kdz2.service;

import java.util.List;
import kpo.kdz2.domain.Dish;
import kpo.kdz2.domain.OrderDish;
import kpo.kdz2.model.DishDTO;
import kpo.kdz2.repos.DishRepository;
import kpo.kdz2.repos.OrderDishRepository;
import kpo.kdz2.util.NotFoundException;
import kpo.kdz2.util.WebUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DishService {

    private final DishRepository dishRepository;
    private final OrderDishRepository orderDishRepository;

    public DishService(final DishRepository dishRepository,
            final OrderDishRepository orderDishRepository) {
        this.dishRepository = dishRepository;
        this.orderDishRepository = orderDishRepository;
    }

    public List<DishDTO> findAll() {
        final List<Dish> dishs = dishRepository.findAll(Sort.by("id"));
        return dishs.stream()
                .map((dish) -> mapToDTO(dish, new DishDTO()))
                .toList();
    }

    public DishDTO get(final Integer id) {
        return dishRepository.findById(id)
                .map((dish) -> mapToDTO(dish, new DishDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final DishDTO dishDTO) {
        final Dish dish = new Dish();
        mapToEntity(dishDTO, dish);
        return dishRepository.save(dish).getId();
    }

    public void update(final Integer id, final DishDTO dishDTO) {
        final Dish dish = dishRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(dishDTO, dish);
        dishRepository.save(dish);
    }

    public void delete(final Integer id) {
        dishRepository.deleteById(id);
    }

    private DishDTO mapToDTO(final Dish dish, final DishDTO dishDTO) {
        dishDTO.setId(dish.getId());
        dishDTO.setName(dish.getName());
        dishDTO.setDescription(dish.getDescription());
        dishDTO.setPrice(dish.getPrice());
        dishDTO.setQuantity(dish.getQuantity());
        return dishDTO;
    }

    private Dish mapToEntity(final DishDTO dishDTO, final Dish dish) {
        dish.setName(dishDTO.getName());
        dish.setDescription(dishDTO.getDescription());
        dish.setPrice(dishDTO.getPrice());
        dish.setQuantity(dishDTO.getQuantity());
        return dish;
    }

    public String getReferencedWarning(final Integer id) {
        final Dish dish = dishRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final OrderDish dishOrderDish = orderDishRepository.findFirstByDish(dish);
        if (dishOrderDish != null) {
            return WebUtils.getMessage("dish.orderDish.dish.referenced", dishOrderDish.getId());
        }
        return null;
    }

}
