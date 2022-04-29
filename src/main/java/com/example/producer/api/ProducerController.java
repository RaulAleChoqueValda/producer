package com.example.producer.api;

import com.example.producer.config.RabbitMqConfig;
import com.example.producer.dto.StudentDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class ProducerController {

    @Autowired
    private RabbitTemplate template;

//    @PostMapping("/v1/api/consumer")
    @RequestMapping(method = RequestMethod.POST, value = "/v1/api/producer/exchange=direct")
    public String sendMessageDirect(@RequestBody StudentDto studentDto) {
        studentDto.setStudentId(UUID.randomUUID().toString());
        template.convertAndSend(RabbitMqConfig.EXCHANGE_1, RabbitMqConfig.ROUTING_KEY_1, studentDto);
        return "Estudiante enviado a la cola 1";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/v1/api/producer/exchange=fanout")
    public String sendMessageFanout(@RequestBody StudentDto studentDto) {
        studentDto.setStudentId(UUID.randomUUID().toString());
        template.convertAndSend(RabbitMqConfig.EXCHANGE_2, "", studentDto);
        return "Estudiante enviado a todas las colas";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/v1/api/producer/exchange=topic&routingKey={key}")
    public String sendMessageTopic(@RequestBody StudentDto studentDto,@PathVariable(name = "key") String key) {
        studentDto.setStudentId(UUID.randomUUID().toString());
        template.convertAndSend(RabbitMqConfig.EXCHANGE_3, key, studentDto);
        return "Estudiante enviado a " + key;
    }

}
