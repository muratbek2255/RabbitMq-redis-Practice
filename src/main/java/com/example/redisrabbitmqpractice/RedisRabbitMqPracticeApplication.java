package com.example.redisrabbitmqpractice;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableRabbit
@EnableScheduling
@SpringBootApplication
public class RedisRabbitMqPracticeApplication {

    public static void main(String[] args) {
//        SpringApplication.run(RedisRabbitMqPracticeApplication.class, args);
        new SpringApplicationBuilder().
                bannerMode(Banner.Mode.OFF).
                sources(RedisRabbitMqPracticeApplication.class).
                run(args);
    }

}
