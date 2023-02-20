package com.example.redisrabbitmqpractice.service;


import com.example.redisrabbitmqpractice.config.RedisLock;
import com.example.redisrabbitmqpractice.domains.CrazyTask;
import com.example.redisrabbitmqpractice.rabbit.CrazyTaskSender;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Strings;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Log4j2
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class CrazyTaskInitializerService {

    RedisLock redisLock;
    CrazyTaskSender crazyTaskSender;

    private static final String SERVER_ID = UUID.randomUUID().toString();
    private static final long ONE_MINUTE_IN_MILLIS = 1000 * 6;
    private static final String GENERATE_CRAZY_TASKS_KEY = "com:example:redis:rabbitmq:practice:generate:crazy:tasks";

    @Scheduled(fixedDelay = 1000L)
    public void generateCrazyTask() {

        if (redisLock.acquireLock(ONE_MINUTE_IN_MILLIS, GENERATE_CRAZY_TASKS_KEY)) {

            log.info(Strings.repeat("-", 100));
//            log.info(String.format("Server \"%s\" generate tasks"), SERVER_ID);

            for (int i = 0; i < 100; i++) {
                crazyTaskSender.sendCrazyTask(
                        CrazyTask.builder()
                                .id(UUID.randomUUID().toString())
                                .fromServer(SERVER_ID)
                                .build()
                );
            }

            log.info(Strings.repeat("-", 100));
//            log.info(String.format("Server \"%s\" generate tasks"), SERVER_ID);

            redisLock.release(GENERATE_CRAZY_TASKS_KEY);
        }
    }
}
