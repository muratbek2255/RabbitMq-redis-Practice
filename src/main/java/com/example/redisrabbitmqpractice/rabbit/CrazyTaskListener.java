package com.example.redisrabbitmqpractice.rabbit;


import com.example.redisrabbitmqpractice.domains.CrazyTask;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Log4j2
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Component
public class CrazyTaskListener {

    ObjectMapper mapper = new ObjectMapper();

    public static final String CRAZY_TASKS_QUEUE = "com.example.redis.tasks.queue";
    public static final String CRAZY_TASKS_EXCHANGE = "com.example.redis.tasks.exchange";

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = CRAZY_TASKS_QUEUE),
                    exchange = @Exchange(value = CRAZY_TASKS_EXCHANGE)
            )
    )

    public void handleCrazyTask(CrazyTask crazyTask) throws JsonProcessingException {
        log.info(mapper.writeValueAsString(crazyTask));
    }

}
