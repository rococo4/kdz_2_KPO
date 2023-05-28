package kpo.kdz2.service;

import java.util.List;
import kpo.kdz2.domain.Dish;
import kpo.kdz2.domain.Order;
import kpo.kdz2.domain.OrderDish;
import kpo.kdz2.model.OrderDishDTO;
import kpo.kdz2.repos.DishRepository;
import kpo.kdz2.repos.OrderDishRepository;
import kpo.kdz2.repos.OrderRepository;
import kpo.kdz2.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class OrderDishService {

    private final OrderDishRepository orderDishRepository;
    private final OrderRepository orderRepository;
    private final DishRepository dishRepository;

    public OrderDishService(final OrderDishRepository orderDishRepository,
            final OrderRepository orderRepository, final DishRepository dishRepository) {
        this.orderDishRepository = orderDishRepository;
        this.orderRepository = orderRepository;
        this.dishRepository = dishRepository;
    }

    public List<OrderDishDTO> findAll() {
        final List<OrderDish> orderDishs = orderDishRepository.findAll(Sort.by("id"));
        return orderDishs.stream()
                .map((orderDish) -> mapToDTO(orderDish, new OrderDishDTO()))
                .toList();
    }

    public OrderDishDTO get(final Integer id) {
        return orderDishRepository.findById(id)
                .map((orderDish) -> mapToDTO(orderDish, new OrderDishDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final OrderDishDTO orderDishDTO) {
        final OrderDish orderDish = new OrderDish();
        mapToEntity(orderDishDTO, orderDish);
        return orderDishRepository.save(orderDish).getId();
    }

    public void update(final Integer id, final OrderDishDTO orderDishDTO) {
        final OrderDish orderDish = orderDishRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(orderDishDTO, orderDish);
        orderDishRepository.save(orderDish);
    }

    public void delete(final Integer id) {
        orderDishRepository.deleteById(id);
    }

    private OrderDishDTO mapToDTO(final OrderDish orderDish, final OrderDishDTO orderDishDTO) {
        orderDishDTO.setId(orderDish.getId());
        orderDishDTO.setQuantity(orderDish.getQuantity());
        orderDishDTO.setPrice(orderDish.getPrice());
        orderDishDTO.setOrder(orderDish.getOrder() == null ? null : orderDish.getOrder().getId());
        orderDishDTO.setDish(orderDish.getDish() == null ? null : orderDish.getDish().getId());
        return orderDishDTO;
    }

    private OrderDish mapToEntity(final OrderDishDTO orderDishDTO, final OrderDish orderDish) {
        orderDish.setQuantity(orderDishDTO.getQuantity());
        orderDish.setPrice(orderDishDTO.getPrice());
        final Order order = orderDishDTO.getOrder() == null ? null : orderRepository.findById(orderDishDTO.getOrder())
                .orElseThrow(() -> new NotFoundException("order not found"));
        orderDish.setOrder(order);
        final Dish dish = orderDishDTO.getDish() == null ? null : dishRepository.findById(orderDishDTO.getDish())
                .orElseThrow(() -> new NotFoundException("dish not found"));
        orderDish.setDish(dish);
        return orderDish;
    }

}
