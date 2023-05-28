package kpo.kdz2.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import kpo.kdz2.model.OrderDTO;
import kpo.kdz2.service.OrderService;
import kpo.kdz2.util.RandomWaitThread;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderResource {

    private final OrderService orderService;

    public OrderResource(final OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(orderService.get(id));
    }



    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createOrder(@RequestBody @Valid final OrderDTO orderDTO) {
        orderDTO.setStatus("Not Ready");
        final Integer createdId = orderService.create(orderDTO);
        var wait = new RandomWaitThread(orderService,orderDTO);
        wait.start();
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateOrder(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final OrderDTO orderDTO) {
        orderService.update(id, orderDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteOrder(@PathVariable(name = "id") final Integer id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
