package tech.buildrun.btgpactual.orderms.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tech.buildrun.btgpactual.orderms.listener.dto.OrderCreatedEvent;
import tech.buildrun.btgpactual.orderms.service.OrderService;

import static tech.buildrun.btgpactual.orderms.config.RabbitMqConfig.ORDER_CREATED_QUEUE;

@Component
public class OrderCreatedListener {

    private final Logger logger = LoggerFactory.getLogger(OrderCreatedListener.class);

    private final OrderService orderService;

    public OrderCreatedListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = ORDER_CREATED_QUEUE)
    public void listen(OrderCreatedEvent message) {
        logger.info("Message consumed: {}", message);
        orderService.save(message);
    }
}