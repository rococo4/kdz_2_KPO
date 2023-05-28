package kpo.kdz2.util;

import kpo.kdz2.model.OrderDTO;
import kpo.kdz2.service.OrderService;

import java.util.Random;

public class RandomWaitThread extends Thread {
    private static final int MIN_WAIT_TIME = 2000; // 2 секунды в миллисекундах
    private static final int MAX_WAIT_TIME = 10000; // 10 секунд в миллисекундах
    private OrderService orderService;
    private OrderDTO orderDTO;


    public RandomWaitThread (OrderService orderService, OrderDTO orderDTO) {
        this.orderService = orderService;
        this.orderDTO = orderDTO;
    }
    @Override
    public void run() {
        Random random = new Random();
        int waitTime = random.nextInt(MAX_WAIT_TIME - MIN_WAIT_TIME + 1) + MIN_WAIT_TIME;
        try {
            Thread.sleep(waitTime);
            orderDTO.setStatus("Ready");
            orderService.update(orderDTO.getId(), orderDTO);
            System.out.println("Заказ готовился " + waitTime / 1000+ " секунд.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
