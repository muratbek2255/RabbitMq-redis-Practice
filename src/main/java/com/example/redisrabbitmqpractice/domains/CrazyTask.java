package com.example.redisrabbitmqpractice.domains;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CrazyTask implements Serializable {

    String id;

    String fromServer;

}
